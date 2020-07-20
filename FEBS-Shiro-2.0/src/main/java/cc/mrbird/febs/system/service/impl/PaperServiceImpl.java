package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.Paper;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.mapper.PaperMapper;
import cc.mrbird.febs.system.service.IPaperService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {
    @Override
    public Paper findByPaper(long paper_id) {
        return this.baseMapper.findByPaper(paper_id);
    }

    @Override
    public IPage<Paper> findPaperDetailList(Paper paper, QueryRequest request) {
        Page<Paper> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setSearchCount(false);
        page.setTotal(baseMapper.countPaperDetail(paper));
        SortUtil.handlePageSort(request, page, "paperId", FebsConstant.ORDER_ASC, false);
        return this.baseMapper.findPaperDetailPage(page, paper);
    }

    @Override
    public Paper findPaperDetailList(long paper_id) {
        Paper param = new Paper();
        param.setPaperId(paper_id);
        List<Paper> papers = this.baseMapper.findPaperDetail(param);
        return CollectionUtils.isNotEmpty(papers) ? papers.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPaper(Paper paper) {
        save(paper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePapers(String[] paperIds) {
        List<String> list = Arrays.asList(paperIds);
        this.removeByIds(list);
    }

}
