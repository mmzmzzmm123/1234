package  com.ruoyi.student.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruoyi.student.domain.PieVo;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-23
 */
public interface PieVoMapper 
{



    public Map<String, BigDecimal> selectPieVoAcc();
    public Map<String, BigDecimal> selectPieVoResidence();
    public Map<String, BigDecimal> selectPieVoNoBackSchool();
    public Map<String, BigDecimal> selectPieVoControl();
    public Map<String, BigDecimal> selectPieVoPlaceToSchool();

}
