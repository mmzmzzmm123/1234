package invest.realEstate;

import java.math.BigDecimal;
import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IRR {

    public static void main(String[] args) {
        // 分期金额
        double total = 2305.56;
        // 期数
        int qs = 12;
        // 每期金额
        double mqje = -200.43;
        // --------------------------------------------------
        List<Double> incomeList = new ArrayList<>();
        incomeList.add(total);
        for (int i = 0; i < qs ; i++) {
            incomeList.add(mqje);
        }
        Double[] temp = incomeList.toArray(new Double[incomeList.size()]);
        double[] income = Arrays.stream(temp).mapToDouble(Double::doubleValue).toArray();
//        double[] income = {-150,0,0,0,0,0,0,0,0,0,0,0,20};
        incomeList.forEach(System.out::println);
        System.out.println(yearIrr(income));
        System.out.println(monthIrr(income));
    }

    public static double irr(double[] income) {
        return irr(income, 0.1D);
    }

    public static double irr(double[] values, double guess) {
        int maxIterationCount = 20;
        double absoluteAccuracy = 1.0E-007D;

        double x0 = guess;

        int i = 0;
        while (i < maxIterationCount) {
            double fValue = 0.0D;
            double fDerivative = 0.0D;
            for (int k = 0; k < values.length; k++) {
                fValue += values[k] / Math.pow(1.0D + x0, k);
                fDerivative += -k * values[k] / Math.pow(1.0D + x0, k + 1);
            }
            double x1 = x0 - fValue / fDerivative;
            if (Math.abs(x1 - x0) <= absoluteAccuracy) {
                return x1;
            }
            x0 = x1;
            i++;
        }
        return (0.0D / 0.0D);
    }


    /**
     * 默认猜测值
     */
    private static final double irrX0 = 0.00001d;

    public static String monthIrr(double[] income) {
        double ret = irr(income,irrX0);
        BigDecimal b = new BigDecimal(100);
        double irr = new BigDecimal(ret).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(b).doubleValue();
        return irr+"%";
    }
    public static String yearIrr(double[] income) {
        double ret = irr(income,irrX0) ;
        BigDecimal bigDecimal = new BigDecimal(ret);
//        =(1+Z2)^12-1
        BigDecimal a = new BigDecimal(1);
        BigDecimal b = new BigDecimal(100);
        double year = (bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).
                multiply(bigDecimal.add(a)).subtract(a).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(b).doubleValue();
        return year+"%";
    }

}
