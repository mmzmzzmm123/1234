package com.ruoyi.common.utils;


import com.ruoyi.common.enums.Operator;

/**
 * @author : yelifeng
 * @date : 2020/10/16 0016 17:55 xuc5566@gmail.com
 */
public class Condition {
    private String field;
    private Operator operator;
    private Object value;

    private Condition() {
    }

    public static Condition of(String field, Operator opt, Object value) {
        Condition condition = new Condition();
        condition.setField(field);
        condition.setOperator(opt);
        condition.setValue(value);
        return condition;
    }

    public static Condition eq(String field, Object value) {
        return of(field, Operator.EQ, value);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(field).append(" ")
                .append(operator.getValue()).append(" ")
                .append(value)
                .toString();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
