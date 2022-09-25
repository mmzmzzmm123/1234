package invest.lixinger.ruoyi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;


@TableName("totalAsset")
@Data
public class totalAssetVO {

    @TableId(value = "riqi", type = IdType.INPUT)
    Timestamp riqi;
    double totalasset;


}
