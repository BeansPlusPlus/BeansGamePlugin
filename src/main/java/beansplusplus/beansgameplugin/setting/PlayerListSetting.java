package beansplusplus.beansgameplugin.setting;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import beansplusplus.beansgameplugin.ConfigSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerListSetting extends ConfigSetting<List> {
  public PlayerListSetting(String key) {
    super(key, new ArrayList<String>());
  }

  private PlayerSetting fakeSetting = new PlayerSetting("player");

  @Override
  public Class<List> getSettingClass() {
    return List.class;
  }

  @Override
  public String validate(String args) {
    for (String p : argsToList(args)) {
      String msg;
      if ((msg = fakeSetting.validate(p)) != null) return msg;
    }

    return null;
  }

  @Override
  public List<String> parse(String args) {
    List<String> players = new ArrayList<>();

    for (String p : argsToList(args)) {
      players.add(fakeSetting.parse(p));
    }

    return players;
  }

  private List<String> argsToList(String args) {
    return Arrays.asList(args.replace(",", " ").split("\\s+"));
  }

  @Override
  public String stringFormat(List value) {
    if (value.isEmpty()) {
      return "[]";
    }
    List<String> strList = (List<String>) value;
    return strList.stream().collect(Collectors.joining(", "));
  }

  public List<String> tabCompletion(String[] args) {
    return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
  }
}
