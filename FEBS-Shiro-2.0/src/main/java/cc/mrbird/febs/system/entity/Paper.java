package cc.mrbird.febs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Bilthas
 */
@Data
@TableName("paper")
@Excel("试题表")
public class Paper implements Serializable {

    private static final long serialVersionUId = 1025995956;

    /**
     * 默认试卷内容
     */
    public static final String DEFAULT_CONTENT = "math";

    /**
     * 默认答案
     */
    public static final String DEFAULT_ANSWER = "不会";

    /**
     * 试卷 ID
     */
//    @TableId(value = "paper_id", type = IdType.AUTO)
    @TableField("paper_id")
    private long paperId;

    /**
     * 试题
     */
    @TableField("paper_content")
    private String content;

    /**
     * 答案
     */
    @TableField("paper_answer")
    private String answer;

    public long getId() {return paperId; }

}
