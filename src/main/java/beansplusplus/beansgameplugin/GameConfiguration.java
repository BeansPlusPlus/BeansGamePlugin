package beansplusplus.beansgameplugin;

import java.util.*;

public class GameConfiguration {
  private static GameConfiguration config;

  static void setConfigSettings(List<ConfigSetting> settings) {
    config = new GameConfiguration(settings);
  }

  public static GameConfiguration getConfig() {
    if (config == null) throw new ConfigLoader.GameConfigError("Game configuration not loaded!");
    return config;
  }

  private Map<String, ConfigSetting> settings = new HashMap<>();
  private Map<String, Object> currentConfig = new HashMap<>();
  private List<String> orderedKeys = new ArrayList<>();

  private GameConfiguration(List<ConfigSetting> settings) {
    for (ConfigSetting setting : settings) {
      this.settings.put(setting.getKey(), setting);
      this.currentConfig.put(setting.getKey(), setting.getDefault());
      this.orderedKeys.add(setting.getKey());
    }
  }

  public <T> T getValue(String key) {
    if (!hasKey(key)) throw new IllegalArgumentException("Unknown setting: " + key);

    Object value = currentConfig.get(key);

    Class<T> ofClass = settings.get(key).getSettingClass();

    if (!ofClass.isInstance(value)) throw new IllegalArgumentException("Invalid setting type: " + key);

    return ofClass.cast(value);
  }

  public <T> void setValue(String key, Object value) {
    if (!hasKey(key)) throw new IllegalArgumentException("Unknown setting: " + key);

    Class ofClass = settings.get(key).getSettingClass();

    if (!ofClass.isInstance(value)) throw new IllegalArgumentException("Invalid setting type: " + key);

    this.currentConfig.put(key, value);
  }

  public String formatValue(String key) {
    if (!hasKey(key)) throw new IllegalArgumentException("Unknown setting: " + key);

    return settings.get(key).stringFormat(currentConfig.get(key));
  }

  public List<String> getKeys() {
    return Collections.unmodifiableList(orderedKeys);
  }

  public boolean hasKey(String key) {
    return settings.containsKey(key);
  }

  public ConfigSetting getSetting(String key) {
    if (!hasKey(key)) throw new IllegalArgumentException("Unknown setting: " + key);

    return settings.get(key);
  }

}
