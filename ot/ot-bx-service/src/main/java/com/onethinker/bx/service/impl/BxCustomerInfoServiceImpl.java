package com.onethinker.bx.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bx.domain.BxCustomerInfo;
import com.onethinker.bx.enums.CustormerTypeEnum;
import com.onethinker.bx.enums.DeliveryTypeEnum;
import com.onethinker.bx.enums.ProductTypeEnum;
import com.onethinker.bx.mapper.BxCustomerInfoMapper;
import com.onethinker.bx.service.IBxCustomerInfoService;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 佰兴-客户信息表Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-05-22
 */
@Service
@Log4j2
public class BxCustomerInfoServiceImpl extends ServiceImpl<BxCustomerInfoMapper, BxCustomerInfo> implements IBxCustomerInfoService {
    @Resource
    private BxCustomerInfoMapper bxCustomerInfoMapper;

    @Resource
    private RedisCache redisCache;

    /**
     * 查询佰兴-客户信息表
     *
     * @param id 佰兴-客户信息表主键
     * @return 佰兴-客户信息表
     */
    @Override
    public BxCustomerInfo selectBxCustomerInfoById(Long id) {
        BxCustomerInfo redis = queryCustomerById(id);
        if (Objects.nonNull(redis)) {
            return redis;
        }
        BxCustomerInfo bxCustomerInfo = bxCustomerInfoMapper.selectBxCustomerInfoById(id);
        if (!StringUtils.isEmpty(bxCustomerInfo.getCustomerType())) {
            bxCustomerInfo.setCustomerType(CustormerTypeEnum.valueDesc(bxCustomerInfo.getCustomerType()).name());
        }
        if (!StringUtils.isEmpty(bxCustomerInfo.getDeliveryType())) {
            bxCustomerInfo.setDeliveryType(DeliveryTypeEnum.valueDesc(bxCustomerInfo.getDeliveryType()).name());
        }
        if (!StringUtils.isEmpty(bxCustomerInfo.getProductType())) {
            String customerType = Arrays.stream(bxCustomerInfo.getProductType().split(",")).map(e -> ProductTypeEnum.valueDesc(e).name()).collect(Collectors.joining(","));
            bxCustomerInfo.setProductType(customerType);
        }
        setRedisCacheById(id,bxCustomerInfo);
        return bxCustomerInfo;
    }




    /**
     * 查询佰兴-客户信息表列表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 佰兴-客户信息表
     */
    @Override
    public List<BxCustomerInfo> selectBxCustomerInfoList(BxCustomerInfo bxCustomerInfo) {
        List<BxCustomerInfo> bxCustomerInfos = bxCustomerInfoMapper.selectBxCustomerInfoList(bxCustomerInfo);
        return bxCustomerInfos.stream().peek(e -> {
            if (!StringUtils.isEmpty(e.getCustomerType())) {
                e.setCustomerType(CustormerTypeEnum.valueDesc(e.getCustomerType()).name());
            }
            if (!StringUtils.isEmpty(e.getDeliveryType())) {
                e.setDeliveryType(DeliveryTypeEnum.valueDesc(e.getDeliveryType()).name());
            }
            if (!StringUtils.isEmpty(e.getProductType())) {
                String customerType = Arrays.stream(e.getProductType().split(",")).map(e1 -> ProductTypeEnum.valueDesc(e1).name()).collect(Collectors.joining(","));
                e.setProductType(customerType);
            }
        }).collect(Collectors.toList());
    }

    /**
     * 新增佰兴-客户信息表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 结果
     */
    @Override
    public int insertBxCustomerInfo(BxCustomerInfo bxCustomerInfo) {
        bxCustomerInfo.setCreateTime(DateUtils.getNowDate());
        if (!StringUtils.isEmpty(bxCustomerInfo.getCustomerType())) {
            String desc = CustormerTypeEnum.valueOf(bxCustomerInfo.getCustomerType()).getDesc();
            bxCustomerInfo.setCustomerType(desc);
        }
        if (!StringUtils.isEmpty(bxCustomerInfo.getDeliveryType())) {
            String desc = DeliveryTypeEnum.valueOf(bxCustomerInfo.getDeliveryType()).getDesc();
            bxCustomerInfo.setDeliveryType(desc);
        }
        if (!StringUtils.isEmpty(bxCustomerInfo.getProductType())) {
            String customerType = Arrays.stream(bxCustomerInfo.getProductType().split(",")).map(e -> ProductTypeEnum.valueOf(e).getDesc()).collect(Collectors.joining(","));
            bxCustomerInfo.setProductType(customerType);
        }
        return bxCustomerInfoMapper.insertBxCustomerInfo(bxCustomerInfo);
    }

    /**
     * 修改佰兴-客户信息表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 结果
     */
    @Override
    public int updateBxCustomerInfo(BxCustomerInfo bxCustomerInfo) {
        bxCustomerInfo.setUpdateTime(DateUtils.getNowDate());
        if (!StringUtils.isEmpty(bxCustomerInfo.getCustomerType())) {
            String desc = CustormerTypeEnum.valueOf(bxCustomerInfo.getCustomerType()).getDesc();
            bxCustomerInfo.setCustomerType(desc);
        }
        if (!StringUtils.isEmpty(bxCustomerInfo.getDeliveryType())) {
            String desc = DeliveryTypeEnum.valueOf(bxCustomerInfo.getDeliveryType()).getDesc();
            bxCustomerInfo.setDeliveryType(desc);
        }
        if (!StringUtils.isEmpty(bxCustomerInfo.getProductType())) {
            String customerType = Arrays.stream(bxCustomerInfo.getProductType().split(",")).map(e -> ProductTypeEnum.valueOf(e).getDesc()).collect(Collectors.joining(","));
            bxCustomerInfo.setProductType(customerType);
        }
        // 删除缓存
        deleteRedisByCustomerById(bxCustomerInfo.getId());
        return bxCustomerInfoMapper.updateBxCustomerInfo(bxCustomerInfo);
    }



    /**
     * 批量删除佰兴-客户信息表
     *
     * @param ids 需要删除的佰兴-客户信息表主键
     * @return 结果
     */
    @Override
    public int deleteBxCustomerInfoByIds(Long[] ids) {
        // 删除缓存
        Arrays.stream(ids).forEach(this::deleteRedisByCustomerById);
        return bxCustomerInfoMapper.deleteBxCustomerInfoByIds(ids);
    }

    /**
     * 删除佰兴-客户信息表信息
     *
     * @param id 佰兴-客户信息表主键
     * @return 结果
     */
    @Override
    public int deleteBxCustomerInfoById(Long id) {
        // 删除缓存
        deleteRedisByCustomerById(id);
        return bxCustomerInfoMapper.deleteBxCustomerInfoById(id);
    }

    @Override
    public void saveEntry(List<BxCustomerInfo> bxCustomerInfos) {
        bxCustomerInfos.parallelStream().filter(e -> !executeBatch(e)).forEach(bxCustomerInfo -> {
            if (Objects.isNull(bxCustomerInfo.getDate())) {
                bxCustomerInfo.setCreateTime(new Date());
            } else {
                bxCustomerInfo.setCreateTime(bxCustomerInfo.getDate());
            }
            bxCustomerInfo.setCreateBy("onethinker");
            if (!StringUtils.isEmpty(bxCustomerInfo.getDeliveryType()) && bxCustomerInfo.getDeliveryType().length() >= DeliveryTypeEnum.SF.getDesc().length()) {
                if (bxCustomerInfo.getDeliveryType().contains(DeliveryTypeEnum.SF_SHORT.getDesc())) {
                    bxCustomerInfo.setDeliveryType(DeliveryTypeEnum.SF.getDesc());
                } else {
                    bxCustomerInfo.setDeliveryType(DeliveryTypeEnum.RG.getDesc());
                }
            } else {
                bxCustomerInfo.setDeliveryType(bxCustomerInfo.getDeliveryType().trim());
            }
            if (!StringUtils.isEmpty(bxCustomerInfo.getProductType())) {
                bxCustomerInfo.setProductType(bxCustomerInfo.getProductType().replaceAll("、",","));
            }
            setRedisCache(bxCustomerInfo);
            try {
                bxCustomerInfoMapper.insertBxCustomerInfo(bxCustomerInfo);
            }catch (Exception e) {
                log.error("保存报错啦！！！！" + JSONObject.toJSONString(bxCustomerInfo));
            }

        });

    }

    @Override
    public List<BxCustomerInfo> export(BxCustomerInfo bxCustomerInfo) {
        List<BxCustomerInfo> bxCustomerInfos = bxCustomerInfoMapper.selectBxCustomerInfoList(bxCustomerInfo);
        return bxCustomerInfos.stream().peek(e -> e.setDate(e.getCreateTime())).collect(Collectors.toList());
    }

    private boolean executeBatch(BxCustomerInfo bxCustomerInfo) {
        String key = CacheEnum.BX_CUSTOMER_INFO_KEY.getCode() + bxCustomerInfo.getCompanyName() + "_" + bxCustomerInfo.getMobile();
        // 查看redis是否存在数据
        return redisCache.hasKey(key);
    }

    private void setRedisCache(BxCustomerInfo bxCustomerInfo) {
        String key = CacheEnum.BX_CUSTOMER_INFO_KEY.getCode() + bxCustomerInfo.getCompanyName() + "_" + bxCustomerInfo.getMobile();
        // 查看redis是否存在数据
        redisCache.setCacheObject(key,bxCustomerInfo);
    }

    private BxCustomerInfo queryCustomerById(Long id) {
        String key = CacheEnum.BX_CUSTOMER_INFO_KEY.getCode() + id;
        return redisCache.getCacheObject(key);
    }

    private void setRedisCacheById(Long id, BxCustomerInfo bxCustomerInfo) {
        String key = CacheEnum.BX_CUSTOMER_INFO_KEY.getCode() + id;
        redisCache.setCacheObject(key,bxCustomerInfo);
    }

    private void deleteRedisByCustomerById(Long id) {
        String key = CacheEnum.BX_CUSTOMER_INFO_KEY.getCode() + id;
        redisCache.deleteObject(key);
    }
}
