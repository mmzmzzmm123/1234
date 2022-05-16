package invest.lixinger.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import invest.lixinger.ruoyi.entity.ziChanXiFenVO;

public interface ziChanXiFenMapper extends BaseMapper<ziChanXiFenVO> {
    Page<ziChanXiFenVO> findAll(Page<ziChanXiFenVO> page);

}
