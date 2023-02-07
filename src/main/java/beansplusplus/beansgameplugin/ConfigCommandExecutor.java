package beansplusplus.beansgameplugin;

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
  private GameConfiguration configuration;

  ConfigCommandExecutor(GameConfiguration configuration) {
    this.configuration = configuration;
  }


  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    try {
      if (args.length < 2) {
        showConfiguration(sender);
        return false;
      }

      String key = args[0].toLowerCase();

      if (!configuration.hasKey(key)) {
        showConfiguration(sender);
        return false;
      }

      String argsJoined = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));

      ConfigSetting setting = configuration.getSetting(args[0].toLowerCase());

      String error = setting.validate(argsJoined);

      if (error != null) {
        sender.sendMessage(ChatColor.RED + error);
        return false;
      }

      Object v = setting.parse(argsJoined);

      configuration.setValue(key, v);

      for (Player player : Bukkit.getOnlinePlayers()) {
        player.sendMessage(ChatColor.YELLOW + key + ChatColor.WHITE + " set to " + ChatColor.RED + configuration.formatValue(key));
      }
    } catch (NumberFormatException e) {
      sender.sendMessage(ChatColor.DARK_RED + "Invalid number entered");
    }

    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    if (args.length == 1) {
      return filterStartWith(configuration.getKeys(), args[0]);
    }

    String key = args[0].toLowerCase();

    if (!configuration.hasKey(key)) return new ArrayList<>();

    return filterStartWith(configuration.getSetting(key).tabCompletion(args), args[args.length - 1]);
  }


  private void showConfiguration(CommandSender sender) {
    sender.sendMessage("[Current Game Configuration]");

    for (String key : configuration.getKeys()) {
      sender.sendMessage(ChatColor.YELLOW + key + ": " + ChatColor.RED + configuration.formatValue(key));
    }
  }

  private List<String> filterStartWith(List<String> options, String startWith) {
    return options.stream().filter((s) -> s.toLowerCase().startsWith(startWith.toLowerCase())).toList();
  }
}
