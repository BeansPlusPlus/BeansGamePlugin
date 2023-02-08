package beansplusplus.beansgameplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommandExecutor implements CommandExecutor {
  private final GameConfiguration configuration;
  private final GameCreator gameCreator;
  private final GameState state;

  GameCommandExecutor(GameState state, GameConfiguration configuration, GameCreator gameCreator) {
    this.state = state;
    this.configuration = configuration;
    this.gameCreator = gameCreator;
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
    if (state.gameStarted()) {
      sender.sendMessage(ChatColor.DARK_RED + "Game already running.");
      return;
    }

    if (gameCreator.isValidSetup(sender, configuration)) {
      state.startNewGame(gameCreator.createGame(configuration, state));
    }
  }

  private void stop(CommandSender sender) {
    if (!state.gameStarted()) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    state.stopGame();

    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Game has been manually stopped.");
    }
  }

  private void reset(CommandSender sender) {
    if (!state.gameStarted()) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    if (gameCreator.isValidSetup(sender, configuration)) {
      for (Player player : Bukkit.getOnlinePlayers()) {
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Game restarting...");
      }

      state.stopGame();
      state.startNewGame(gameCreator.createGame(configuration, state));
    }
  }

  private void pause(CommandSender sender) {
    if (!state.gameStarted()) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    state.setPaused(!state.isPaused());

    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Game is now " + (state.isPaused() ? "paused" : "unpaused"));
    }
  }

  private void unpause(CommandSender sender) {
    if (!state.gameStarted()) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    state.setPaused(false);

    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Game is now unpaused");
    }
  }
}
