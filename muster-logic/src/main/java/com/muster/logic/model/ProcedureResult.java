package com.muster.logic.model;

import java.util.List;
import java.util.Map;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020-07-04.
 *
 * @author 刘江涛.
 */
public class ProcedureResult {

   private int res;

   private List<Map<String, Object>> result;

    /**
     * Gets res.
     *
     * @return the res
     */
    public int getRes() {
        return res;
    }

    /**
     * Sets res.
     *
     * @param res the res
     */
    public void setRes(final int res) {
        this.res = res;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public List<Map<String, Object>> getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(final List<Map<String, Object>> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProcedureResult{");
        sb.append("res=").append(res);
        sb.append(", result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
