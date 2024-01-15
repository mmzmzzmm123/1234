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

import com.ruoyi.entity.SourceZeroBill;
import com.ruoyi.excel.listener.MixtureResolveListener;
import com.ruoyi.excel.bo.MixtureResolveExcel;
import com.ruoyi.mapper.SourceMixtureResolveMapper;
import com.ruoyi.req.SourcePageQeq;
import com.ruoyi.service.ISourceMixtureResolveService;
import com.ruoyi.vo.SourceMixtureResolveVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 原数据 --- 混合料分解 服务实现类
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Slf4j
@Service
public class SourceMixtureResolveServiceImpl extends ServiceImpl<SourceMixtureResolveMapper, SourceMixtureResolve> implements ISourceMixtureResolveService {

    @Override
    public void importData(MultipartFile file)  {
        MixtureResolveListener listener = new MixtureResolveListener();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("获取文件信息异常，异常信息:{}",e.getMessage(),e);
            throw new RuntimeException("获取文件信息异常");

        }
        EasyExcel.read(inputStream, MixtureResolveExcel.class,listener).sheet().doRead();
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
    public List<SourceMixtureResolveVO> dataList(SourcePageQeq req) {
        List<SourceMixtureResolve> list = lambdaQuery().eq(SourceMixtureResolve::getProjectNo, 124)
                .eq(SourceMixtureResolve::getDeleted, true)
                .like(SourceMixtureResolve::getItemNum,req.getItemNum())
                .like(SourceMixtureResolve::getItemName,req.getItemName())
                .list();
        if (CollectionUtils.isEmpty(list)){
            return new ArrayList<>();
        }
        Map<String, List<SourceMixtureResolve>> mixtureResolveMap = list.stream().collect(Collectors.groupingBy(SourceMixtureResolve::getItemNum));
        List<SourceMixtureResolveVO> sourceMixtureResolveVOS = new ArrayList<>();
        for (SourceMixtureResolve sourceMixtureResolve : list) {
            SourceMixtureResolveVO sourceMixtureResolveVO = new SourceMixtureResolveVO();
            List<SourceMixtureResolveVO.mixtureResolveVOBean> mixtureResolveVOBeans = new ArrayList<>();
            BeanUtils.copyProperties(sourceMixtureResolve,sourceMixtureResolveVO);
            List<SourceMixtureResolve> sourceMixtureResolves = mixtureResolveMap.get(sourceMixtureResolve.getItemNum());
            for (SourceMixtureResolve mixtureResolve : sourceMixtureResolves) {
                SourceMixtureResolveVO.mixtureResolveVOBean mixtureResolveVOBean = new SourceMixtureResolveVO.mixtureResolveVOBean();
                BeanUtils.copyProperties(mixtureResolve,mixtureResolveVOBean);
                mixtureResolveVOBeans.add(mixtureResolveVOBean);
            }
            sourceMixtureResolveVO.setSourceMixtureResolveVOBeanList(mixtureResolveVOBeans);
            sourceMixtureResolveVOS.add(sourceMixtureResolveVO);
        }
        return sourceMixtureResolveVOS;
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
