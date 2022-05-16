package invest.lixinger.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import invest.lixinger.ruoyi.entity.hsagVO;

import java.util.List;

public interface hsagMapper extends BaseMapper<hsagVO> {
    hsagVO nearestDateInDB();

}
