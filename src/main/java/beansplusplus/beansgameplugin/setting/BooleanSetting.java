package beansplusplus.beansgameplugin.setting;

import beansplusplus.beansgameplugin.ConfigSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BooleanSetting extends ConfigSetting<Boolean> {
  public BooleanSetting(String key, boolean defaultValue) {
    super(key, defaultValue);
  }

  @Override
  public Class<Boolean> getSettingClass() {
    return Boolean.class;
  }

  @Override
  public String validate(String args) {
    if (args.equalsIgnoreCase("false") || args.equalsIgnoreCase("true")) return null;

    return "Must be true or false";
  }

  @Override
  public Boolean parse(String args) {
    return args.equalsIgnoreCase("true");
  }

  public List<String> tabCompletion(String[] args) {
    if (args.length != 2) return new ArrayList<>();

    return Arrays.asList("true", "false");
  }
}
