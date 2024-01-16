package com.ruoyi.bk.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bk.mapper.LabelMapper;
import com.ruoyi.bk.domain.Label;
import com.ruoyi.bk.service.ILabelService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 标签Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class LabelServiceImpl extends ServiceImpl<LabelMapper,Label> implements ILabelService {
    @Resource
    private LabelMapper labelMapper;

    /**
     * 查询标签
     *
     * @param id 标签主键
     * @return 标签
     */
    @Override
    public Label selectLabelById(Long id) {
        return labelMapper.selectLabelById(id);
    }

    /**
     * 查询标签列表
     *
     * @param label 标签
     * @return 标签
     */
    @Override
    public List<Label> selectLabelList(Label label) {
        return labelMapper.selectLabelList(label);
    }

    /**
     * 新增标签
     *
     * @param label 标签
     * @return 结果
     */
    @Override
    public int insertLabel(Label label) {
            return labelMapper.insertLabel(label);
    }

    /**
     * 修改标签
     *
     * @param label 标签
     * @return 结果
     */
    @Override
    public int updateLabel(Label label) {
        return labelMapper.updateLabel(label);
    }

    /**
     * 批量删除标签
     *
     * @param ids 需要删除的标签主键
     * @return 结果
     */
    @Override
    public int deleteLabelByIds(Long[] ids) {
        return labelMapper.deleteLabelByIds(ids);
    }

    /**
     * 删除标签信息
     *
     * @param id 标签主键
     * @return 结果
     */
    @Override
    public int deleteLabelById(Long id) {
        return labelMapper.deleteLabelById(id);
    }
}
