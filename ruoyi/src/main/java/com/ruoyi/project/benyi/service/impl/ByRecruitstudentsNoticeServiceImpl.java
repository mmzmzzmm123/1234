package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByRecruitstudentsNoticeMapper;
import com.ruoyi.project.benyi.domain.ByRecruitstudentsNotice;
import com.ruoyi.project.benyi.service.IByRecruitstudentsNoticeService;

/**
 * 入园通知书Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-28
 */
@Service
public class ByRecruitstudentsNoticeServiceImpl implements IByRecruitstudentsNoticeService {
    @Autowired
    private ByRecruitstudentsNoticeMapper byRecruitstudentsNoticeMapper;

    /**
     * 查询入园通知书
     *
     * @param id 入园通知书ID
     * @return 入园通知书
     */
    @Override
    public ByRecruitstudentsNotice selectByRecruitstudentsNoticeById(Long id) {
        return byRecruitstudentsNoticeMapper.selectByRecruitstudentsNoticeById(id);
    }

    /**
     * 查询入园通知书列表
     *
     * @param byRecruitstudentsNotice 入园通知书
     * @return 入园通知书
     */
    @Override
    public List<ByRecruitstudentsNotice> selectByRecruitstudentsNoticeList(ByRecruitstudentsNotice byRecruitstudentsNotice) {
        return byRecruitstudentsNoticeMapper.selectByRecruitstudentsNoticeList(byRecruitstudentsNotice);
    }

    /**
     * 新增入园通知书
     *
     * @param byRecruitstudentsNotice 入园通知书
     * @return 结果
     */
    @Override
    public int insertByRecruitstudentsNotice(ByRecruitstudentsNotice byRecruitstudentsNotice) {
        byRecruitstudentsNotice.setCreateTime(DateUtils.getNowDate());
        return byRecruitstudentsNoticeMapper.insertByRecruitstudentsNotice(byRecruitstudentsNotice);
    }

    /**
     * 修改入园通知书
     *
     * @param byRecruitstudentsNotice 入园通知书
     * @return 结果
     */
    @Override
    public int updateByRecruitstudentsNotice(ByRecruitstudentsNotice byRecruitstudentsNotice) {
        return byRecruitstudentsNoticeMapper.updateByRecruitstudentsNotice(byRecruitstudentsNotice);
    }

    /**
     * 批量删除入园通知书
     *
     * @param ids 需要删除的入园通知书ID
     * @return 结果
     */
    @Override
    public int deleteByRecruitstudentsNoticeByIds(Long[] ids) {
        return byRecruitstudentsNoticeMapper.deleteByRecruitstudentsNoticeByIds(ids);
    }

    /**
     * 删除入园通知书信息
     *
     * @param id 入园通知书ID
     * @return 结果
     */
    @Override
    public int deleteByRecruitstudentsNoticeById(Long id) {
        return byRecruitstudentsNoticeMapper.deleteByRecruitstudentsNoticeById(id);
    }
}
