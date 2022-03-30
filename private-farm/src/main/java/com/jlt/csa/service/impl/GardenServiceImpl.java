package com.jlt.csa.service.impl;

import java.util.List;
import java.util.Map;

import com.jlt.csa.utils.CommonFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.GardenMapper;
import com.jlt.csa.domain.Garden;
import com.jlt.csa.service.IGardenService;

/**
 * 菜地划分Service业务层处理
 *
 * @author 郏磊涛
 * @date 2022-03-29
 */
@Service
public class GardenServiceImpl implements IGardenService
{
    @Autowired
    private GardenMapper gardenMapper;

    /**
     * 查询菜地划分
     *
     * @param id 菜地划分主键
     * @return 菜地划分
     */
    @Override
    public Garden selectGardenById(Long id)
    {
        return gardenMapper.selectGardenById(id);
    }

    /**
     * 查询菜地划分列表
     *
     * @param garden 菜地划分
     * @return 菜地划分
     */
    @Override
    public List<Garden> selectGardenList(Garden garden)
    {
        return gardenMapper.selectGardenList(garden);
    }

    /**
     * 新增菜地划分
     *
     * @param garden 菜地划分
     * @return 结果
     */
    @Override
    public int insertGarden(Garden garden)
    {
        return gardenMapper.insertGarden(garden);
    }

    /**
     * 批量新增菜地
     *
     * @param garden 起始菜地信息，以此地编号开始依次递增
     * @param amount 菜地数量
     * @return 结果
     */
    public int insertGardens(Garden garden, int amount) {
        Map<String, Object> map = CommonFunctions.parseCode(garden.getCode());

        if (map.isEmpty()) {
            // 返回空map，格式不正确
            throw new RuntimeException("菜地编号格式不正确！");
        } else {
            // 菜地编码前缀
            String prefix = (String) map.get("prefix");
            // 编码的数字部分的位数
            int numberWidth = (int) map.get("width");
            int startCode = (int) map.get("number");
            Integer endCode = startCode + amount - 1;
            // 检查批量编码是否会导致位数变长，如变长则抛出异常
            if (endCode.toString().length() == numberWidth) {
                for (int i = startCode; i <= endCode; i++) {
                    garden.setCode(prefix + i);
                    gardenMapper.insertGarden(garden);
                }
            } else {
                // 出现进位导致编号变长，格式不正确
                throw new RuntimeException("菜地编号数字部分长度不够或数量过多！");
            }
        }

        return amount;
    }

    /**
     * 修改菜地划分
     *
     * @param garden 菜地划分
     * @return 结果
     */
    @Override
    public int updateGarden(Garden garden)
    {
        return gardenMapper.updateGarden(garden);
    }

    /**
     * 批量修改菜地状态
     *
     * @param garden 菜地划分
     * @param ids 需要更改的菜地id集合
     * @return 结果
     */
    public int updateGardenStatus(Garden garden, Long[] ids) {
        return gardenMapper.updateGardenStatus(garden, ids);
    }

    /**
     * 批量删除菜地划分
     *
     * @param ids 需要删除的菜地划分主键
     * @return 结果
     */
    @Override
    public int deleteGardenByIds(Long[] ids)
    {
        return gardenMapper.deleteGardenByIds(ids);
    }

    /**
     * 删除菜地划分信息
     *
     * @param id 菜地划分主键
     * @return 结果
     */
    @Override
    public int deleteGardenById(Long id)
    {
        return gardenMapper.deleteGardenById(id);
    }
}
