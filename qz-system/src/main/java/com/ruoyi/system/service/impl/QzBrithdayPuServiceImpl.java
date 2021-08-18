package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QzBrithdayPuMapper;
import com.ruoyi.system.domain.QzBrithdayPu;
import com.ruoyi.system.service.IQzBrithdayPuService;
import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.Zodiac;
import cn.hutool.core.date.DateUtil;

/**
 * 生日溥Service业务层处理
 * 
 * @author zlx
 * @date 2021-08-10
 */
@Service
public class QzBrithdayPuServiceImpl implements IQzBrithdayPuService 
{
    @Autowired
    private QzBrithdayPuMapper qzBrithdayPuMapper;

    /**
     * 查询生日溥
     * 
     * @param id 生日溥主键
     * @return 生日溥
     */
    @Override
    public QzBrithdayPu selectQzBrithdayPuById(Long id)
    {
        return qzBrithdayPuMapper.selectQzBrithdayPuById(id);
    }

    /**
     * 查询生日溥列表
     *
     * @param userId 此用户的生日溥
     * @return 生日溥
     */
    @Override
    public List<QzBrithdayPu> selectQzBrithdayPuListByUserId(Long userId)
    {
        return qzBrithdayPuMapper.selectQzBrithdayPuListByUserId(userId);
    }

    /**
     * 查询生日溥列表
     * 
     * @param qzBrithdayPu 生日溥
     * @return 生日溥
     */
    @Override
    public List<QzBrithdayPu> selectQzBrithdayPuList(QzBrithdayPu qzBrithdayPu)
    {
        return qzBrithdayPuMapper.selectQzBrithdayPuList(qzBrithdayPu);
    }

    /**
     * 新增生日溥
     * 
     * @param qzBrithdayPu 生日溥
     * @return 结果
     */
    @Override
    public int insertQzBrithdayPu(QzBrithdayPu qzBrithdayPu)
    {
        if(qzBrithdayPu.getSolarBirthday() != null && qzBrithdayPu.getSolarFlag() == "0"){
            //过阳历生日，计算阴历日期
            ChineseDate cd = new ChineseDate(qzBrithdayPu.getSolarBirthday());
            //得到农历日期
            Date lunarDate = DateUtils.parseDate(cd.toStringNormal());
            qzBrithdayPu.setBirthday(lunarDate);
            //阴历对应生肖
            qzBrithdayPu.setZodiac(cd.getChineseZodiac());
            //阳历生日对应的星座
            String constellation = Zodiac.getZodiac(qzBrithdayPu.getSolarBirthday());
            qzBrithdayPu.setConstellation(constellation);

        }else if(qzBrithdayPu.getBirthday() != null && qzBrithdayPu.getSolarFlag() == "1"){
            //过阴历生日，计算阳历
            ChineseDate cd = new ChineseDate(DateUtil.year(qzBrithdayPu.getBirthday()),
                                             DateUtil.month(qzBrithdayPu.getBirthday()),
                                             DateUtil.dayOfMonth(qzBrithdayPu.getBirthday()));
            qzBrithdayPu.setSolarBirthday(cd.getGregorianDate());
            //阴历对应生肖
            qzBrithdayPu.setZodiac(cd.getChineseZodiac());
            //阳历对应的星座
            String constellation = Zodiac.getZodiac(qzBrithdayPu.getSolarBirthday());
            qzBrithdayPu.setConstellation(constellation);
        }else{
            //异常，标志位、阴历阳历日期存在异常
        }
        qzBrithdayPu.setCreateTime(DateUtils.getNowDate());

        return qzBrithdayPuMapper.insertQzBrithdayPu(qzBrithdayPu);
    }

    /**
     * 修改生日溥
     * 
     * @param qzBrithdayPu 生日溥
     * @return 结果
     */
    @Override
    public int updateQzBrithdayPu(QzBrithdayPu qzBrithdayPu)
    {
        qzBrithdayPu.setUpdateTime(DateUtils.getNowDate());
        return qzBrithdayPuMapper.updateQzBrithdayPu(qzBrithdayPu);
    }

    /**
     * 批量删除生日溥
     * 
     * @param ids 需要删除的生日溥主键
     * @return 结果
     */
    @Override
    public int deleteQzBrithdayPuByIds(Long[] ids)
    {
        return qzBrithdayPuMapper.deleteQzBrithdayPuByIds(ids);
    }

    /**
     * 删除生日溥信息
     * 
     * @param id 生日溥主键
     * @return 结果
     */
    @Override
    public int deleteQzBrithdayPuById(Long id)
    {
        return qzBrithdayPuMapper.deleteQzBrithdayPuById(id);
    }
}
