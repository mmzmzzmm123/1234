package invest.lixinger.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import invest.lixinger.ruoyi.entity.CNDebtVO;
import invest.lixinger.ruoyi.entity.usspxVO;

public interface CNDebtMapper extends BaseMapper<CNDebtVO> {
    CNDebtVO nearestDateInDB();
}
