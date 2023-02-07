package beansplusplus.beansgameplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class GameConfigPlugin extends JavaPlugin {
  public void onEnable() {
    ConfigCommandExecutor configCommand = new ConfigCommandExecutor();
    getCommand("config").setTabCompleter(configCommand);
    getCommand("config").setExecutor(configCommand);
  }
}
