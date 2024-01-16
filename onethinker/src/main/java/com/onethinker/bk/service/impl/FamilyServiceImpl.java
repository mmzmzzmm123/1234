package com.onethinker.bk.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onethinker.bk.mapper.FamilyMapper;
import com.onethinker.bk.domain.Family;
import com.onethinker.bk.service.IFamilyService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 家庭信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class FamilyServiceImpl extends ServiceImpl<FamilyMapper,Family> implements IFamilyService {
    @Resource
    private FamilyMapper familyMapper;

    /**
     * 查询家庭信息
     *
     * @param id 家庭信息主键
     * @return 家庭信息
     */
    @Override
    public Family selectFamilyById(Long id) {
        return familyMapper.selectFamilyById(id);
    }

    /**
     * 查询家庭信息列表
     *
     * @param family 家庭信息
     * @return 家庭信息
     */
    @Override
    public List<Family> selectFamilyList(Family family) {
        return familyMapper.selectFamilyList(family);
    }

    /**
     * 新增家庭信息
     *
     * @param family 家庭信息
     * @return 结果
     */
    @Override
    public int insertFamily(Family family) {
                family.setCreateTime(DateUtils.getNowDate());
            return familyMapper.insertFamily(family);
    }

    /**
     * 修改家庭信息
     *
     * @param family 家庭信息
     * @return 结果
     */
    @Override
    public int updateFamily(Family family) {
                family.setUpdateTime(DateUtils.getNowDate());
        return familyMapper.updateFamily(family);
    }

    /**
     * 批量删除家庭信息
     *
     * @param ids 需要删除的家庭信息主键
     * @return 结果
     */
    @Override
    public int deleteFamilyByIds(Long[] ids) {
        return familyMapper.deleteFamilyByIds(ids);
    }

    /**
     * 删除家庭信息信息
     *
     * @param id 家庭信息主键
     * @return 结果
     */
    @Override
    public int deleteFamilyById(Long id) {
        return familyMapper.deleteFamilyById(id);
    }
}
