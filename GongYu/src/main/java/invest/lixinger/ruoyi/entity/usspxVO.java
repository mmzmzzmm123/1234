package invest.lixinger.ruoyi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("lx_usspx")
public class usspxVO {
    @TableId(value = "sj", type = IdType.INPUT)
    Date sj;
    double spdw;
    double peCv;
    double pbCv;
    double psCv;
    double pePos10;
    double pbPos10;
    double psPos10;
    double pePos20;
    double pbPos20;
    double psPos20;
    double zonghePos;
}
