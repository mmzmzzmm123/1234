package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.NewDateUtil;
import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.dto.HeaderDTO;
import com.ruoyi.psychology.dto.WorkDTO;
import com.ruoyi.psychology.mapper.PsyConsultWorkMapper;
import com.ruoyi.psychology.request.PsyConsultWorkReq;
import com.ruoyi.psychology.request.PsyWorkReq;
import com.ruoyi.psychology.service.IPsyConsultWorkService;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PsyConsultWorkServiceImpl extends ServiceImpl<PsyConsultWorkMapper, PsyConsultWork> implements IPsyConsultWorkService {
    
    @Resource
    private PsyConsultWorkMapper psyConsultWorkMapper;

    @Override
    public void doSave(PsyConsultWorkReq req) {
        long num = NewDateUtil.getTwoDateDays(req.getStartDay(), req.getEndDay()) + 1;
        List<Integer> days = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            days.add(i);
        }

        List<PsyConsultWork> list = new ArrayList<>();
        List<String> dayDelt = new ArrayList<>();
        req.getIds().forEach(p -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(req.getStartDay());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            days.forEach(i -> {
                if (i > 1) {
                    calendar.add(Calendar.DATE, 1);
                }

                String fm = sdf.format(calendar.getTime());
                dayDelt.add(fm);

                PsyConsultWork work = new PsyConsultWork();
                work.setConsultId(p);
                work.setDay(fm);
                work.setWeek(NewDateUtil.getWeekOfDate(calendar));
                work.setTimeStart(req.getTimeStart());
                work.setTimeEnd(req.getTimeEnd());
                work.setStatus(req.getStatus());
                makeTime(work);
                list.add(work);
            });
        });

        // 先删除,后新增
        LambdaQueryWrapper<PsyConsultWork> wp = new LambdaQueryWrapper<>();
        wp.in(PsyConsultWork::getConsultId, req.getIds());
        wp.in(PsyConsultWork::getDay, dayDelt);
        this.remove(wp);

        this.saveBatch(list);
    }

    private void makeTime(PsyConsultWork req) {
        List<Integer> num = new ArrayList<>();
        if ("0".equals(req.getStatus())) {
            int s = Integer.parseInt(req.getTimeStart().substring(0, 2));
            int e = Integer.parseInt(req.getTimeEnd().substring(0, 2));
            for (int i = s; i <= e; i++) {
                num.add(i);
            }
        }

        req.setLive(JSONObject.toJSONString(num));
        req.setDeath(JSONObject.toJSONString(new ArrayList<>()));
    }

    @Override
    public WorkDTO getWorks(PsyWorkReq req) {
        WorkDTO res = new WorkDTO();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<HeaderDTO> headers = new ArrayList<>();
        HeaderDTO header1 = new HeaderDTO();
        header1.setLabel("咨询师");
        header1.setProp("nickName");
        headers.add(header1);

        if ("1".equals(req.getMonth())) {
            c.add(Calendar.MONTH, -1);
        } else if ("3".equals(req.getMonth())) {
            c.add(Calendar.MONTH, 1);
        }

        int max_day_of_month = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 1; i <= max_day_of_month; i++) {
            if (i > 1) {
                c.add(Calendar.DATE, 1);
            }

            String fm = sdf.format(c.getTime());
            if (i == 1) {
                req.setStart(fm);
            }
            if (i == max_day_of_month) {
                req.setEnd(fm);
            }

            HeaderDTO headerItem = new HeaderDTO();
            headerItem.setLabel(StrUtil.format("{}({})", fm.substring(5, 10), NewDateUtil.getWeekOfDate(c)));
            headerItem.setProp(fm);
            headers.add(headerItem);
        }
        res.setHeaders(headers);

        List<PsyConsultWorkVO> works = psyConsultWorkMapper.getWorks(req);
        if (CollectionUtils.isEmpty(works)) {
            return res;
        }

        List<HashMap<String, String>> items = new ArrayList<>();

        Map<Long, List<PsyConsultWorkVO>> collect = works.stream().collect(Collectors.groupingBy(PsyConsultWorkVO::getConsultId));
        for (Map.Entry<Long, List<PsyConsultWorkVO>> item: collect.entrySet()) {
            List<PsyConsultWorkVO> value = item.getValue();
            HashMap<String, String> node = new HashMap<>();
            node.put("id", String.valueOf(value.get(0).getConsultId()));
            node.put("nickName", value.get(0).getNickName());
            node.put("userName", StrUtil.format("(系统账号:{})", value.get(0).getUserName()));

            value.forEach(a -> {
                String v = "未排班";
                if ("0".equals(a.getStatus())) {
                    v = StrUtil.format("{}-{}", a.getTimeStart(), a.getTimeEnd());
                } else if ("1".equals(a.getStatus())) {
                    v = "休息";
                }
                node.put(a.getDay(), v);
            });

            items.add(node);
        }
        res.setItems(items);

        return res;
    }

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
        update(one);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return psyConsultWorkMapper.deleteById(id);
    }

    private void converTime(PsyConsultWorkVO req) {
//        req.setDay(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, req.getTimeStart()));
//        req.setWeek(getWeekOfDate(req.getTimeStart()));
    }
}
