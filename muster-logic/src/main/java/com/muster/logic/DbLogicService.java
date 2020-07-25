package com.muster.logic;

import com.muster.logic.model.ProcedureResult;

import java.util.Map;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020-07-04.
 *
 * @author 刘江涛.
 */
public interface DbLogicService {

    /**
     * @param procedureName 存储过程名称，从 ProcedureNameConstants 获取
     * @param params 存储过程参数
     * @return 返回结果集
     */
    ProcedureResult exec(final String procedureName, final Map<String, Object> params);
}
