package com.ruoyi.project.benyi.mapper;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByRecruitstudentsNotice;

/**
 * 入园通知书Mapper接口
 *
 * @author tsbz
 * @date 2020-07-28
 */
public interface ByRecruitstudentsNoticeMapper
{
    /**
     * 查询入园通知书
     *
     * @param id 入园通知书ID
     * @return 入园通知书
     */
    public ByRecruitstudentsNotice selectByRecruitstudentsNoticeById(Long id);

    /**
     * 查询入园通知书列表
     *
     * @param byRecruitstudentsNotice 入园通知书
     * @return 入园通知书集合
     */
    public List<ByRecruitstudentsNotice> selectByRecruitstudentsNoticeList(ByRecruitstudentsNotice byRecruitstudentsNotice);

    /**
     * 新增入园通知书
     *
     * @param byRecruitstudentsNotice 入园通知书
     * @return 结果
     */
    public int insertByRecruitstudentsNotice(ByRecruitstudentsNotice byRecruitstudentsNotice);

    /**
     * 修改入园通知书
     *
     * @param byRecruitstudentsNotice 入园通知书
     * @return 结果
     */
    public int updateByRecruitstudentsNotice(ByRecruitstudentsNotice byRecruitstudentsNotice);

    /**
     * 删除入园通知书
     *
     * @param id 入园通知书ID
     * @return 结果
     */
    public int deleteByRecruitstudentsNoticeById(Long id);

    /**
     * 批量删除入园通知书
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByRecruitstudentsNoticeByIds(Long[] ids);
}
