package com.stdiet.common.enums;

/**
 * 年龄、性别对应BMI值枚举类
 */
public enum AgeBMI {

    age7_7(7, 7, "15.5_15"),
    age8_8(8, 8, "16_15"),
    age9_9(9, 9, "16.5_16"),
    age10_10(10, 10, "17_16"),
    age11_11(11, 11, "17.5_17"),
    age12_12(12, 12, "18_17"),
    age13_13(13, 13, "19_18"),
    age14_14(14, 14, "19.5_19"),
    age15_15(15, 15, "20_20"),
    age16_16(16, 16, "20.5_20"),
    age17_17(17, 17, "21_20"),
    age18_27(18, 27, "22_20"),
    age28_32(28, 32, "22.5_20.5"),
    age33_37(33, 37, "23_21"),
    age38_42(38, 42, "23.5_21.5"),
    age43_47(43, 47, "24_22"),
    age48_52(48, 52, "24.5_22.5");

    private final int minAge;

    private final int maxAge;

    //BMI数值，0男 1女
    private final double[] bmi;

    //等于或大于该年龄之后就五岁一个阶梯，五岁BMI加0.5
    private static final int beyondAge = 53;

    //超过beyondAge年龄之后的起始BMI值，0男 1女
    private static final double[] beyondAgeBmi = {25, 23};

    AgeBMI(int minAge, int maxAge, String bmi)
    {
        this.bmi = new double[2];
        this.bmi[0] = Double.parseDouble(bmi.split("_")[0]);
        this.bmi[1] = Double.parseDouble(bmi.split("_")[1]);
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * 根据年龄、性别获取BMI值
     * @param age 年龄
     * @param sex 性别
     * @return
     */
    public static double getBmiByAgeAndSex(int age, int sex){
        if(age < beyondAge){
            double bmi = 0.0;
            for (AgeBMI ageBMI : AgeBMI.values()) {
                if(age >= ageBMI.minAge && age <= ageBMI.maxAge){
                    bmi = ageBMI.bmi[sex];
                    break;
                }
            }
            return bmi;
        }else{
            return getBeyondAgeBmi(age, sex);
        }
    }


    /**
     * 大年龄计算BMI，五岁一个阶梯
     * @param age
     * @return
     */
    public static double getBeyondAgeBmi(int age, int sex){
        return beyondAgeBmi[sex] + (0.5 * ((age - beyondAge)/5));
    }

    public static void main(String[] args) {
        System.out.println(getBmiByAgeAndSex(20,0));
    }
}
