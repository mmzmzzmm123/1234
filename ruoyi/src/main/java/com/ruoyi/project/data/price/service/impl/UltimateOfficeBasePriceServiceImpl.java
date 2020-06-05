package com.ruoyi.project.data.price.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.OfficeBasePriceModifyModel;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import com.ruoyi.project.data.price.mapper.UltimateOfficeBasePriceMapper;
import com.ruoyi.project.data.price.service.IUltimateOfficeBasePriceService;
import com.ruoyi.project.system.service.impl.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 办公基价Service业务层处理
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@Service
public class UltimateOfficeBasePriceServiceImpl implements IUltimateOfficeBasePriceService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private UltimateOfficeBasePriceMapper officeBasePriceUltimateMapper;

    private static Integer getLastYearMonth(Integer yearMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearMonth / 100, yearMonth % 100, 1);
        return new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1));
    }

    /**
     * 查询办公基价列表
     *
     * @param officeBasePriceUltimate 办公基价
     * @return 办公基价
     */
    @Override
    public List<UltimateOfficeBasePrice> selectOfficeBasePriceUltimateList(UltimateOfficeBasePrice officeBasePriceUltimate) {
        officeBasePriceUltimate.setLastYearMonth(getLastYearMonth(officeBasePriceUltimate.getYearMonth()));
        return officeBasePriceUltimateMapper.getList(officeBasePriceUltimate);
    }

    @Override
    public int selectOfficeBasePriceUltimateListCount(UltimateOfficeBasePrice officeBasePriceUltimate) {
        officeBasePriceUltimate.setLastYearMonth(getLastYearMonth(officeBasePriceUltimate.getYearMonth()));
        return officeBasePriceUltimateMapper.getCount(officeBasePriceUltimate);
    }

    @Override
    public UltimateOfficeBasePrice getById(Integer yearMonth, String id) {
        Integer lastYearMonth = getLastYearMonth(yearMonth);
        return officeBasePriceUltimateMapper.getById(yearMonth, lastYearMonth, id);
    }

    @Override
    public String batchImport(Integer yearMonth, List<UltimateOfficeBasePrice> officeBasePriceUltimates,
                              String operName) {
        if (StringUtils.isNull(officeBasePriceUltimates) || officeBasePriceUltimates.size() == 0) {
            throw new CustomException("导入办公数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int insertNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        Integer lastYearMonth = getLastYearMonth(yearMonth);

        // 批量插入

        for (UltimateOfficeBasePrice inputModel : officeBasePriceUltimates) {
            try {
                inputModel.setYearMonth(yearMonth);
                officeBasePriceUltimateMapper.insertArtificialOfficeBasePrice(inputModel);
                // 验证是否存在这个用户
                UltimateOfficeBasePrice currentUltimateOfficeBasePrice =
                        officeBasePriceUltimateMapper.getById(yearMonth, lastYearMonth,
                                inputModel.getId());
                UltimateOfficeBasePrice lastUltimateOfficeBasePrice =
                        officeBasePriceUltimateMapper.getByBuildingId(lastYearMonth,
                                inputModel.getBuildingId());
                if (StringUtils.isNotNull(currentUltimateOfficeBasePrice)) {
                    updateBasePrice(inputModel, currentUltimateOfficeBasePrice, lastUltimateOfficeBasePrice);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、ID= " + inputModel.getId() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、ID= " + inputModel.getId() + " 失败");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、ID= " + inputModel.getId() + " 导入失败：";
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

    @Override
    public List<VueSelectModel> getYearMonthList() {
        return officeBasePriceUltimateMapper.getYearMonthList();
    }

    /**
     * 更新
     *
     * @param officeBasePriceUltimate
     * @return
     */
    @Override
    public int update(UltimateOfficeBasePrice officeBasePriceUltimate) {
        // 当期价格
        Integer lastYearMonth = getLastYearMonth(officeBasePriceUltimate.getYearMonth());
        UltimateOfficeBasePrice ultimateOfficeBasePrice =
                officeBasePriceUltimateMapper.getByRouteId(officeBasePriceUltimate.getYearMonth(),
                        officeBasePriceUltimate.getId());
        // 上期价格
        UltimateOfficeBasePrice lastUltimateOfficeBasePrice =
                officeBasePriceUltimateMapper.getByBuildingId(lastYearMonth,
                        officeBasePriceUltimate.getBuildingId());

        updateBasePrice(officeBasePriceUltimate, ultimateOfficeBasePrice, lastUltimateOfficeBasePrice);

        return 1;
    }

    /**
     * 基价更新
     *
     * @param inputModel
     * @param currentUltimateOfficeBasePrice
     * @param lastUltimateOfficeBasePrice
     */
    private void updateBasePrice(UltimateOfficeBasePrice inputModel,
                                 UltimateOfficeBasePrice currentUltimateOfficeBasePrice,
                                 UltimateOfficeBasePrice lastUltimateOfficeBasePrice) {

        OfficeBasePriceModifyModel officeBasePriceModifyModel = compareYearMonth(inputModel,
                currentUltimateOfficeBasePrice, lastUltimateOfficeBasePrice);
        if (null != officeBasePriceModifyModel) {
            officeBasePriceUltimateMapper.updateBasePrice(officeBasePriceModifyModel);
        }

        officeBasePriceModifyModel = compareLastYearMonth(inputModel, lastUltimateOfficeBasePrice);
        if (null != officeBasePriceModifyModel) {
            officeBasePriceUltimateMapper.updateBasePrice(officeBasePriceModifyModel);
        }
    }

    /**
     * 上期价格
     *
     * @param inputModel
     * @param ultimateOfficeBasePrice
     * @return
     */
    private OfficeBasePriceModifyModel compareYearMonth(UltimateOfficeBasePrice inputModel,
                                                        UltimateOfficeBasePrice ultimateOfficeBasePrice,
                                                        UltimateOfficeBasePrice lastUltimateOfficeBasePrice) {
        BigDecimal inputMainPrice = inputModel.getMainPrice();
        BigDecimal mainPrice = ultimateOfficeBasePrice.getMainPrice();
        BigDecimal inputMainPriceRent = inputModel.getMainPriceRent();
        BigDecimal mainPriceRent = ultimateOfficeBasePrice.getMainPriceRent();
        if (Objects.equals(inputMainPrice, mainPrice) && Objects.equals(inputMainPriceRent, mainPriceRent)) {
            return null;
        }

        OfficeBasePriceModifyModel officeBasePriceModifyModel =
                new OfficeBasePriceModifyModel(inputModel.getId(),
                        inputModel.getYearMonth());
        officeBasePriceModifyModel.setMainPrice(inputModel.getMainPrice_1());
        if (null != lastUltimateOfficeBasePrice.getMainPrice() && lastUltimateOfficeBasePrice.getMainPrice().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal mainPricePst =
                    inputModel.getMainPrice().divide(lastUltimateOfficeBasePrice.getMainPrice(), 4);
            officeBasePriceModifyModel.setMainPricePst(mainPricePst);
        }

        officeBasePriceModifyModel.setMainPriceRent(inputModel.getMainPriceRent_1());
        if (null != lastUltimateOfficeBasePrice.getMainPriceRent() && lastUltimateOfficeBasePrice.getMainPriceRent().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal mainPriceRentPst =
                    inputModel.getMainPriceRent().divide(lastUltimateOfficeBasePrice.getMainPriceRent(), 4);
            officeBasePriceModifyModel.setMainPriceRentPst(mainPriceRentPst);
        }

        return officeBasePriceModifyModel;
    }

    /**
     * 上期价格
     *
     * @param inputModel
     * @param lastUltimateOfficeBasePrice
     * @return
     */
    private OfficeBasePriceModifyModel compareLastYearMonth(UltimateOfficeBasePrice inputModel,
                                                            UltimateOfficeBasePrice lastUltimateOfficeBasePrice) {
        BigDecimal inputMainPrice = inputModel.getMainPrice_1();
        BigDecimal mainPrice_1 = lastUltimateOfficeBasePrice.getMainPrice();
        BigDecimal inputMainPriceRent = inputModel.getMainPriceRent_1();
        BigDecimal mainPriceRent_1 = lastUltimateOfficeBasePrice.getMainPriceRent();
        if (Objects.equals(inputMainPrice, mainPrice_1) && Objects.equals(inputMainPriceRent, mainPriceRent_1)) {
            return null;
        }

        OfficeBasePriceModifyModel officeBasePriceModifyModel =
                new OfficeBasePriceModifyModel(lastUltimateOfficeBasePrice.getId(),
                        lastUltimateOfficeBasePrice.getYearMonth());
        officeBasePriceModifyModel.setMainPrice(inputModel.getMainPrice_1());
        officeBasePriceModifyModel.setMainPricePst(lastUltimateOfficeBasePrice.getMainPricePst());
        officeBasePriceModifyModel.setMainPriceRent(inputModel.getMainPriceRent_1());
        officeBasePriceModifyModel.setMainPriceRentPst(lastUltimateOfficeBasePrice.getMainPriceRentPst());

        return officeBasePriceModifyModel;
    }
}
