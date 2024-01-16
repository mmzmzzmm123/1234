package com.ruoyi.bk.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bk.mapper.WeiYanMapper;
import com.ruoyi.bk.domain.WeiYan;
import com.ruoyi.bk.service.IWeiYanService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 微言Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class WeiYanServiceImpl extends ServiceImpl<WeiYanMapper,WeiYan> implements IWeiYanService {
    @Resource
    private WeiYanMapper weiYanMapper;

    /**
     * 查询微言
     *
     * @param id 微言主键
     * @return 微言
     */
    @Override
    public WeiYan selectWeiYanById(Long id) {
        return weiYanMapper.selectWeiYanById(id);
    }

    /**
     * 查询微言列表
     *
     * @param weiYan 微言
     * @return 微言
     */
    @Override
    public List<WeiYan> selectWeiYanList(WeiYan weiYan) {
        return weiYanMapper.selectWeiYanList(weiYan);
    }

    /**
     * 新增微言
     *
     * @param weiYan 微言
     * @return 结果
     */
    @Override
    public int insertWeiYan(WeiYan weiYan) {
                weiYan.setCreateTime(DateUtils.getNowDate());
            return weiYanMapper.insertWeiYan(weiYan);
    }

    /**
     * 修改微言
     *
     * @param weiYan 微言
     * @return 结果
     */
    @Override
    public int updateWeiYan(WeiYan weiYan) {
        return weiYanMapper.updateWeiYan(weiYan);
    }

    /**
     * 批量删除微言
     *
     * @param ids 需要删除的微言主键
     * @return 结果
     */
    @Override
    public int deleteWeiYanByIds(Long[] ids) {
        return weiYanMapper.deleteWeiYanByIds(ids);
    }

    /**
     * 删除微言信息
     *
     * @param id 微言主键
     * @return 结果
     */
    @Override
    public int deleteWeiYanById(Long id) {
        return weiYanMapper.deleteWeiYanById(id);
    }
}
