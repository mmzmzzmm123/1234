package com.ruoyi.project.data.price.service.impl;

import java.util.List;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import com.ruoyi.project.data.price.mapper.UltimateOfficeBasePriceMapper;
import com.ruoyi.project.data.price.service.IUltimateOfficeBasePriceService;
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
public class UltimateOfficeBasePriceServiceImpl implements IUltimateOfficeBasePriceService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private UltimateOfficeBasePriceMapper officeBasePriceUltimateMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param officeBasePriceUltimate 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<UltimateOfficeBasePrice> selectOfficeBasePriceUltimateList(UltimateOfficeBasePrice officeBasePriceUltimate) {
        return officeBasePriceUltimateMapper.selectOfficeBasePriceUltimateList(officeBasePriceUltimate);
    }

    @Override
    public int selectOfficeBasePriceUltimateListCount(UltimateOfficeBasePrice officeBasePriceUltimate) {
        return officeBasePriceUltimateMapper.selectOfficeBasePriceUltimateListCount(officeBasePriceUltimate);
    }

    @Override
    public UltimateOfficeBasePrice selectOfficeBasePriceUltimateById(String id) {
        return officeBasePriceUltimateMapper.selectOfficeBasePriceUltimateById(id);
    }

    @Override
    public int updateOfficeBasePriceUltimate(UltimateOfficeBasePrice officeBasePriceUltimate) {
        return officeBasePriceUltimateMapper.updateOfficeBasePriceUltimate(officeBasePriceUltimate);
    }

    @Override
    public String batchImport(List<UltimateOfficeBasePrice> officeBasePriceUltimates, String operName) {
        if (StringUtils.isNull(officeBasePriceUltimates) || officeBasePriceUltimates.size() == 0) {
            throw new CustomException("导入办公数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (UltimateOfficeBasePrice officeBasePriceUltimate : officeBasePriceUltimates) {
            try {
                // 验证是否存在这个用户
                UltimateOfficeBasePrice officeBasePriceUltimateInDb =
                        officeBasePriceUltimateMapper.selectOfficeBasePriceUltimateById(officeBasePriceUltimate.getId());
                if (StringUtils.isNotNull(officeBasePriceUltimateInDb)) {
                    this.updateOfficeBasePriceUltimate(officeBasePriceUltimate);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、ID= " + officeBasePriceUltimate.getId() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、ID= " + officeBasePriceUltimate.getId() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、ID= " + officeBasePriceUltimate.getId() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
