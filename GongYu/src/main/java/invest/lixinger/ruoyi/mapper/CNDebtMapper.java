package invest.lixinger.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import invest.lixinger.ruoyi.entity.CNDebtVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CNDebtMapper extends BaseMapper<CNDebtVO> {
    CNDebtVO nearestDateInDB();


    List<CNDebtVO> dateRangeInDB(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<CNDebtVO> allDataInDB();

}
