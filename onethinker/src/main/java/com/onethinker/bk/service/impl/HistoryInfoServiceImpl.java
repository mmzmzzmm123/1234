package com.onethinker.bk.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onethinker.bk.mapper.HistoryInfoMapper;
import com.onethinker.bk.domain.HistoryInfo;
import com.onethinker.bk.service.IHistoryInfoService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 历史信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class HistoryInfoServiceImpl extends ServiceImpl<HistoryInfoMapper,HistoryInfo> implements IHistoryInfoService {
    @Resource
    private HistoryInfoMapper historyInfoMapper;

    /**
     * 查询历史信息
     *
     * @param id 历史信息主键
     * @return 历史信息
     */
    @Override
    public HistoryInfo selectHistoryInfoById(Long id) {
        return historyInfoMapper.selectHistoryInfoById(id);
    }

    /**
     * 查询历史信息列表
     *
     * @param historyInfo 历史信息
     * @return 历史信息
     */
    @Override
    public List<HistoryInfo> selectHistoryInfoList(HistoryInfo historyInfo) {
        return historyInfoMapper.selectHistoryInfoList(historyInfo);
    }

    /**
     * 新增历史信息
     *
     * @param historyInfo 历史信息
     * @return 结果
     */
    @Override
    public int insertHistoryInfo(HistoryInfo historyInfo) {
                historyInfo.setCreateTime(DateUtils.getNowDate());
            return historyInfoMapper.insertHistoryInfo(historyInfo);
    }

    /**
     * 修改历史信息
     *
     * @param historyInfo 历史信息
     * @return 结果
     */
    @Override
    public int updateHistoryInfo(HistoryInfo historyInfo) {
        return historyInfoMapper.updateHistoryInfo(historyInfo);
    }

    /**
     * 批量删除历史信息
     *
     * @param ids 需要删除的历史信息主键
     * @return 结果
     */
    @Override
    public int deleteHistoryInfoByIds(Long[] ids) {
        return historyInfoMapper.deleteHistoryInfoByIds(ids);
    }

    /**
     * 删除历史信息信息
     *
     * @param id 历史信息主键
     * @return 结果
     */
    @Override
    public int deleteHistoryInfoById(Long id) {
        return historyInfoMapper.deleteHistoryInfoById(id);
    }
}
