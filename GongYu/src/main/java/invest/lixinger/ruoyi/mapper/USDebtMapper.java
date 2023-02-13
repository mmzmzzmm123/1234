package invest.lixinger.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import invest.lixinger.ruoyi.entity.CNDebtVO;
import invest.lixinger.ruoyi.entity.USDebtVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface USDebtMapper extends BaseMapper<USDebtVO> {
    USDebtVO nearestDateInDB();

    List<USDebtVO> dateRangeInDB(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<USDebtVO> allDataInDB();
}
