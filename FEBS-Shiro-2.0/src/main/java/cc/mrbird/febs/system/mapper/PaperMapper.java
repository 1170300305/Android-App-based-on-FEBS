package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.Paper;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author Bilthas
 */
public interface PaperMapper extends BaseMapper<Paper> {

    /**
     *通过试卷的id查找试卷
     * @param paper_id 试卷id
     * @return 试卷
     */
    Paper findByPaper(long paper_id);

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param paper 用户对象，用于传递查询条件
     * @return Ipage
     */
    <T> IPage<Paper> findPaperDetailPage(Page<T> page, @Param("paper") Paper paper);

    long countPaperDetail(@Param("paper") Paper paper);

    /**
     * 查找用户详细信息
     *
     * @param paper 用户对象，用于传递查询条件
     * @return List<Paper>
     */
    List<Paper> findPaperDetail(@Param("paper") Paper paper);
}
