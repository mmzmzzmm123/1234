package com.ruoyi.project.data.price.compute.service.impl;

import java.util.List;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.data.price.compute.domain.OfficeBasePriceUltimate;
import com.ruoyi.project.data.price.compute.mapper.OfficeBasePriceUltimateMapper;
import com.ruoyi.project.data.price.compute.service.IOfficeBasePriceUltimateService;
import com.ruoyi.project.system.service.impl.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@Service

public class OfficeBasePriceUltimateServiceImpl implements IOfficeBasePriceUltimateService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private OfficeBasePriceUltimateMapper officeBasePriceUltimateMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param officeBasePriceUltimate 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<OfficeBasePriceUltimate> selectOfficeBasePriceUltimateList(OfficeBasePriceUltimate officeBasePriceUltimate) {
        return officeBasePriceUltimateMapper.selectOfficeBasePriceUltimateList(officeBasePriceUltimate);
    }

    @Override
    public int selectOfficeBasePriceUltimateListCount(OfficeBasePriceUltimate officeBasePriceUltimate) {
        return officeBasePriceUltimateMapper.selectOfficeBasePriceUltimateListCount(officeBasePriceUltimate);
    }

    @Override
    public OfficeBasePriceUltimate selectOfficeBasePriceUltimateById(String id) {
        return officeBasePriceUltimateMapper.selectOfficeBasePriceUltimateById(id);
    }

    @Override
    public int updateOfficeBasePriceUltimate(OfficeBasePriceUltimate officeBasePriceUltimate) {
        return officeBasePriceUltimateMapper.updateOfficeBasePriceUltimate(officeBasePriceUltimate);
    }

    @Override
    public String batchImport(List<OfficeBasePriceUltimate> officeBasePriceUltimates, String operName) {
        if (StringUtils.isNull(officeBasePriceUltimates) || officeBasePriceUltimates.size() == 0)
        {
            throw new CustomException("导入办公数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (OfficeBasePriceUltimate officeBasePriceUltimate : officeBasePriceUltimates)
        {
            try
            {
                // 验证是否存在这个用户
                OfficeBasePriceUltimate officeBasePriceUltimateInDb = officeBasePriceUltimateMapper.selectOfficeBasePriceUltimateById(officeBasePriceUltimate.getId());
                if (StringUtils.isNotNull(officeBasePriceUltimateInDb))
                {
                    this.updateOfficeBasePriceUltimate(officeBasePriceUltimate);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、ID= " + officeBasePriceUltimate.getId() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、ID= " + officeBasePriceUltimate.getId() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、ID= " + officeBasePriceUltimate.getId() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
