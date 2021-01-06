package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;
import com.ruoyi.jxjs.mapper.TsbzJxjsjbxxMapper;
import com.ruoyi.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxjscjMapper;
import com.ruoyi.jxjs.domain.TsbzJxjscj;
import com.ruoyi.jxjs.service.ITsbzJxjscjService;

/**
 * 见习教师成绩Service业务层处理
 *
 * @author ruoyi
 * @date 2020-11-09
 */
@Service
public class TsbzJxjscjServiceImpl implements ITsbzJxjscjService {

    private static final Logger log = LoggerFactory.getLogger(TsbzJxjscjServiceImpl.class);
    @Autowired
    private TsbzJxjscjMapper tsbzJxjscjMapper;

    @Autowired
    private TsbzJxjsjbxxMapper tsbzJxjsjbxxMapper;


    @Override
    public TsbzJxjscj selectTsbzJxjscjById(Long id) {
        return tsbzJxjscjMapper.selectTsbzJxjscjById(id);
    }

    // 根据名称查询
    @Override
    public TsbzJxjscj selectTsbzJxjscjByJsid(Long jsid) {
        return tsbzJxjscjMapper.selectTsbzJxjscjByJsid(jsid);
    }

    /**
     * 查询见习教师成绩列表
     *
     * @param tsbzJxjscj 见习教师成绩
     * @return 见习教师成绩
     */
    @Override
    public List<TsbzJxjscj> selectTsbzJxjscjList(TsbzJxjscj tsbzJxjscj) {
        return tsbzJxjscjMapper.selectTsbzJxjscjList(tsbzJxjscj);
    }

    /**
     * 新增见习教师成绩
     *
     * @param tsbzJxjscj 见习教师成绩
     * @return 结果
     */
    @Override
    public int insertTsbzJxjscj(TsbzJxjscj tsbzJxjscj) {
        tsbzJxjscj.setCreateTime(DateUtils.getNowDate());
        return tsbzJxjscjMapper.insertTsbzJxjscj(tsbzJxjscj);
    }

    /**
     * 修改见习教师成绩
     *
     * @param tsbzJxjscj 见习教师成绩
     * @return 结果
     */
    @Override
    public int updateTsbzJxjscj(TsbzJxjscj tsbzJxjscj) {
        return tsbzJxjscjMapper.updateTsbzJxjscj(tsbzJxjscj);
    }

    /**
     * 批量删除见习教师成绩
     *
     * @param ids 需要删除的见习教师成绩ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjscjByIds(Long[] ids) {
        return tsbzJxjscjMapper.deleteTsbzJxjscjByIds(ids);
    }

    /**
     * 删除见习教师成绩信息
     *
     * @param id 见习教师成绩ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjscjById(Long id) {
        return tsbzJxjscjMapper.deleteTsbzJxjscjById(id);
    }

    /**
     * 成绩导入
     *
     * @param tsbzJxjscj 基地区级审核
     * @return 结果
     */
    @Override
    public int updateTsbzJxjscjforjsfa(TsbzJxjscj tsbzJxjscj)
    {
        return tsbzJxjscjMapper.updateTsbzJxjscjforjsfa(tsbzJxjscj);
    }

    @Override
    public String importTsbzJxjscj(List<TsbzJxjscj> tsbzJxjscjList, Boolean isUpdateSupport, String operName) {
        return null;
    }

//    // 导入教师数据
//    /**
//     * 导入教师数据
//     *
//     * @param tsbzJxjscjList 用户数据列表
//     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
//     * @param operName 操作用户
//     * @return 结果
//     */
//    @Override
//    public String importTsbzJxjscj(List<TsbzJxjscj> tsbzJxjscjList, Boolean isUpdateSupport, String operName) {
//
//        // 判断表格数据是否为空
//        if (StringUtils.isNull(tsbzJxjscjList) || tsbzJxjscjList.size() == 0)
//        {
//            throw new CustomException("导入用户数据不能为空！");
//        }
//
//        int successNum = 0;
//        int failureNum = 0;
//        StringBuilder successMsg = new StringBuilder();
//        StringBuilder failureMsg = new StringBuilder();
//        for (TsbzJxjscj tsbzJxjscj : tsbzJxjscjList)
//        {
//            Long theJsid = null;
//            TsbzJxjsjbxx tsbzjxjsjbxx = new TsbzJxjsjbxx();
//            tsbzjxjsjbxx.setName(tsbzJxjscj.getName());
//            List<TsbzJxjsjbxx> list = tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxList(tsbzjxjsjbxx);
//            if (list.size() == 0 || StringUtils.isNull(list)) {
//                throw new CustomException("导入数据非见习教师");
//            }
//            tsbzJxjscj.setJsid( tsbzjxjsjbxx.getId());
//
//            try
//            {
//                // 验证是否存在这个用户
//                TsbzJxjscj u = tsbzJxjscjMapper.selectTsbzJxjscjByJsid(tsbzJxjscj.getJsid());
//                if (StringUtils.isNull(u))
//                {
//                    tsbzJxjscj.setCreateBy(operName);
//                    this.insertTsbzJxjscj(tsbzJxjscj);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、账号 " + tsbzJxjscj.getJsid() + " 导入成功");
//                }
//                else if (isUpdateSupport)
//                {
//                    tsbzJxjscj.setUpdateBy(operName);
//                    this.updateTsbzJxjscj(tsbzJxjscj);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、账号 " + tsbzJxjscj.getJsid() + " 更新成功");
//                }
//                else
//                {
//                    failureNum++;
//                    failureMsg.append("<br/>" + failureNum + "、账号 " + tsbzJxjscj.getJsid() + " 已存在");
//                }
//            }
//            catch (Exception e)
//            {
//                failureNum++;
//                String msg = "<br/>" + failureNum + "、账号 " + tsbzJxjscj.getJsid() + " 导入失败：";
//                failureMsg.append(msg + e.getMessage());
//                log.error(msg, e);
//            }
//        }
//        if (failureNum > 0)
//        {
//            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
//            throw new CustomException(failureMsg.toString());
//        }
//        else
//        {
//            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
//        }
//        return successMsg.toString();
//    }
}







































