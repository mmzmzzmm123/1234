package com.stdiet.custom.service.impl;

import java.util.List;

import com.stdiet.common.core.domain.model.LoginUser;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SecurityUtils;
import com.stdiet.common.utils.bean.ObjectUtils;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.dto.request.CustomerInvestigateRequest;
import com.stdiet.custom.mapper.SysCustomerPhysicalSignsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysCustomerMapper;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.service.ISysCustomerService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户信息Service业务层处理
 *
 * @author xzj
 * @date 2020-12-31
 */
@Service
@Transactional
public class SysCustomerServiceImpl implements ISysCustomerService
{
    @Autowired
    private SysCustomerMapper sysCustomerMapper;

    @Autowired
    private SysCustomerPhysicalSignsMapper sysCustomerPhysicalSignsMapper;

    /**
     * 查询客户信息
     *
     * @param id 客户信息ID
     * @return 客户信息
     */
    @Override
    public SysCustomer selectSysCustomerById(Long id)
    {
        return sysCustomerMapper.selectSysCustomerById(id);
    }

    /**
     * 查询客户信息列表
     *
     * @param sysCustomer 客户信息
     * @return 客户信息
     */
    @Override
    public List<SysCustomer> selectSysCustomerList(SysCustomer sysCustomer)
    {
        return sysCustomerMapper.selectSysCustomerList(sysCustomer);
    }

    /**
     * 新增客户信息
     *
     * @param sysCustomer 客户信息
     * @return 结果
     */
    @Override
    public int insertSysCustomer(SysCustomer sysCustomer)
    {
        sysCustomer.setCreateTime(DateUtils.getNowDate());
        return sysCustomerMapper.insertSysCustomer(sysCustomer);
    }

    /**
     * 修改客户信息
     *
     * @param sysCustomer 客户信息
     * @return 结果
     */
    @Override
    public int updateSysCustomer(SysCustomer sysCustomer)
    {
        sysCustomer.setUpdateTime(DateUtils.getNowDate());
        return sysCustomerMapper.updateSysCustomer(sysCustomer);
    }

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerByIds(Long[] ids)
    {
        return sysCustomerMapper.deleteSysCustomerByIds(ids);
    }

    /**
     * 删除客户信息信息
     *
     * @param id 客户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerById(Long id)
    {
        return sysCustomerMapper.deleteSysCustomerById(id);
    }

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return 结果
     */
    public SysCustomer getCustomerByPhone(String phone){
        return sysCustomerMapper.getCustomerByPhone(phone);
    }

    /**
     * 客户建档资料填写
     *
     * @param customerInvestigateRequest 客户建档相关资料
     * @return 结果
     */
    public int addOrupdateCustomerAndSign(CustomerInvestigateRequest customerInvestigateRequest) throws Exception{
        //当前登录用户
        //LoginUser loginUser = SecurityUtils.getLoginUser();
        SysCustomer sysCustomer  = new SysCustomer();
        sysCustomer.setId(customerInvestigateRequest.getId());
        sysCustomer.setName(customerInvestigateRequest.getName());
        sysCustomer.setPhone(customerInvestigateRequest.getPhone());
        //添加人、更新人
        /*if(loginUser != null){
            if(customerInvestigateRequest.getId() == null){
                sysCustomer.setCreateBy(loginUser.getUser().getUserId()+"");
            }else{
                sysCustomer.setUpdateBy(loginUser.getUser().getUserId()+"");
            }
        }*/
        //sysCustomer.setEmail(customerInvestigateRequest.getEmail());
        //sysCustomer.setAddress(customerInvestigateRequest.getAddress());
        int addOrUpdateRow = customerInvestigateRequest.getId() == null ? insertSysCustomer(sysCustomer) : updateSysCustomer(sysCustomer);
        if(addOrUpdateRow > 0){
            SysCustomerPhysicalSigns customerSigns = ObjectUtils.getObjectByObject(customerInvestigateRequest, SysCustomerPhysicalSigns.class);
            customerSigns.setCustomerId(sysCustomer.getId());
            customerSigns.setId(null);
            addOrUpdateRow = customerInvestigateRequest.getId() == null ? sysCustomerPhysicalSignsMapper.insertSysCustomerPhysicalSigns(customerSigns) : sysCustomerPhysicalSignsMapper.updateSysCustomerPhysicalSignsByCustomerId(customerSigns);
        }
        return addOrUpdateRow;
    }

    /**
     *  查询客户基础信息以及体征信息列表
     *
     * @param sysCustomer 查询条件
     * @return 结果
     */
    public List<SysCustomer> selectSysCustomerAndSignList(SysCustomer sysCustomer){
        return sysCustomerMapper.selectSysCustomerAndSignList(sysCustomer);
    }

    /**
     *  根据id查询客户信息（基础信息以及体征信息）
     *
     * @param id 客户id
     * @return 结果
     */
    public SysCustomer getCustomerAndSignById(Long id){
        SysCustomer sysCustomer = new SysCustomer();
        sysCustomer.setId(id);
        List<SysCustomer> list = sysCustomerMapper.selectSysCustomerAndSignList(sysCustomer);
        return list.size() > 0 ? list.get(0) : null;
    }

    public int delCustomerAndSignById(Long[] ids){
        if(deleteSysCustomerByIds(ids) > 0){
            return sysCustomerPhysicalSignsMapper.deleteSysCustomerPhysicalSignsByCustomerIds(ids);
        }
        return 0;
    }
}