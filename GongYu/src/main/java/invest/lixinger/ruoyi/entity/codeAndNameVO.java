package invest.lixinger.ruoyi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lx_codename")
public class codeAndNameVO {
    String code;
    String name;
}
