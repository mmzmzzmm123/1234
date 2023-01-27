package invest.lixinger.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import invest.lixinger.ruoyi.entity.hsagVO;
import invest.lixinger.ruoyi.entity.usspxVO;

public interface usspxMapper extends BaseMapper<usspxVO> {
    usspxVO nearestDateInDB();

}
