package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Limit;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.service.ValidateCodeService;
import cc.mrbird.febs.common.utils.Md5Util;
import cc.mrbird.febs.monitor.entity.LoginLog;
import cc.mrbird.febs.monitor.service.ILoginLogService;
import cc.mrbird.febs.system.entity.Paper;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IPaperService;
import cc.mrbird.febs.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Validated
@RestController
@RequiredArgsConstructor
public class LoginController extends BaseController {

    private final IUserService userService;
    private final ValidateCodeService validateCodeService;
    private final ILoginLogService loginLogService;
    private final IPaperService paperService;


    @ResponseBody
    @PostMapping("/mpaper")
    public String mpaper(@RequestBody String paperJson) throws JSONException {
        System.out.println(paperJson);
        JSONObject mJsonObject = new JSONObject(paperJson);
        long paperId = mJsonObject.optLong("paperId");
        String commit = mJsonObject.optString("commit");
        Paper paper = paperService.findByPaper(paperId);
        if (commit.equals("")){
            return "{\"type\":\"paper_content\", \"data\":\"" + paper.getContent() + "\"}";
        }
        else{
            if (paper.getAnswer().equals(commit)){
                return "{\"type\":\"paper_answer\", \"data\":\"true\"}";
            }
            else{
                return "{\"type\":\"paper_answer\", \"data\":\"false\"}";
            }
        }
    }

    @ResponseBody
    @PostMapping("/mlogin")
    public String mlogin(@RequestBody String loginJson){
        System.out.println(loginJson);
        try{
            JSONObject mJsonObject = new JSONObject(loginJson);
            String username = mJsonObject.optString("user_name");
            String password = mJsonObject.optString("user_password");
            password = Md5Util.encrypt(username.toLowerCase(), password);
            User user = userService.findByName(username);

            if (user!=null){
                //用户名正确
                if (user.getPassword().equals(password)){
                    //用户名密码正确
                    System.out.println("成功登录");
                    return "{\"type\":\"user_login\", \"data\":\"true\", \"error\":\"\"}";
                }else{
                    System.out.println("密码错误");
                    return "{\"type\":\"user_login\", \"data\":\"false\", \"error\":\"密码错误\"}";
                }
            }else{
                System.out.println("用户名错误");
                return "{\"type\":\"user_login\", \"data\":\"false\", \"error\":\"用户名错误\"}";
            }

        }catch(Exception e){
            System.out.println("Login Error");
            return "{\"type\":\"user_login\", \"data\":\"false\"}";
        }
    }


    @PostMapping("login")
    @Limit(key = "login", period = 60, count = 10, name = "登录接口", prefix = "limit")
    public FebsResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password,
            @NotBlank(message = "{required}") String verifyCode,
            boolean rememberMe, HttpServletRequest request) throws FebsException {
        HttpSession session = request.getSession();
        validateCodeService.check(session.getId(), verifyCode);
        password = Md5Util.encrypt(username.toLowerCase(), password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        super.login(token);
        // 保存登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setSystemBrowserInfo();
        this.loginLogService.saveLoginLog(loginLog);

        return new FebsResponse().success();
    }

    @PostMapping("regist")
    public FebsResponse regist(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws FebsException {
        User user = userService.findByName(username);
        if (user != null) {
            throw new FebsException("该用户名已存在");
        }
        this.userService.regist(username, password);
        return new FebsResponse().success();
    }

    @GetMapping("index/{username}")
    public FebsResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        // 更新登录时间
        this.userService.updateLoginTime(username);
        Map<String, Object> data = new HashMap<>(5);
        // 获取系统访问记录
        Long totalVisitCount = this.loginLogService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = this.loginLogService.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = this.loginLogService.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastSevenVisitCount = this.loginLogService.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);
        User param = new User();
        param.setUsername(username);
        List<Map<String, Object>> lastSevenUserVisitCount = this.loginLogService.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return new FebsResponse().success().data(data);
    }

    @GetMapping("images/captcha")
    @Limit(key = "get_captcha", period = 60, count = 10, name = "获取验证码", prefix = "limit")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, FebsException {
        validateCodeService.create(request, response);
    }
}
