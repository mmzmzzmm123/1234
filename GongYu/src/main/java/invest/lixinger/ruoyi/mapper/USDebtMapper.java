package invest.lixinger.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import invest.lixinger.ruoyi.entity.CNDebtVO;
import invest.lixinger.ruoyi.entity.USDebtVO;

public interface USDebtMapper extends BaseMapper<USDebtVO> {
    USDebtVO nearestDateInDB();
}
