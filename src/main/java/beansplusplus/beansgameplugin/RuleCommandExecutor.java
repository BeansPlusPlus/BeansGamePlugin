package beansplusplus.beansgameplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RuleCommandExecutor implements CommandExecutor {
  private String name;
  private List<String> rules;

  RuleCommandExecutor(String name, List<String> rules) {
    this.name = name;
    this.rules = rules;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + name + " rules:");

    for (String rule : rules) {
      sender.sendMessage(rule);
    }

    return true;
  }
}
