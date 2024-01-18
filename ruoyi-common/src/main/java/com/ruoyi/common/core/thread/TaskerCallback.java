package com.ruoyi.common.core.thread;


import java.util.List;

public interface TaskerCallback<I, O> extends Callback {
    public List<O> run(List<I> curDatas);
}
