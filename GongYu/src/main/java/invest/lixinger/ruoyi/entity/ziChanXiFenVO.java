package invest.lixinger.ruoyi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("getZiChanXiFen")
@Data
public class ziChanXiFenVO {
    // 变量名不能有下划线
    @TableId(value = "name",type=IdType.INPUT)
    String name;
    @TableField(value = "huobi")
    double huobi;
    @TableField(value = "zhaiquan")
    double zhaiquan;
    @TableField(value = "yitouru")
    double yitouru;
    @TableField(value = "guonei_zichan")
    double guoneiZichan;
    @TableField(value = "lundong_zichan")
    double lundongZichan;
    @TableField(value = "haiwai_zichan")
    double haiwaiZichan;
    @TableField(value = "zongji")
    double zongji;
}
