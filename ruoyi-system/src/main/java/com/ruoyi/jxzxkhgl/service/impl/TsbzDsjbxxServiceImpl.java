package com.ruoyi.jxzxkhgl.service.impl;

import java.util.List;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxzxkhgl.mapper.TsbzDsjbxxMapper;
import com.ruoyi.jxzxkhgl.domain.TsbzDsjbxx;
import com.ruoyi.jxzxkhgl.service.ITsbzDsjbxxService;

/**
 * 导师基本信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-10
 */
@Service
public class TsbzDsjbxxServiceImpl implements ITsbzDsjbxxService 
{
    @Autowired
    private TsbzDsjbxxMapper tsbzDsjbxxMapper;

    /**
     * 查询导师基本信息
     * 
     * @param id 导师基本信息ID
     * @return 导师基本信息
     */
    @Override
    public TsbzDsjbxx selectTsbzDsjbxxById(Long id)
    {
        return tsbzDsjbxxMapper.selectTsbzDsjbxxById(id);
    }

    /**
     * 查询导师基本信息列表
     * 
     * @param tsbzDsjbxx 导师基本信息
     * @return 导师基本信息
     */
    @Override
    public List<TsbzDsjbxx> selectTsbzDsjbxxList(TsbzDsjbxx tsbzDsjbxx)
    {
        return tsbzDsjbxxMapper.selectTsbzDsjbxxList(tsbzDsjbxx);
    }

    /**
     * 新增导师基本信息
     * 
     * @param tsbzDsjbxx 导师基本信息
     * @return 结果
     */
    @Override
    public int insertTsbzDsjbxx(TsbzDsjbxx tsbzDsjbxx)
    {
        tsbzDsjbxx.setCreateTime(DateUtils.getNowDate());
        return tsbzDsjbxxMapper.insertTsbzDsjbxx(tsbzDsjbxx);
    }

    /**
     * 修改导师基本信息
     * 
     * @param tsbzDsjbxx 导师基本信息
     * @return 结果
     */
    @Override
    public int updateTsbzDsjbxx(TsbzDsjbxx tsbzDsjbxx)
    {
        return tsbzDsjbxxMapper.updateTsbzDsjbxx(tsbzDsjbxx);
    }

    /**
     * 批量删除导师基本信息
     * 
     * @param ids 需要删除的导师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzDsjbxxByIds(Long[] ids)
    {
        return tsbzDsjbxxMapper.deleteTsbzDsjbxxByIds(ids);
    }

    /**
     * 删除导师基本信息信息
     * 
     * @param id 导师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzDsjbxxById(Long id)
    {
        return tsbzDsjbxxMapper.deleteTsbzDsjbxxById(id);
    }

    /**
     * 导入导师数据
     *
     * @param dsjbxxList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importDsjbxx(List<TsbzDsjbxx> dsjbxxList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(dsjbxxList) || dsjbxxList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        StringBuilder successMsg = new StringBuilder();
        for (TsbzDsjbxx tsbzDsjbxx : dsjbxxList)
        {
            tsbzDsjbxx.setCreateBy(operName);
            this.insertTsbzDsjbxx(tsbzDsjbxx);
            successNum++;
            successMsg.append("<br/>" + successNum + "、导师名称 " + tsbzDsjbxx.getJsxm() + " 导入成功");
        }
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        return successMsg.toString();
    }
}
