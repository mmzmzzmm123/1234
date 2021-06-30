package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysCustomerHealthy;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.mapper.SysCustomerMapper;
import com.stdiet.custom.mapper.SysCustomerPhysicalSignsMapper;
import com.stdiet.custom.mapper.SysWxUserInfoMapper;
import com.stdiet.custom.service.ISysCustomerService;
import com.stdiet.custom.service.ISysWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户信息Service业务层处理
 *
 * @author xzj
 * @date 2020-12-31
 */
@Service
@Transactional
public class SysCustomerServiceImpl implements ISysCustomerService {
    @Autowired
    private SysCustomerMapper sysCustomerMapper;

    @Autowired
    private SysCustomerPhysicalSignsMapper sysCustomerPhysicalSignsMapper;

    @Autowired
    private SysCustomerHealthyServiceImpl sysCustomerHealthyService;

    @Autowired
    private SysCustomerPhysicalSignsServiceImpl sysCustomerPhysicalSignsService;

    @Autowired
    private ISysWxUserInfoService sysWxUserInfoService;

    /**
     * 查询客户信息
     *
     * @param id 客户信息ID
     * @return 客户信息
     */
    @Override
    public SysCustomer selectSysCustomerById(Long id) {
        return sysCustomerMapper.selectSysCustomerById(id);
    }

    /**
     * 查询客户信息列表
     *
     * @param sysCustomer 客户信息
     * @return 客户信息
     */
    @Override
    public List<SysCustomer> selectSysCustomerList(SysCustomer sysCustomer) {
        return sysCustomerMapper.selectSysCustomerList(sysCustomer);
    }

    /**
     * 新增客户信息
     *
     * @param sysCustomer 客户信息
     * @return 结果
     */
    @Override
    public int insertSysCustomer(SysCustomer sysCustomer) {
        sysCustomer.setCreateTime(DateUtils.getNowDate());
        int row = sysCustomerMapper.insertSysCustomer(sysCustomer);
        if(row > 0){
            updateWxInfoMessage(sysCustomer);
        }
        return row;
    }

    /**
     * 修改客户信息
     *
     * @param sysCustomer 客户信息
     * @return 结果
     */
    @Override
    public int updateSysCustomer(SysCustomer sysCustomer) {
        sysCustomer.setUpdateTime(DateUtils.getNowDate());
        int row = sysCustomerMapper.updateSysCustomer(sysCustomer);
        if(row > 0){
            updateWxInfoMessage(sysCustomer);
        }
        return row;
    }

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerByIds(Long[] ids) {
        return sysCustomerMapper.deleteSysCustomerByIds(ids);
    }

    /**
     * 删除客户信息信息
     *
     * @param id 客户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerById(Long id) {
        return sysCustomerMapper.deleteSysCustomerById(id);
    }

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return 结果
     */
    public SysCustomer getCustomerByPhone(String phone) {
        return sysCustomerMapper.getCustomerByPhone(phone);
    }

    /**
     * 判断客户手机号是否已存在
     *
     * @param sysCustomer
     * @return
     */
    public boolean isCustomerExistByPhone(SysCustomer sysCustomer) {
        if (sysCustomer.getId() != null) {
            if (StringUtils.isNotEmpty(sysCustomer.getPhone())) {
                SysCustomer phoneCustomer = getCustomerByPhone(sysCustomer.getPhone());
                return phoneCustomer != null && phoneCustomer.getId().intValue() != sysCustomer.getId().intValue();
            }
        } else {
            if (StringUtils.isNotEmpty(sysCustomer.getPhone())) {
                return getCustomerByPhone(sysCustomer.getPhone()) != null;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getPhysicalSignsById(Long id) {
        Map<String, Object> result = new HashMap<>();

        SysCustomer customer = sysCustomerMapper.selectSysCustomerById(id);
        result.put("customerInfo", customer);

        String key = "customerHealthy";
        result.put("type", 0);
        //查询健康评估信息
        SysCustomerHealthy sysCustomerHealthy = sysCustomerHealthyService.selectSysCustomerHealthyByCustomerId(id);
        if (sysCustomerHealthy != null) {
           /* if (StringUtils.isNotEmpty(sysCustomerHealthy.getPhone())) {
                sysCustomerHealthy.setPhone(StringUtils.hiddenPhoneNumber(sysCustomerHealthy.getPhone()));
            }*/
            result.put(key, sysCustomerHealthy);
        } else {
            //查询体征信息
            SysCustomerPhysicalSigns sysCustomerPhysicalSigns = sysCustomerPhysicalSignsService.selectSysCustomerPhysicalSignsByCusId(id);
            if (sysCustomerPhysicalSigns != null) {
               /* if (StringUtils.isNotEmpty(sysCustomerPhysicalSigns.getPhone())) {
                    sysCustomerPhysicalSigns.setPhone(StringUtils.hiddenPhoneNumber(sysCustomerPhysicalSigns.getPhone()));
                }*/
                result.put("type", 1);
            }
            result.put(key, sysCustomerPhysicalSigns);
        }
        //对ID进行加密
        result.put("enc_id", id != null ? AesUtils.encrypt(id + "", null) : "");
        return result;
    }

    @Override
    public Map<String, Object> getPhysicalSignsByOutId(String id) {
        return null;
    }

    /**
     * 根据openid查询客户信息
     * @param openid
     * @return
     */
    public SysCustomer getCustomerByOpenId(String openid){
        return sysCustomerMapper.getCustomerByOpenId(openid);
    }

    @Async
    public void updateWxInfoMessage(SysCustomer newCustomer){
        if(newCustomer == null || newCustomer.getId() == null || StringUtils.isEmpty(newCustomer.getPhone())){
            return;
        }
        //先根据cusId查询是否已经绑定过微信用户
        SysWxUserInfo cusIdWxUserInfo = sysWxUserInfoService.selectSysWxUserInfoByCusId(newCustomer.getId());
        //根据手机号查询微信用户记录
        SysWxUserInfo phoneWxUserInfo = sysWxUserInfoService.getSysWxUserInfoByPhone(newCustomer.getPhone());
        if(cusIdWxUserInfo != null) {
            if(phoneWxUserInfo != null){
                if(!cusIdWxUserInfo.getOpenid().equals(phoneWxUserInfo.getOpenid())){
                    //解绑之前记录
                    sysWxUserInfoService.removeCusIdByOpenId(cusIdWxUserInfo.getOpenid());
                    //更新
                    SysWxUserInfo param = new SysWxUserInfo();
                    param.setOpenid(phoneWxUserInfo.getOpenid());
                    param.setCusId(newCustomer.getId());
                    sysWxUserInfoService.updateSysWxUserInfo(param);
                }
            }else{
                //解绑之前记录
                sysWxUserInfoService.removeCusIdByOpenId(cusIdWxUserInfo.getOpenid());
            }
        }else{
            if(phoneWxUserInfo != null){
                //更新
                SysWxUserInfo param = new SysWxUserInfo();
                param.setOpenid(phoneWxUserInfo.getOpenid());
                param.setCusId(newCustomer.getId());
                sysWxUserInfoService.updateSysWxUserInfo(param);
            }
        }
    }
}