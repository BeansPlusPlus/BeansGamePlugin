package tomay0.gameconfig.setting;

import tomay0.gameconfig.ConfigSetting;

public class IntSetting extends ConfigSetting<Integer> {
  private int min, max;

  public IntSetting(String key, int defaultValue, int min, int max) {
    super(key, defaultValue);

    this.min = min;
    this.max = max;
  }

  @Override
  public Class<Integer> getSettingClass() {
    return Integer.class;
  }

  @Override
  public String validate(String args) {
    try {
      int v = Integer.parseInt(args);

      if (v < min) return "Value must be at least " + min;
      if (v > max) return "Value must be at maximum " + max;

      return null;
    } catch(NumberFormatException e) {
      return "Not a valid number";
    }
  }

  @Override
  public Integer parse(String args) {
    return Integer.parseInt(args);
  }
}
