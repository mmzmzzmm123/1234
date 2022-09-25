package invest.lixinger.ruoyi.mapper;

import GYProject.mybatisTest.entity.testNoSpringVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import invest.lixinger.ruoyi.entity.totalAssetVO;

import java.util.List;

public interface totalAssetMapper extends BaseMapper<totalAssetVO> {
    List<totalAssetVO> findAll();

}
