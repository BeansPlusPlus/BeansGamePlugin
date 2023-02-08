package beansplusplus.beansgameplugin;

import org.bukkit.command.CommandSender;

public interface GameStartValidator {
  boolean isValid(CommandSender sender, GameConfiguration config);

  static GameStartValidator alwaysValid() {
    return (sender, config) -> true;
  }
}
