package mybatisNoSpringUtils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class mybatisNoSpringParam {
    static String filePath = mybatisNoSpringParam.class.getClassLoader().getResource("application.yml").getPath();
    static Map indexReqParam;

    static {
        try {
            indexReqParam = new Yaml().load(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static String JdbcUrl = (String) ((Map) ((Map) indexReqParam.get("spring")).get("datasource")).get("url");

    static String Username = (String) ((Map) ((Map) indexReqParam.get("spring")).get("datasource")).get("username");
    static String  Password = (String) ((Map) ((Map) indexReqParam.get("spring")).get("datasource")).get("password");
    static String DriverClassName = (String) ((Map) ((Map) indexReqParam.get("spring")).get("datasource")).get("driver-class-name");
    static String MappersLocation = (String) indexReqParam.get("mybatisNoSpringMappersLocation");;
    public mybatisNoSpringParam() throws FileNotFoundException {

    }
}
