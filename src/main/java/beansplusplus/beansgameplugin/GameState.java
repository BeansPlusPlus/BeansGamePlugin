package beansplusplus.beansgameplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameState {
  private Game currentGame;

  private boolean paused = false;

  public boolean isPaused() {
    return paused;
  }

  public void stopGame() {
    if (currentGame == null) return;

    currentGame.cleanUp();
    currentGame = null;
  }

  boolean gameStarted() {
    return currentGame != null;
  }

  void startNewGame(Game game) {
    currentGame = game;
    currentGame.start();
    paused = false;
  }

  void setPaused(boolean paused) {
    this.paused = paused;
  }
}
