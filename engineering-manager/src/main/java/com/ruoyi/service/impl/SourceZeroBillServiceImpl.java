package com.ruoyi.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.PageDto;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.entity.SourceZeroBill;
import com.ruoyi.excel.bo.ZeroBillExcel;
import com.ruoyi.excel.listener.ZeroBillListener;
import com.ruoyi.mapper.SourceZeroBillMapper;
import com.ruoyi.service.ISourceZeroBillService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 原数据---0号台账 服务实现类
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Service
public class SourceZeroBillServiceImpl extends ServiceImpl<SourceZeroBillMapper, SourceZeroBill> implements ISourceZeroBillService {

    @Override
    public void importData(MultipartFile file) throws IOException {
        ZeroBillListener listener = new ZeroBillListener();
        EasyExcel.read(file.getInputStream(), ZeroBillExcel.class,listener).sheet().doRead();
        List<ZeroBillExcel> cachedDataList = listener.getCachedDataList();
        System.out.println("end----");
        System.out.println(cachedDataList.size());
        System.out.println("============================================");
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Integer projectNo = 124;
        Integer companyNo = 456;
        List<SourceZeroBill> sourceZeroBills = new ArrayList<>();
        for (ZeroBillExcel zeroBillExcel : cachedDataList) {
            SourceZeroBill sourceZeroBill = new SourceZeroBill();
            BeanUtils.copyProperties(zeroBillExcel,sourceZeroBill);
            sourceZeroBill.setCompanyNo(companyNo);
            sourceZeroBill.setProjectNo(projectNo);
            LocalDateTime localDateTime = LocalDateTime.now();
            sourceZeroBill.setCreateTime(localDateTime);
            sourceZeroBill.setUpdateTime(localDateTime);
            sourceZeroBill.setCreateUser(loginUser.getUserId().toString());
            sourceZeroBill.setUpdateUser(loginUser.getUserId().toString());
            sourceZeroBill.setDeleted(Boolean.TRUE);
            sourceZeroBills.add(sourceZeroBill);
        }
        this.saveBatch(sourceZeroBills,1000);
    }

    @Override
    public List<SourceZeroBill> dataList() {
        return lambdaQuery().eq(SourceZeroBill::getProjectNo,124)
                .eq(SourceZeroBill::getDeleted,true)
                .list();
    }

    @Override
    public void saveOrUpdateData(SourceZeroBill sourceZeroBill) {
        LocalDateTime localDateTime = LocalDateTime.now();
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (null == loginUser){
            throw new RuntimeException("获取用户信息异常");
        }

        if (null == sourceZeroBill.getId()){
            // 新增
            sourceZeroBill.setCreateTime(localDateTime);
            sourceZeroBill.setUpdateTime(localDateTime);
            sourceZeroBill.setCreateUser(loginUser.getUserId().toString());
            sourceZeroBill.setUpdateUser(loginUser.getUserId().toString());
            sourceZeroBill.setDeleted(Boolean.TRUE);
            save(sourceZeroBill);
        }else {
            // 编辑
            sourceZeroBill.setUpdateTime(localDateTime);
            sourceZeroBill.setUpdateUser(loginUser.getUserId().toString());
            sourceZeroBill.setDeleted(Boolean.TRUE);
            updateById(sourceZeroBill);
        }
    }



}
