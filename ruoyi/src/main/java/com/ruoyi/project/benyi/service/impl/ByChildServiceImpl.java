package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.impl.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByChildMapper;
import com.ruoyi.project.benyi.domain.ByChild;
import com.ruoyi.project.benyi.service.IByChildService;

/**
 * 幼儿信息Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-20
 */
@Service
public class ByChildServiceImpl implements IByChildService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    private ByChildMapper byChildMapper;

    /**
     * 查询幼儿信息
     *
     * @param id 幼儿信息ID
     * @return 幼儿信息
     */
    @Override
    public ByChild selectByChildById(Long id) {
        return byChildMapper.selectByChildById(id);
    }

    /**
     * 查询幼儿信息列表
     *
     * @param byChild 幼儿信息
     * @return 幼儿信息
     */
    @Override
    public List<ByChild> selectByChildList(ByChild byChild) {
        return byChildMapper.selectByChildList(byChild);
    }

    /**
     * 查询幼儿信息列表-用于考勤
     *
     * @param byChild 幼儿信息
     * @return 幼儿信息集合
     */
    @Override
    public List<ByChild> selectByCheckList(ByChild byChild) {
        return byChildMapper.selectByCheckList(byChild);
    }

    /**
     * 新增幼儿信息
     *
     * @param byChild 幼儿信息
     * @return 结果
     */
    @Override
    public int insertByChild(ByChild byChild) {
        byChild.setCreateTime(DateUtils.getNowDate());
        return byChildMapper.insertByChild(byChild);
    }

    /**
     * 修改幼儿信息
     *
     * @param byChild 幼儿信息
     * @return 结果
     */
    @Override
    public int updateByChild(ByChild byChild) {
        return byChildMapper.updateByChild(byChild);
    }

    /**
     * 批量删除幼儿信息
     *
     * @param ids 需要删除的幼儿信息ID
     * @return 结果
     */
    @Override
    public int deleteByChildByIds(Long[] ids) {
        return byChildMapper.deleteByChildByIds(ids);
    }

    /**
     * 删除幼儿信息信息
     *
     * @param id 幼儿信息ID
     * @return 结果
     */
    @Override
    public int deleteByChildById(Long id) {
        return byChildMapper.deleteByChildById(id);
    }


    /**
     * 查询幼儿考勤信息列表
     *
     * @param byChild 班级信息
     * @return 班级信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<ByChild> selectststicstClassList(ByChild byChild) {
        return byChildMapper.selectststicstClassList(byChild);
    }

    /**
     * 导入用户数据
     *
     * @param userChild 用户数据列表
     * @param operName  操作用户
     * @param deptId    部门id
     * @param bjbh      班级编号
     * @return 结果
     */
    @Override
    public String importChild(List<ByChild> userChild, String operName, Long deptId, String bjbh) {
        if (StringUtils.isNull(userChild) || userChild.size() == 0) {
            throw new CustomException("导入幼儿数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (ByChild byChild : userChild) {
            try {
                if (bjbh == null || "".equals(bjbh)) {
                } else {
                    byChild.setClassid(bjbh);
                }
                byChild.setSchoolid(deptId);
                byChild.setCreateBy(operName);
                this.insertByChild(byChild);
                successNum++;
                successMsg.append("<br/>" + successNum + "、幼儿 " + byChild.getName() + " 导入成功");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、幼儿 " + byChild.getName() + " 导入失败：";
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
