package com.ruoyi.common.enums;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/23/023 16:30
 * @Description : 资金操作类型
 */
public enum AmountOperationTypeEnums {

    TRANSFER(1, "划拨"), CONSUMPTION(2, "消费"), REFUND(3, "退款"), DEDUCTION(4, "扣款");


    int operationType;

    String operationName;

    public int getOperationType() {
        return operationType;
    }

    public String getOperationName() {
        return operationName;
    }

    AmountOperationTypeEnums(int operationType, String operationName) {
        this.operationType = operationType;
        this.operationName = operationName;
    }

    public AmountOperationTypeEnums findByOperationType(int operationType) {
        for (AmountOperationTypeEnums operationTypeEnums : AmountOperationTypeEnums.values()) {
            if (operationTypeEnums.getOperationType() == operationType) {
                return operationTypeEnums;
            }
        }
        return null;
    }
}
