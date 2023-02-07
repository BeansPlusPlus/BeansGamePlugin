package beansplusplus.beansgameplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCommandExecutor implements CommandExecutor {
  private GameConfiguration configuration;
  private GameSupplier gameSupplier;

  private Game currentGame = null;

  public GameCommandExecutor(GameConfiguration configuration, GameSupplier gameSupplier) {
    this.configuration = configuration;
    this.gameSupplier = gameSupplier;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (label.equalsIgnoreCase("start")) start(sender);
    else if (label.equalsIgnoreCase("stop")) stop(sender);
    else if (label.equalsIgnoreCase("reset")) reset(sender);
    else if (label.equalsIgnoreCase("pause")) pause(sender);
    else if (label.equalsIgnoreCase("continue") ||
        label.equalsIgnoreCase("unpause")) unpause(sender);

    return true;
  }

  private void start(CommandSender sender) {
    if (currentGame != null) {
      sender.sendMessage(ChatColor.DARK_RED + "Game already running.");
      return;
    }

    currentGame = gameSupplier.get(configuration);

    if (currentGame != null) {
      currentGame.start();
    }
  }

  private void stop(CommandSender sender) {
    if (currentGame == null) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    currentGame.stop();
  }

  private void reset(CommandSender sender) {
    if (currentGame == null) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    Game newGame = gameSupplier.get(configuration);

    if (newGame != null) {
      currentGame.stop();
      currentGame = newGame;
      currentGame.start();
    }
  }

  private void pause(CommandSender sender) {
    if (currentGame == null) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    currentGame.pause();
  }

  private void unpause(CommandSender sender) {
    if (currentGame == null) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    currentGame.unpause();
  }
}
