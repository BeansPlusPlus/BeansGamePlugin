package beansplusplus.beansgameplugin.setting;

import beansplusplus.beansgameplugin.ConfigSetting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerSetting extends ConfigSetting<String> {
  public PlayerSetting(String key) {
    super(key, null);
  }

  @Override
  public Class<String> getSettingClass() {
    return String.class;
  }

  @Override
  public String validate(String args) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (player.getName().equalsIgnoreCase(args)) {
        return null;
      }
    }

    return "Must be a player";
  }

  @Override
  public String parse(String args) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (player.getName().equalsIgnoreCase(args)) {
        return player.getName();
      }
    }

    throw new IllegalArgumentException("Invalid player: " + args);
  }

  public List<String> tabCompletion(String[] args) {
    if (args.length != 2) return new ArrayList<>();

    return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
  }
}
