package beansplusplus.beansgameplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCommandExecutor implements CommandExecutor {
  private GameConfiguration configuration;
  private GameSupplier gameSupplier;
  private GameState state;

  public GameCommandExecutor(GameConfiguration configuration, GameSupplier gameSupplier) {
    this.configuration = configuration;
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

    Game game = gameSupplier.get(sender, configuration, state);

    if (game != null) state.startNewGame(game);
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

    Game game = gameSupplier.get(sender, configuration, state);

    if (game != null) {
      state.stopGame();
      state.startNewGame(game);
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
