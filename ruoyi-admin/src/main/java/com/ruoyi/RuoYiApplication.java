package com.ruoyi;

import com.ruoyi.common.utils.DefaultProfileUtil;
import com.ruoyi.common.utils.SpringContextUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.invoke.MethodHandles;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    //private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());
    private static final Logger syslog = LoggerFactory.getLogger(RuoYiApplication.class);
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        //SpringApplication.run(RuoYiApplication.class, args);
        //System.out.println("(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
        //        " .-------.       ____     __        \n" +
        //        " |  _ _   \\      \\   \\   /  /    \n" +
        //        " | ( ' )  |       \\  _. /  '       \n" +
        //        " |(_ o _) /        _( )_ .'         \n" +
        //        " | (_,_).' __  ___(_ o _)'          \n" +
        //        " |  |\\ \\  |  ||   |(_,_)'         \n" +
        //        " |  | \\ `'   /|   `-'  /           \n" +
        //        " |  |  \\    /  \\      /           \n" +
        //        " ''-'   `'-'    `-..-'              ");
        appEnvInfo(args);
    }
    /**
     * @Description: 打印应用启动环境信息
     * @Author: libiao
     * @Date: 2021/10/17 19:40
     * @param args:
     * @return: void
     **/
    public static void appEnvInfo(String[] args){
        SpringApplication app = new SpringApplication(RuoYiApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        ApplicationContext appc =  app.run(args);
        Environment env = appc.getEnvironment();
        String ip = "localhost";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        syslog.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://{}:{}\n\t" +
                        "----------------------------------------------------------",
                env.getProperty("spring.profiles.active"),ip,
                env.getProperty("server.port"));
        syslog.info("程序启动完成!");
        SpringContextUtils.setStaticApplicationContext(appc);
    }
}
