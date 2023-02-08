package beansplusplus.beansgameplugin;

import org.bukkit.command.CommandSender;

import java.io.InputStream;
import java.util.List;

public interface GameCreator {
  Game createGame(GameConfiguration configuration, GameState state);

  boolean isValidSetup(CommandSender sender, GameConfiguration config);

  InputStream config();

  List<String> rulePages();

  String name();
}
