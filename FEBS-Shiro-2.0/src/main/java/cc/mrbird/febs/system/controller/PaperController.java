package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Paper;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IPaperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("paper")
public class PaperController extends BaseController {

    private final IPaperService paperService;

//    @ResponseBody
//    @PostMapping("/mpaper")
//    public String mpaper(@RequestBody String paperJson) throws JSONException {
//        JSONObject mJsonObject = new JSONObject(paperJson);
//        long paperId = mJsonObject.optLong("paperId");
//        Paper paper = paperService.findByPaper(paperId);
//        return "{\"type\":\"paper_content\", \"data\":\"" + paper.getContent() + "\"}";
//    }

    @GetMapping("list")
    @RequiresPermissions("paper:view")
    public FebsResponse paperList(Paper paper, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.paperService.findPaperDetailList(paper, request));
        return new FebsResponse().success().data(dataTable);
    }

    @PostMapping
    @RequiresPermissions("paper:add")
    @ControllerEndpoint(operation = "新增试题", exceptionMessage = "新增试题失败")
    public FebsResponse addPaper(@Valid Paper paper) {
        this.paperService.createPaper(paper);
        return new FebsResponse().success();
    }
}
