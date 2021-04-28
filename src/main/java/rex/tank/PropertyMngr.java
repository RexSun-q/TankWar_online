package rex.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMngr {
    private static Properties props = new Properties();
    static {
        try {
            props.load(PropertyMngr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        if (props == null) return null;
        return (String) props.get(key);
    }

    public static void main(String[] args) {
        System.out.println(props.get("initTanksCount"));
    }

}
