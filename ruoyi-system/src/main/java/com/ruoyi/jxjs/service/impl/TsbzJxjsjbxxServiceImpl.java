package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxjsjbxxMapper;
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;
import com.ruoyi.jxjs.service.ITsbzJxjsjbxxService;

/**
 * 见习教师基本信息Service业务层处理
 *
 * @author ruoyi
 * @date 2020-08-20
 */
@Service
public class TsbzJxjsjbxxServiceImpl implements ITsbzJxjsjbxxService {
    private static final Logger log = LoggerFactory.getLogger(TsbzJxjscjServiceImpl.class);
    @Autowired
    private TsbzJxjsjbxxMapper tsbzJxjsjbxxMapper;

    /**
     * 查询见习教师基本信息
     *
     * @param id 见习教师基本信息ID
     * @return 见习教师基本信息
     */
    @Override
    public TsbzJxjsjbxx selectTsbzJxjsjbxxById(Long id) {
        return tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxById(id);
    }

    // 通过进修编号查询教师基本信息
    @Override
    public TsbzJxjsjbxx selectTsbzJxjsjbxxByJxbh(String jxbh) {
        return tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxByJxbh(jxbh);
    }

    /**
     * 查询见习教师基本信息列表
     *
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 见习教师基本信息
     */
    @Override
    public List<TsbzJxjsjbxx> selectTsbzJxjsjbxxList(TsbzJxjsjbxx tsbzJxjsjbxx) {
        return tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxList(tsbzJxjsjbxx);
    }

    /**
     * 查询见习教师基本信息列表
     *
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 见习教师基本信息
     */
    @Override
    public List<TsbzJxjsjbxx> selectTsbzJxjsjbxxListnotjdx(TsbzJxjsjbxx tsbzJxjsjbxx) {
        return tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxListnotjdx(tsbzJxjsjbxx);
    }

    /**
     * 查询见习教师基本信息列表
     *
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 见习教师基本信息集合
     */
    @Override
    public List<TsbzJxjsjbxx> selectTsbzJxjsjbxxListnotjdcx(TsbzJxjsjbxx tsbzJxjsjbxx) {
        return tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxListnotjdcx(tsbzJxjsjbxx);
    }

    /**
     * 新增见习教师基本信息
     *
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 结果
     */
    @Override
    public int insertTsbzJxjsjbxx(TsbzJxjsjbxx tsbzJxjsjbxx) {
        tsbzJxjsjbxx.setCreateTime(DateUtils.getNowDate());
        return tsbzJxjsjbxxMapper.insertTsbzJxjsjbxx(tsbzJxjsjbxx);
    }

    /**
     * 修改见习教师基本信息
     *
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 结果
     */
    @Override
    public int updateTsbzJxjsjbxx(TsbzJxjsjbxx tsbzJxjsjbxx) {
        return tsbzJxjsjbxxMapper.updateTsbzJxjsjbxx(tsbzJxjsjbxx);
    }

    /**
     * 清空见习教师基本信息基地校id
     *
     * @param id 见习教师基本信息ID
     * @return 结果
     */
    @Override
    public int clearTsbzJxjsjdx(Long id){
        return tsbzJxjsjbxxMapper.clearTsbzJxjsjdx(id);
    }

    /**
     * 批量删除见习教师基本信息
     *
     * @param ids 需要删除的见习教师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjsjbxxByIds(Long[] ids) {
        return tsbzJxjsjbxxMapper.deleteTsbzJxjsjbxxByIds(ids);
    }

    /**
     * 删除见习教师基本信息信息
     *
     * @param id 见习教师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjsjbxxById(Long id) {
        return tsbzJxjsjbxxMapper.deleteTsbzJxjsjbxxById(id);
    }

    // 导入见习教师
    @Override
    public String importUser(List<TsbzJxjsjbxx> tsbzJxjsjbxxList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(tsbzJxjsjbxxList) || tsbzJxjsjbxxList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TsbzJxjsjbxx tsbzJxjsjbxx : tsbzJxjsjbxxList)
        {
            try
            {
                // 验证是否存在这个用户
                TsbzJxjsjbxx u = tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxByJxbh(tsbzJxjsjbxx.getJxbh());
                if (StringUtils.isNull(u))
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + tsbzJxjsjbxx.getName() + " 导入失败");
                }
                else if (isUpdateSupport)
                {
                    tsbzJxjsjbxx.setId(u.getId());
                    tsbzJxjsjbxx.setJdxid(u.getJdxid());
                    this.updateTsbzJxjsjbxx(tsbzJxjsjbxx);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + tsbzJxjsjbxx.getName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + tsbzJxjsjbxx.getName() + " 导入失败");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + tsbzJxjsjbxx.getName() + " 导入失败：";
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








































