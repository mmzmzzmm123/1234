package com.ruoyi.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.PageDto;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.entity.SourceMixtureResolve;

import com.ruoyi.excel.listener.MixtureResolveListener;
import com.ruoyi.excel.bo.MixtureResolveExcel;
import com.ruoyi.mapper.SourceMixtureResolveMapper;
import com.ruoyi.service.ISourceMixtureResolveService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 原数据 --- 混合料分解 服务实现类
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Service
public class SourceMixtureResolveServiceImpl extends ServiceImpl<SourceMixtureResolveMapper, SourceMixtureResolve> implements ISourceMixtureResolveService {

    @Override
    public void importData(MultipartFile file) throws IOException {
        MixtureResolveListener listener = new MixtureResolveListener();
        EasyExcel.read(file.getInputStream(), MixtureResolveExcel.class,listener).sheet().doRead();
        List<MixtureResolveExcel> cachedDataList = listener.getCachedDataList();
        System.out.println("end----");
        System.out.println(cachedDataList.size());
        System.out.println("============================================");
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Integer projectNo = 124;
        Integer companyNo = 456;
        List<SourceMixtureResolve> sourceMixtureResolves = new ArrayList<>();
        MixtureResolveExcel lastMixtureResolveExcel = null;
        for (MixtureResolveExcel mixtureResolveExcel : cachedDataList) {
            SourceMixtureResolve sourceMixtureResolve = new SourceMixtureResolve();
            if (StringUtils.isEmpty(mixtureResolveExcel.getItemNum())) {
                mixtureResolveExcel.setItemNum(lastMixtureResolveExcel.getItemNum());
                mixtureResolveExcel.setItemName(lastMixtureResolveExcel.getItemName());
                mixtureResolveExcel.setPartUsed(lastMixtureResolveExcel.getPartUsed());
                mixtureResolveExcel.setSumQuantity(lastMixtureResolveExcel.getSumQuantity());
                mixtureResolveExcel.setCement325R(lastMixtureResolveExcel.getCement325R());
                mixtureResolveExcel.setCement425R(lastMixtureResolveExcel.getCement425R());
                mixtureResolveExcel.setCement430R(lastMixtureResolveExcel.getCement430R());
                mixtureResolveExcel.setWaterReducer(lastMixtureResolveExcel.getWaterReducer());
                mixtureResolveExcel.setSwellingAgent(lastMixtureResolveExcel.getSwellingAgent());
            } else {
                lastMixtureResolveExcel = mixtureResolveExcel;
            }
            BeanUtils.copyProperties(mixtureResolveExcel, sourceMixtureResolve);
            sourceMixtureResolve.setSpecificationsModel(sourceMixtureResolve.getMaterialsName());
            if (null !=sourceMixtureResolve.getSumQuantity() && null != sourceMixtureResolve.getProportion()){

                sourceMixtureResolve.setQuantity(sourceMixtureResolve.getSumQuantity().multiply(sourceMixtureResolve.getProportion()));
            }
            sourceMixtureResolve.setCompanyNo(companyNo);
            sourceMixtureResolve.setProjectNo(projectNo);
            LocalDateTime localDateTime = LocalDateTime.now();
            sourceMixtureResolve.setCreateTime(localDateTime);
            sourceMixtureResolve.setUpdateTime(localDateTime);
            sourceMixtureResolve.setCreateUser(loginUser.getUserId().toString());
            sourceMixtureResolve.setUpdateUser(loginUser.getUserId().toString());
            sourceMixtureResolve.setDeleted(Boolean.TRUE);
            sourceMixtureResolves.add(sourceMixtureResolve);
        }

        saveBatch(sourceMixtureResolves, 1000);
    }

    @Override
    public Page<SourceMixtureResolve> dataList(PageDto pageDto) {
        Page<SourceMixtureResolve> page = new Page<>(pageDto.getPageNum(),pageDto.getPageSize());
        return lambdaQuery().eq(SourceMixtureResolve::getProjectNo,124)
                .eq(SourceMixtureResolve::getDeleted,true)
                .page(page);
    }

    @Override
    public void saveOrUpdateData(SourceMixtureResolve sourceMixtureResolve) {
        LocalDateTime localDateTime = LocalDateTime.now();
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (null == loginUser){
            throw new RuntimeException("获取用户信息异常");
        }
        sourceMixtureResolve.setSpecificationsModel(sourceMixtureResolve.getMaterialsName());
        sourceMixtureResolve.setQuantity(sourceMixtureResolve.getSumQuantity().multiply(sourceMixtureResolve.getProportion()));
        if (null == sourceMixtureResolve.getId()){
            // 新增
            sourceMixtureResolve.setCreateTime(localDateTime);
            sourceMixtureResolve.setUpdateTime(localDateTime);
            sourceMixtureResolve.setCreateUser(loginUser.getUserId().toString());
            sourceMixtureResolve.setUpdateUser(loginUser.getUserId().toString());
            sourceMixtureResolve.setDeleted(Boolean.TRUE);
            save(sourceMixtureResolve);
        }else {
            // 编辑
            sourceMixtureResolve.setUpdateTime(localDateTime);
            sourceMixtureResolve.setUpdateUser(loginUser.getUserId().toString());
            sourceMixtureResolve.setDeleted(Boolean.TRUE);
            updateById(sourceMixtureResolve);
        }
    }
}
