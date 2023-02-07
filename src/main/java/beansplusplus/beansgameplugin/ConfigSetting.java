package beansplusplus.beansgameplugin;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigSetting<T> {
  private String key;
  private T defautValue;

  public String getKey() {
    return key;
  }

  public T getDefault() {
    return defautValue;
  }

  public ConfigSetting(String key, T defaultValue) {
    this.key = key.toLowerCase();
    this.defautValue = defaultValue;
  }


  public String stringFormat(T value) {
    if (value == null) return "[none]";

    return value.toString();
  }

  public abstract String validate(String args);

  public abstract T parse(String args);

  public abstract Class<T> getSettingClass();

  public List<String> tabCompletion(String[] args) {
    return new ArrayList<>();
  }
}
