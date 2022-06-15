package com.ruoyi.driver.binance;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018/12/2.
 */
@Getter
@Setter
public class BinanceSpotBalance {
    private String asset;
    private Double free;
    private Double locked;

    @Override
    public String toString() {
        return "BinanceSpotBalance{" +
                "asset='" + asset + '\'' +
                ", free=" + free +
                ", locked=" + locked +
                '}';
    }
}
