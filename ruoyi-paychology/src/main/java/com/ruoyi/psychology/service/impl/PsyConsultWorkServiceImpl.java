package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.mapper.PsyConsultWorkMapper;
import com.ruoyi.psychology.service.IPsyConsultWorkService;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PsyConsultWorkServiceImpl implements IPsyConsultWorkService {
    
    @Resource
    private PsyConsultWorkMapper psyConsultWorkMapper;

    @Override
    public List<PsyConsultWork> checkDataUnique(PsyConsultWorkVO req) {
        return psyConsultWorkMapper.checkDataUnique(req);
    }

    @Override
    public PsyConsultWorkVO getOne(Long id) {
        return BeanUtil.toBean(psyConsultWorkMapper.selectById(id), PsyConsultWorkVO.class);
    }

    @Override
    public List<PsyConsultWork> getList(PsyConsultWorkVO req) {
        req.setDelFlag("0");
        return psyConsultWorkMapper.getList(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultWorkVO req) {
        converTime(req);
        return psyConsultWorkMapper.insert(BeanUtil.toBean(req, PsyConsultWork.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultWorkVO req) {
        converTime(req);
        return psyConsultWorkMapper.updateById(BeanUtil.toBean(req, PsyConsultWork.class));
    }

    @Override
    public void updateNum(Long id, int num, int buyNum) {
        PsyConsultWorkVO one = getOne(id);
        one.setNum(num);
        one.setBuyNum(buyNum);
        update(one);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(Long[] ids) {
        return psyConsultWorkMapper.tombstonedByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return psyConsultWorkMapper.deleteById(id);
    }

    private void converTime(PsyConsultWorkVO req) {
        req.setTime(DateUtils.differentMinByMillisecond(req.getTimeStart(), req.getTimeEnd()));
        req.setDay(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, req.getTimeStart()));
        req.setWeek(getWeekOfDate(req.getTimeStart()));
    }

    /**
     * 获取当前日期是星期几
     *
     * @param date
     * @return 当前日期是星期几
     */
    private String getWeekOfDate(Date date) {
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
