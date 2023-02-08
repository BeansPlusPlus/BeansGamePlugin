package beansplusplus.beansgameplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;

public class BeansGamePlugin extends JavaPlugin {
  public void registerGame(InputStream configInputStream, GameStartValidator validator, GameSupplier gameSupplier) {
    GameConfiguration configuration = ConfigLoader.loadConfig(configInputStream);

    ConfigCommandExecutor configCommandExecutor = new ConfigCommandExecutor(configuration);
    GameCommandExecutor gameCommandExecutor = new GameCommandExecutor(configuration, validator, gameSupplier);

    getCommand("config").setTabCompleter(configCommandExecutor);
    getCommand("config").setExecutor(configCommandExecutor);
    getCommand("start").setExecutor(gameCommandExecutor);
    getCommand("stop").setExecutor(gameCommandExecutor);
    getCommand("reset").setExecutor(gameCommandExecutor);
    getCommand("pause").setExecutor(gameCommandExecutor);
    getCommand("continue").setExecutor(gameCommandExecutor);
  }
}
