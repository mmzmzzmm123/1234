package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;

public class MoonApplication {

    public MoonApplication() {
    }

    public static void printInfo(ConfigurableApplicationContext context) {
        if (context != null) {
            Environment environment = context.getEnvironment();
            String info = "\n----------------------------------\n\t" +
                    "Port:     %s \n\t" +
                    "Host:     http://%s:%s \n\t" +
                    "Profile:  %s \n\t" +
                    "----------------------------------";
            try {
                String hostAddress = InetAddress.getLocalHost().getHostAddress();
                String port = environment.getProperty("server.port");
                System.out.println(String.format(info,
                        port,
                        hostAddress,
                        port,
                        environment.getActiveProfiles()[0]));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
}
