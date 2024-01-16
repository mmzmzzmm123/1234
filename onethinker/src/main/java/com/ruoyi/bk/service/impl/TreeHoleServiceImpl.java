package com.ruoyi.bk.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bk.mapper.TreeHoleMapper;
import com.ruoyi.bk.domain.TreeHole;
import com.ruoyi.bk.service.ITreeHoleService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 树洞Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class TreeHoleServiceImpl extends ServiceImpl<TreeHoleMapper,TreeHole> implements ITreeHoleService {
    @Resource
    private TreeHoleMapper treeHoleMapper;

    /**
     * 查询树洞
     *
     * @param id 树洞主键
     * @return 树洞
     */
    @Override
    public TreeHole selectTreeHoleById(Long id) {
        return treeHoleMapper.selectTreeHoleById(id);
    }

    /**
     * 查询树洞列表
     *
     * @param treeHole 树洞
     * @return 树洞
     */
    @Override
    public List<TreeHole> selectTreeHoleList(TreeHole treeHole) {
        return treeHoleMapper.selectTreeHoleList(treeHole);
    }

    /**
     * 新增树洞
     *
     * @param treeHole 树洞
     * @return 结果
     */
    @Override
    public int insertTreeHole(TreeHole treeHole) {
                treeHole.setCreateTime(DateUtils.getNowDate());
            return treeHoleMapper.insertTreeHole(treeHole);
    }

    /**
     * 修改树洞
     *
     * @param treeHole 树洞
     * @return 结果
     */
    @Override
    public int updateTreeHole(TreeHole treeHole) {
        return treeHoleMapper.updateTreeHole(treeHole);
    }

    /**
     * 批量删除树洞
     *
     * @param ids 需要删除的树洞主键
     * @return 结果
     */
    @Override
    public int deleteTreeHoleByIds(Long[] ids) {
        return treeHoleMapper.deleteTreeHoleByIds(ids);
    }

    /**
     * 删除树洞信息
     *
     * @param id 树洞主键
     * @return 结果
     */
    @Override
    public int deleteTreeHoleById(Long id) {
        return treeHoleMapper.deleteTreeHoleById(id);
    }
}
