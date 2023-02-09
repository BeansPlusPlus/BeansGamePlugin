package beansplusplus.beansgameplugin;

public class GameState {
  private Game currentGame;

  private boolean paused = false;

  public boolean isPaused() {
    return paused;
  }

  public Game getCurrentGame() { return currentGame; }

  public void stopGame() {
    if (currentGame == null) return;

    currentGame.cleanUp();
    currentGame = null;
  }

  public boolean gameStarted() {
    return currentGame != null;
  }

  public void startNewGame(Game game) {
    currentGame = game;
    currentGame.start();
    paused = false;
  }

  public void setPaused(boolean paused) {
    this.paused = paused;
  }
}
