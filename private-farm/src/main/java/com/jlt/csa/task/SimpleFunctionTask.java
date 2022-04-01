package com.jlt.csa.task;

public class SimpleFunctionTask {
    public static int amount = 0;
    public void ryNoParams()
    {
        System.out.println("[方法任务] ==> 执行无参方法，第 " + (++amount) + " 次.");
    }
}
