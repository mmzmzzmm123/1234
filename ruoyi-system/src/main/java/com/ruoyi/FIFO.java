package com.ruoyi;

import java.util.Deque;
import java.util.List;

public interface FIFO<T> extends List<T>, Deque<T>, Cloneable, java.io.Serializable {

    /**
     * 向最后添加一个新的，如果长度超过允许的最大值，则弹出一个 *
     */
    T sPush(T addLast);

    /**
     * 弹出head，如果Size = 0返回null。而不同于pop抛出异常
     *
     * @return
     */
    T sPoll();

    /**
     * 获得最大保存
     *
     * @return
     */
    int getMaxSize();

}