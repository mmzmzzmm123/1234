package invest.lixinger.ruoyi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("lx_cndebt")
public class CNDebtVO {
    @TableId(value = "rq", type = IdType.INPUT)
    String rq;
    double m1;
    double m3;
    double m6;
    double y1;
    double y2;
    double y3;
    double y5;
    double y7;
    double y10;
    double y20;
    double y30;
    double y2minusy10;
    double y2_3_5_10pos;


}
