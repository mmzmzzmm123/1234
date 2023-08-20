package invest.realEstate;

import org.apache.poi.ss.formula.functions.Finance;

import java.math.BigDecimal;

public class test1 {
    public static void main(String[] args) {
        double interest = 0.0834;//年化利率
        int period = 24;//期数
        BigDecimal periodB = BigDecimal.valueOf(period);
        BigDecimal monthRepay = BigDecimal.valueOf(Finance.pmt(interest / 12,period , -1)).multiply(periodB).subtract(BigDecimal.ONE).setScale(4,BigDecimal.ROUND_HALF_UP);
        BigDecimal monthRate = monthRepay.divide(periodB,4, BigDecimal.ROUND_HALF_UP);
        System.out.println(monthRate);
    }

}
