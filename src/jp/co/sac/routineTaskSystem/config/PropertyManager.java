package jp.co.sac.routineTaskSystem.config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * プロパティファイル操作クラス
 *
 * @author shogo_saito
 */
public class PropertyManager {

    private static Logger log = Logger.getLogger("root");

    private Properties resource;

    public PropertyManager(String fileName, String charSetName) {
        load(fileName, charSetName);
    }

    private void load(String filePath, String charSetName) {
        try {
            resource = new Properties();
            try (InputStream is = new FileInputStream(new File(filePath));
                    InputStreamReader isr = new InputStreamReader(is, charSetName)) {
                resource.load(isr);
            }
        } catch (Exception ex) {
            log.warn(" * [" + filePath + "]を読み込めませんでした。");
        }
    }

    public boolean getBoolean(String key, boolean defBool, String trueString) {
        try {
            return trueString.equals(resource.get(key));
        } catch (Exception ex) {
            log.warn(ex);
            return defBool;
        }
    }

    public String getString(String key, String defString) {
        String ret = getString(key);
        if (ret == null || ret.isEmpty()) {
            return defString;
        } else {
            return ret;
        }
    }

    public String getString(String key) {
        try {
            Object value = resource.get(key);
            if (value == null) {
                return null;
            }
            return resource.get(key).toString();
        } catch (Exception ex) {
            return null;
        }
    }

    public int getInt(String key, int defInt) {
        String tmp = getString(key);
        if (tmp != null) {
            try {
                return Integer.valueOf(tmp);
            } catch (NumberFormatException ex) {
            }
        }
        return defInt;
    }
}
