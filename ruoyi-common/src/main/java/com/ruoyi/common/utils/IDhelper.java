package com.ruoyi.common.utils;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * @author qinjin
 */
public class IDhelper {

    private static IdentifierGenerator identifierGenerator=new DefaultIdentifierGenerator();

    public  static Long getNextId()
    {
        Number id=  identifierGenerator.nextId(new Object());
        return Long.valueOf(id+"");
    }
}
