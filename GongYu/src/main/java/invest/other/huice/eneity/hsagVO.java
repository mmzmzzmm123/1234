package invest.other.huice.eneity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

@TableName("lx_hsag")
@Data
public class hsagVO {
    @TableId(value = "sj", type = IdType.INPUT)
    private Date sj;
    @TableField(value = "spdw")
    private Double spdw;
    @TableField(value = "pe_cv")
    private Double pe_cv;
    @TableField(value = "pb_cv")
    private Double pb_cv;
    @TableField(value = "ps_cv")
    private Double ps_cv;

    @TableField(value = "pe_pos10")
    private Double pe_pos10;
    @TableField(value = "pb_pos10")
    private Double pb_pos10;
    @TableField(value = "ps_pos10")
    private Double ps_pos10;

    @TableField(value = "pe_pos20")
    private Double pe_pos20;
    @TableField(value = "pb_pos20")
    private Double pb_pos20;
    @TableField(value = "ps_pos20")
    private Double ps_pos20;

    @TableField(value = "zonghe_pos")
    private Double zonghe_pos;
}
