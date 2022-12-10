package tomay0.gameconfig.setting;

import tomay0.gameconfig.ConfigSetting;

import java.util.*;
import java.util.stream.Collectors;

public class EnumSetting extends ConfigSetting<String> {
  private Set<String> options;

  public EnumSetting(String key, String defaultValue, Collection<String> options) {
    super(key, defaultValue);

    this.options = Set.copyOf(options);
  }

  @Override
  public Class<String> getSettingClass() {
    return String.class;
  }

  @Override
  public String validate(String args) {
    for (String o : options) {
      if (o.equalsIgnoreCase(args)) {
        return null;
      }
    }

    return "Must be one of: " + options.stream().collect(Collectors.joining(", "));
  }

  @Override
  public String parse(String args) {
    for (String o : options) {
      if (o.equalsIgnoreCase(args)) {
        return o;
      }
    }

    throw new IllegalArgumentException("Invalid enum: " + args);
  }

  public List<String> tabCompletion(String[] args) {
    if (args.length != 2) return new ArrayList<>();

    return List.copyOf(options);
  }
}
