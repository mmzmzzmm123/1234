//package com.ruoyi.framework.datasource;
//
//
//import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
//import com.baomidou.dynamic.datasource.provider.YmlDynamicDataSourceProvider;
//import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
//import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidDynamicDataSourceConfiguration;
//import com.github.pagehelper.PageInterceptor;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import javax.sql.DataSource;
//import java.util.List;
//import java.util.Properties;
//
//@Configuration
//@ConditionalOnBean(SqlSessionFactory.class)
////@AutoConfigureAfter(MybatisAutoConfiguration.class)
//@AutoConfigureAfter(DynamicDataSourceAutoConfiguration.class)
//public class DynamicDataSourcePagerDialectConfig {
//    @Autowired
//    private List<SqlSessionFactory> sqlSessionFactoryList;
////    @Autowired
////    private DynamicDataSourceProvider dynamicDataSourceProvider;
//
//    @PostConstruct
//    public void addPageInterceptor() {
//
//        sqlSessionFactoryList.forEach(x->{
//            System.out.println("");
//        });
//
////        dynamicDataSourceProvider.loadDataSources().forEach((k, v) -> {
////            PageInterceptor interceptor = new PageInterceptor();
////            Properties properties = new Properties();
////
////        });
//
//        //先把一般方式配置的属性放进去
////        properties.putAll(pageHelperProperties());
//        //在把特殊配置放进去，由于close-conn 利用上面方式时，属性名就是 close-conn 而不是 closeConn，所以需要额外的一步
////        properties.putAll(this.properties.getProperties());
////        interceptor.setProperties(properties);
////        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
////            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
////        }
//    }
//
////    @Bean(name = "mssqlSessionFactory")
////    public SqlSessionFactory mssqlSessionFactory(@Qualifier("mssqlDataSource") DataSource dataSource)
////            throws Exception {
////        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
////        //分页插件
////        Interceptor interceptor = new PageInterceptor();
////        Properties properties = new Properties();
////        //数据库
////        properties.setProperty("helperDialect", "sqlserver2012");
////        //是否分页合理化
////        properties.setProperty("reasonable", "false");
////
////        interceptor.setProperties(properties);
////
////        sessionFactory.setPlugins(new Interceptor[] {interceptor});
////        sessionFactory.setDataSource(dataSource);
////        return sessionFactory.getObject();
////    }
//}
