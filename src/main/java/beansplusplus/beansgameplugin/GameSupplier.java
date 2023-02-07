package beansplusplus.beansgameplugin;

import org.bukkit.command.CommandSender;

public interface GameSupplier {
  Game get(CommandSender sender, GameConfiguration configuration);
}
