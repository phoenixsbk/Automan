package cn.lynx.automan.testutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestConfigs {
  private static Properties properties;

  static {
    properties = new Properties();
    try (InputStream is = TestConfigs.class.getClassLoader().getResourceAsStream("testresources.properties")) {
      properties.load(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getProp(String key) {
    return properties.getProperty(key);
  }
}
