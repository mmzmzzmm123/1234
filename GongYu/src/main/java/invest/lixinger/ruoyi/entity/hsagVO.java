package invest.lixinger.ruoyi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("lx_hsag")
public class hsagVO {
    @TableId(value = "sj", type = IdType.INPUT)
    Date sj;
    double spdw;
    double peCv;
    double pbCv;
    double psCv;
    double zonghePos;
}
