package tomay0.gameconfig;

import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import tomay0.gameconfig.setting.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigLoader {
  public static class GameConfigError extends Error {
    GameConfigError(String message) {
      super(message);
    }
  }

  public static void loadForPlugin(JavaPlugin plugin) {
    GameConfiguration.setConfigSettings(getConfigSettings(plugin.getResource("config.yml")));

    ConfigCommandExecutor configCommand = new ConfigCommandExecutor();
    plugin.getCommand("config").setTabCompleter(configCommand);
    plugin.getCommand("config").setExecutor(configCommand);
  }

  private static List<ConfigSetting> getConfigSettings(InputStream is) {
    Yaml yaml = new Yaml();

    if (is == null) throw new GameConfigError("Failed to load config.yml. No input stream");

    Map<String, Object> data = yaml.load(is);

    List<ConfigSetting> settings = new ArrayList<>();

    for (String key : data.keySet()) {
      if (!(data.get(key) instanceof Map))
        throw new GameConfigError("Invalid formatted config.yml");

      Map<String, Object> map = (Map<String, Object>) data.get(key);

      if (!map.containsKey("type")) throw new GameConfigError("type required for " + key);

      String type = map.get("type").toString();

      if (type.equalsIgnoreCase("player_list")) {
        settings.add(new PlayerListSetting(key.toLowerCase()));
      } else if (type.equalsIgnoreCase("player")) {
        settings.add(new PlayerSetting(key.toLowerCase()));
      } else if (type.equalsIgnoreCase("enum")) {
        settings.add(enumSetting(key, map));
      } else if (type.equalsIgnoreCase("boolean")) {
        settings.add(booleanSetting(key, map));
      } else if (type.equalsIgnoreCase("int")) {
        settings.add(intSetting(key, map));
      } else if (type.equalsIgnoreCase("double")) {
        settings.add(doubleSetting(key, map));
      } else throw new GameConfigError("Invalid type for " + key);
    }

    return settings;
  }

  private static EnumSetting enumSetting(String key, Map<String, Object> map) {
    if (!map.containsKey("options"))
      throw new GameConfigError("options required for type enum in " + key);

    if (!(map.get("options") instanceof List))
      throw new GameConfigError("Options must be a string list for " + key);

    List<String> options = (List<String>) map.get("options");

    String defaultV;

    if (!map.containsKey("default")) defaultV = options.get(0);
    else defaultV = map.get("default").toString();

    if (!options.contains(defaultV))
      throw new GameConfigError("Default must be in list of options for " + key);

    return new EnumSetting(key, defaultV, options);
  }

  private static BooleanSetting booleanSetting(String key, Map<String, Object> map) {
    boolean defaultV = false;

    if (map.containsKey("default")) {
      if (!map.get("default").toString().equalsIgnoreCase("false") && !map.get("default").toString().equalsIgnoreCase("true"))
        throw new GameConfigError("Default boolean value must be true or false");

      defaultV = map.get("default").toString().equalsIgnoreCase("true");
    }

    return new BooleanSetting(key, defaultV);
  }

  private static IntSetting intSetting(String key, Map<String, Object> map) {
    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;

    if (map.containsKey("min")) {
      try {
        min = Integer.parseInt(map.get("min").toString());
      } catch (NumberFormatException e) {
        throw new GameConfigError("Min is not an integer for " + key);
      }
    }

    if (map.containsKey("max")) {
      try {
        max = Integer.parseInt(map.get("max").toString());
      } catch (NumberFormatException e) {
        throw new GameConfigError("Max is not an integer for " + key);
      }
    }

    int defaultV = 0;

    if (map.containsKey("default")) {
      try {
        defaultV = Integer.parseInt(map.get("default").toString());
      } catch (NumberFormatException e) {
        throw new GameConfigError("Max is not an integer for " + key);
      }
    }

    if (defaultV < min || defaultV > max)
      throw new GameConfigError("Default value must be between bounds for " + key);

    return new IntSetting(key, defaultV, min, max);
  }

  private static DoubleSetting doubleSetting(String key, Map<String, Object> map) {
    double min = -Double.MAX_VALUE;
    double max = Double.MAX_VALUE;

    if (map.containsKey("min")) {
      try {
        min = Double.parseDouble(map.get("min").toString());
      } catch (NumberFormatException e) {
        throw new GameConfigError("Min is not a double for " + key);
      }
    }

    if (map.containsKey("max")) {
      try {
        max = Double.parseDouble(map.get("max").toString());
      } catch (NumberFormatException e) {
        throw new GameConfigError("Max is not a double for " + key);
      }
    }

    double defaultV = 0;

    if (map.containsKey("default")) {
      try {
        defaultV = Double.parseDouble(map.get("default").toString());
      } catch (NumberFormatException e) {
        throw new GameConfigError("Max is not a double for " + key);
      }
    }

    if (defaultV < min || defaultV > max)
      throw new GameConfigError("Default value must be between bounds for " + key);

    return new DoubleSetting(key, defaultV, min, max);
  }
}
