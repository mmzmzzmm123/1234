package com.ruoyi.common.utils;
import com.ruoyi.common.constant.SymbolType;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringUtil {

    /**
     * 判断字符串是否满足通配符
     * @param candidate 字符串
     * @param wildcard 通配符
     * @return true 是 false 否
     */
    public static boolean isMatch(String candidate, String wildcard) {
        if (wildcard == null && candidate != null) {
            return false;
        }
        if (ObjectUtils.isEmpty(candidate)) {
            return false;
        }
        if (candidate.equals(wildcard)) {
            return true;
        }
        if (!candidate.endsWith(SymbolType.ASTERISK) && !candidate.endsWith(SymbolType.SLASH)) {
            candidate = candidate + SymbolType.SLASH;
        }
        //dp[i][j]表示s中前i项和p中前j项是否匹配
        boolean[][] dp = new boolean[candidate.length() + 1][wildcard.length() + 1];
        dp[0][0] = true;
        for (int i = 1; i <= wildcard.length(); i++) {
            if (dp[0][i - 1] && wildcard.charAt(i - 1) == '*') {
                dp[0][i] = true;
            }
        }
        for (int i = 1; i <= candidate.length(); i++) {
            for (int j = 1; j <= wildcard.length(); j++) {
                if (candidate.charAt(i - 1) == wildcard.charAt(j - 1) || wildcard.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (wildcard.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
            }
        }
        return dp[candidate.length()][wildcard.length()];
    }

    /**
     * 校验目标字符串是否满足通配符集合
     * @param target 目标字符串
     * @param candidates 通配符集合
     * @return true 是 | false 否
     */
    public static boolean isWildCardsMatchStr(String target, List<String> candidates) {
        Set<String> wildcards = new HashSet<>(candidates);
        for (String wildcard : wildcards) {
            if (target.equals(wildcard)) {
                return true;
            }
            if (StringUtil.isMatch(target, wildcard)) {
                return true;
            }
        }
        return false;
    }

}
