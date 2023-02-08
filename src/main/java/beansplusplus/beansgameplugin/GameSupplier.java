package beansplusplus.beansgameplugin;

public interface GameSupplier {
  Game get(GameConfiguration configuration, GameState state);
}
