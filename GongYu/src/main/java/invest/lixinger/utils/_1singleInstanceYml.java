package invest.lixinger.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class _1singleInstanceYml {
    private Map conf;
    private static _1singleInstanceYml instance;

    private _1singleInstanceYml() {
        try {
            conf = new Yaml().load(new FileInputStream(new File("./etc/config.yml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static _1singleInstanceYml getInstance() {
        if (instance == null) {
            synchronized (_1singleInstanceYml.class) {
                if (instance == null) {
                    instance = new _1singleInstanceYml();
                }
            }
        }
        return instance;
    }

    public String getString(String param) {
        return String.valueOf(conf.get(param));
    }

    public String getString(String param, String defaultValue) {
        if (null == conf.get(param)) {
            return defaultValue;
        }
        return String.valueOf(conf.get(param));
    }

    public Map getConfig() {
        return conf;
    }
}
