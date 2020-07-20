package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Paper;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IPaperService extends IService<Paper> {


    /**
     *通过试卷的id查找试卷
     * @param paper_id 试卷id
     * @return 试卷
     */
    Paper findByPaper(long paper_id);

    /**
     * 查找用户详细信息
     *
     * @param request request
     * @param paper    用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<Paper> findPaperDetailList(Paper paper, QueryRequest request);

    /**
     * 通过用户名查找用户详细信息
     *
     * @param paper_id 用户名
     * @return 用户信息
     */
    Paper findPaperDetailList(long paper_id);

    /**
     * 新建试卷
     * @param paper Paper
     */
    void createPaper(Paper paper);

    /**
     * 删除试卷
     *
     * @param paperIds 试卷 id数组
     */
    void deletePapers(String[] paperIds);

}
