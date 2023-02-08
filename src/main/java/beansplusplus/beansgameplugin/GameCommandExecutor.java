package beansplusplus.beansgameplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCommandExecutor implements CommandExecutor {
  private final GameConfiguration configuration;
  private final GameStartValidator validator;
  private final GameSupplier gameSupplier;
  private final GameState state;

  GameCommandExecutor(GameConfiguration configuration, GameStartValidator validator, GameSupplier gameSupplier) {
    this.configuration = configuration;
    this.validator = validator;
    this.gameSupplier = gameSupplier;
    this.state = new GameState();
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

    if (validator.isValid(sender, configuration)) {
      state.startNewGame(gameSupplier.get(configuration, state));
    }
  }

  private void stop(CommandSender sender) {
    if (!state.gameStarted()) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    state.stopGame();
  }

  private void reset(CommandSender sender) {
    if (!state.gameStarted()) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    if (validator.isValid(sender, configuration)) {
      state.stopGame();
      state.startNewGame(gameSupplier.get(configuration, state));
    }
  }

  private void pause(CommandSender sender) {
    if (!state.gameStarted()) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    state.setPaused(!state.isPaused());
  }

  private void unpause(CommandSender sender) {
    if (!state.gameStarted()) {
      sender.sendMessage(ChatColor.DARK_RED + "No game is currently running.");
      return;
    }

    state.setPaused(false);
  }
}
