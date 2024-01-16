package com.onethinker.onethinker.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.ActivityTypeEnum;
import com.ruoyi.common.utils.DateUtils;

import com.onethinker.onethinker.domain.SysFileInfo;
import com.onethinker.onethinker.dto.ActivityResDTO;
import com.onethinker.onethinker.dto.RedEnvelopeCtrlDTO;
import com.onethinker.onethinker.service.ISysFileInfoService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.ruoyi.common.utils.StringUtils;
import com.onethinker.onethinker.dto.ActivityReqDTO;
import com.onethinker.onethinker.factory.ActivityDetailFactory;
import com.onethinker.onethinker.factory.service.IActivityDetailService;
import com.onethinker.onethinker.mapper.ActivityMapper;
import com.onethinker.onethinker.domain.Activity;
import com.onethinker.onethinker.service.IActivityService;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;

/**
 * 活动Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Service
@Log4j2
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper,Activity> implements IActivityService {
    @Resource
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityDetailFactory activityDetailFactory;

    @Autowired
    private ISysFileInfoService sysFileInfoService;

    /**
     * 查询活动
     *
     * @param id 活动主键
     * @return 活动
     */
    @Override
    public ActivityResDTO selectActivityById(Long id) {
        Activity activity = activityMapper.selectActivityById(id);
        ActivityResDTO resultDTO = new ActivityResDTO();
        BeanUtils.copyProperties(activity,resultDTO);
        // 获取活动详情内容
        IActivityDetailService activityDetailService = activityDetailFactory.queryActivityDetailByActivityType(activity.getActivityType());
        // 查看具体是什么
        if (ActivityTypeEnum.RED_ENVELOPE.getCode().equals(activity.getActivityType())) {
            RedEnvelopeCtrlDTO redEnvelopeCtrlDTO = new RedEnvelopeCtrlDTO();
            redEnvelopeCtrlDTO.setBatchNo(activity.getBatchNo());
            List<RedEnvelopeCtrlDTO> list = activityDetailService.queryRedEnvelopeCtrlByParams(redEnvelopeCtrlDTO);
            if (!list.isEmpty()) {
                resultDTO.setRedEnvelopeCtrlDTO(list.get(0));
            }
        }
        // 获取文件内容
        SysFileInfo sysFileInfo = sysFileInfoService.selectSysFileInfoByFileId(activity.getBgUrl());
        resultDTO.setBgUrlInfo(sysFileInfo);
        return resultDTO;
    }

    /**
     * 查询活动列表
     *
     * @param activity 活动
     * @return 活动
     */
    @Override
    public List<Activity> selectActivityList(Activity activity) {
        return activityMapper.selectActivityList(activity);
    }

    /**
     * 新增活动
     *
     * @param reqDTO 活动
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertActivity(ActivityReqDTO reqDTO) {
        // 常规校验
        reqDTO.existsParams();
        Activity activity = new Activity();
        BeanUtils.copyProperties(reqDTO,activity);
        activity.setCreateTime(DateUtils.getNowDate());
        activity.setWeight(System.currentTimeMillis());
        int i = activityMapper.insertActivity(activity);
        if (i == 0) {
            throw new RuntimeException("活动创建有误，请稍后再试");
        }
        // 保存活动明细数据
        IActivityDetailService activityDetailService = activityDetailFactory.queryActivityDetailByActivityType(reqDTO.getActivityType());
        int result = activityDetailService.saveEntry(reqDTO);
        if (result == 0) {
            throw new RuntimeException("活动明细创建有误，请稍后再试");
        }
        return result;
    }

    /**
     * 修改活动
     *
     * @param activity 活动
     * @return 结果
     */
    @Override
    public int updateActivity(Activity activity) {
        activity.setUpdateTime(DateUtils.getNowDate());
        return activityMapper.updateActivity(activity);
    }

    /**
     * 批量删除活动
     *
     * @param ids 需要删除的活动主键
     * @return 结果
     */
    @Override
    public int deleteActivityByIds(Long[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    /**
     * 删除活动信息
     *
     * @param id 活动主键
     * @return 结果
     */
    @Override
    public int deleteActivityById(Long id) {
        return activityMapper.deleteActivityById(id);
    }

    @Override
    public ActivityResDTO queryActivityMyBatisPuls(Long id) {
        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Activity::getId,id);
        Activity activity = activityMapper.selectOne(queryWrapper);
        ActivityResDTO activityResDTO = new ActivityResDTO();
        BeanUtils.copyProperties(activity,activityResDTO);
        return activityResDTO;
    }

}
