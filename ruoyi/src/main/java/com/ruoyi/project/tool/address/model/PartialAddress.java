package com.ruoyi.project.tool.address.model;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 清洗地址
 *
 * @author lihe
 */
public abstract class PartialAddress {
    protected String address;
    protected String hao;
    protected String shi;
    protected Integer floor;
    protected final static String SHI_PATTERN = "([\\dA-Za-z]+)室$";
    protected final static String HAO_PATTERN = "([\\dA-Za-z]+)(甲乙丙丁戊己庚辛仍亏)?号";
    protected final static String NUMBER_PATTERN = "\\d+";
    protected final int HUNDRED = 100;
    protected final int TEN_THOUSAND = 10 * 1000;

    public PartialAddress(String address) {
        this.address = address;
    }

    public String getHao() {
        return hao;
    }

    public String getShi() {
        return shi;
    }

    public Integer getFloor() {
        return floor;
    }

    /**
     * 获取地址（单套、楼栋、小区）
     *
     * @return
     */
    public abstract List<PartialAddress> getPartialAddress();

    /**
     * 室解析
     *
     * @return
     */
    protected String parseShi() {
        Pattern pattern = Pattern.compile(SHI_PATTERN);
        Matcher matcher = pattern.matcher(this.address);
        // 这个需要优化，地下层没有考虑
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 楼层解析
     *
     * @return
     */
    protected Integer parseFloor() {
        if (StringUtils.isEmpty(this.shi)) {
            return null;
        }
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(this.shi);
        // 这个需要优化，地下层没有考虑
        if (matcher.find()) {
            Integer num = new Integer(matcher.group(0));
            if (num <= HUNDRED) {
                return num / 10;
            } else if (num > HUNDRED && num <= TEN_THOUSAND) {
                return num / 100;
            } else {
                throw new CustomException("室号太大");
            }
        }
        return null;
    }

    /**
     * 号
     *
     * @return
     */
    protected String parseHAO() {
        Pattern pattern = Pattern.compile(HAO_PATTERN);
        Matcher matcher = pattern.matcher(this.address);
        // 这个需要优化，地下层没有考虑
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public String toString() {
        return "PartialAddress{" +
                "hao='" + hao + '\'' +
                ", shi='" + shi + '\'' +
                ", floor=" + floor +
                '}';
    }
}
