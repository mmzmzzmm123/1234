package com.stdiet.custom.service.impl;

import java.util.Date;
import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysImportFanWxAccount;
import com.stdiet.custom.service.ISysImportFanWxAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysImportFanRecordMapper;
import com.stdiet.custom.domain.SysImportFanRecord;
import com.stdiet.custom.service.ISysImportFanRecordService;

/**
 * 导粉管理Service业务层处理
 *
 * @author xzj
 * @date 2021-05-17
 */
@Service
public class SysImportFanRecordServiceImpl implements ISysImportFanRecordService
{
    @Autowired
    private SysImportFanRecordMapper sysImportFanRecordMapper;

    @Autowired
    private ISysImportFanWxAccountService sysImportFanWxAccountService;

    /**
     * 查询导粉管理
     *
     * @param id 导粉管理ID
     * @return 导粉管理
     */
    @Override
    public SysImportFanRecord selectSysImportFanRecordById(Long id)
    {
        return sysImportFanRecordMapper.selectSysImportFanRecordById(id);
    }

    /**
     * 查询导粉管理列表
     *
     * @param sysImportFanRecord 导粉管理
     * @return 导粉管理
     */
    @Override
    public List<SysImportFanRecord> selectSysImportFanRecordList(SysImportFanRecord sysImportFanRecord)
    {
        return sysImportFanRecordMapper.selectSysImportFanRecordList(sysImportFanRecord);
    }

    /**
     * 新增导粉管理
     *
     * @param sysImportFanRecord 导粉管理
     * @return 结果
     */
    @Override
    public int insertSysImportFanRecord(SysImportFanRecord sysImportFanRecord)
    {
        sysImportFanRecord.setCreateTime(DateUtils.getNowDate());
        //根据账号渠道、直播间判断是否已存在该渠道的导粉记录
        SysImportFanRecord record = sysImportFanRecordMapper.getFanRecordByChannelLive(sysImportFanRecord);
        int row = 0;
        if(record == null){
            row = sysImportFanRecordMapper.insertSysImportFanRecord(sysImportFanRecord);
        }
        if(record != null || row > 0){
            //添加微信号对应记录
            SysImportFanWxAccount sysImportFanWxAccount = new SysImportFanWxAccount();
            sysImportFanWxAccount.setImportFanRecordId(record != null ? record.getId() : sysImportFanRecord.getId());
            sysImportFanWxAccount.setImportWxAccountId(sysImportFanRecord.getWxAccountId());
            sysImportFanWxAccount.setImportFanNum(sysImportFanRecord.getFanNum());
            sysImportFanWxAccount.setCreateTime(new Date());
            //根据微信号、导粉记录查询是否存在
            SysImportFanWxAccount oldFanWxAccount = sysImportFanWxAccountService.getWxAccountByFanRecordId(sysImportFanWxAccount);
            if(oldFanWxAccount == null){
                row = sysImportFanWxAccountService.insertSysImportFanWxAccount(sysImportFanWxAccount);
            }else{
                oldFanWxAccount.setImportFanNum((oldFanWxAccount.getImportFanNum() == null ? 0 : oldFanWxAccount.getImportFanNum()) + sysImportFanWxAccount.getImportFanNum());
                row = sysImportFanWxAccountService.updateSysImportFanWxAccount(oldFanWxAccount);
            }
        }
        return row;
    }

    /**
     * 修改导粉管理
     *
     * @param sysImportFanRecord 导粉管理
     * @return 结果
     */
    @Override
    public int updateSysImportFanRecord(SysImportFanRecord sysImportFanRecord)
    {
        sysImportFanRecord.setUpdateTime(DateUtils.getNowDate());
        return sysImportFanRecordMapper.updateSysImportFanRecord(sysImportFanRecord);
    }

    /**
     * 批量删除导粉管理
     *
     * @param ids 需要删除的导粉管理ID
     * @return 结果
     */
    @Override
    public int deleteSysImportFanRecordByIds(Long[] ids)
    {
        int row = sysImportFanRecordMapper.deleteSysImportFanRecordByIds(ids);
        if(row > 0){
            sysImportFanWxAccountService.deleteSysImportFanWxAccountByFanRecordIds(ids);
        }
        return row;
    }

    /**
     * 删除导粉管理信息
     *
     * @param id 导粉管理ID
     * @return 结果
     */
    @Override
    public int deleteSysImportFanRecordById(Long id)
    {
        int row = sysImportFanRecordMapper.deleteSysImportFanRecordById(id);
        if(row > 0){
            Long[] ids = {id};
            sysImportFanWxAccountService.deleteSysImportFanWxAccountByFanRecordIds(ids);
        }
        return row;
    }

    //查询总导粉数量
    public int selectTotalSysImportFanNum(SysImportFanRecord sysImportFanRecord){
        return sysImportFanRecordMapper.selectTotalSysImportFanNum(sysImportFanRecord);
    }
}