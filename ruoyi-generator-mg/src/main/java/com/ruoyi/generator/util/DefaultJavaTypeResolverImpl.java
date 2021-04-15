package com.ruoyi.generator.util;

import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

public class DefaultJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl
                                         implements JavaTypeResolver {

    public DefaultJavaTypeResolverImpl() {
        super();
        super.typeMap.put(Types.SMALLINT, super.typeMap.get(Types.INTEGER));
    }

}
