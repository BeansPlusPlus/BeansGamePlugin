package tomay0.gameconfig.setting;

import tomay0.gameconfig.ConfigSetting;

public class DoubleSetting extends ConfigSetting<Double> {
  private double min, max;

  public DoubleSetting(String key, double defaultValue, double min, double max) {
    super(key, defaultValue);

    this.min = min;
    this.max = max;
  }

  @Override
  public Class<Double> getSettingClass() {
    return Double.class;
  }

  @Override
  public String validate(String args) {
    try {
      double v = Double.parseDouble(args);

      if (v < min) return "Value must be at least " + min;
      if (v > max) return "Value must be at maximum " + max;

      return null;
    } catch(NumberFormatException e) {
      return "Not a valid number";
    }
  }

  @Override
  public Double parse(String args) {
    return Double.parseDouble(args);
  }
}
