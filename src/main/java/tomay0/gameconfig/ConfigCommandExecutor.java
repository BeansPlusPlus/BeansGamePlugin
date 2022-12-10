package tomay0.gameconfig;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigCommandExecutor implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    try {
      if (args.length < 2) {
        showConfiguration(sender);
        return false;
      }

      String key = args[0].toLowerCase();

      if (!GameConfiguration.getConfig().hasKey(key)) {
        showConfiguration(sender);
        return false;
      }

      String argsJoined = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));

      ConfigSetting setting = GameConfiguration.getConfig().getSetting(args[0].toLowerCase());

      String error = setting.validate(argsJoined);

      if (error != null) {
        sender.sendMessage(ChatColor.RED + error);
        return false;
      }

      Object v = setting.parse(argsJoined);

      GameConfiguration.getConfig().setValue(key, v);

      for (Player player : Bukkit.getOnlinePlayers()) {
        player.sendMessage(ChatColor.YELLOW + key + ChatColor.WHITE + " set to " + ChatColor.RED + GameConfiguration.getConfig().formatValue(key));
      }
    } catch (NumberFormatException e) {
      sender.sendMessage(ChatColor.DARK_RED + "Invalid number entered");
    }

    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    if (args.length == 1) {
      return GameConfiguration.getConfig().getKeys();
    }

    String key = args[0].toLowerCase();

    if (!GameConfiguration.getConfig().hasKey(key)) return new ArrayList<>();

    return GameConfiguration.getConfig().getSetting(key).tabCompletion(args);
  }


  private void showConfiguration(CommandSender sender) {
    sender.sendMessage("[Current Game Configuration]");

    for (String key : GameConfiguration.getConfig().getKeys()) {
      sender.sendMessage(ChatColor.YELLOW + key + ": " + ChatColor.RED + GameConfiguration.getConfig().formatValue(key));
    }
  }
}
