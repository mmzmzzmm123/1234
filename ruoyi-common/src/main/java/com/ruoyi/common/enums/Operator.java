package com.ruoyi.common.enums;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;

public enum Operator {
    EQ("="), NE("!="), GT(">"), GE(">="), LT("<"), LE("<="),

    /**
     * ex. id in 1,2
     */
    IN("IN"),

    /**
     * ex. id nin 1,2
     */
    NIN("NIN"),

    LIKE("LIKE");

    private String value;

    private static List<String> OPERATOR_LIST = new ImmutableList.Builder<String>()
            .addAll(Lists.newArrayList(Arrays.asList(
                    EQ.getValue(), NE.getValue(), GT.getValue(), GE.getValue(),
                    LT.getValue(), LE.getValue(), IN.getValue(), NIN.getValue(), LIKE.getValue()
            ))).build();

    Operator(String value) {
        this.value = value;
    }

    public static boolean isValidOperator(String opt) {
        return OPERATOR_LIST.contains(opt);
    }


    public String getValue() {
        return this.value;
    }
}
