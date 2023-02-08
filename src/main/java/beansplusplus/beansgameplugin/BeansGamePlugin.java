package beansplusplus.beansgameplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class BeansGamePlugin extends JavaPlugin {
  public void registerGame(GameCreator gameCreator) {
    GameConfiguration configuration = ConfigLoader.loadConfig(gameCreator.config());

    GameState state = new GameState();

    ConfigCommandExecutor configCommandExecutor = new ConfigCommandExecutor(configuration);
    GameCommandExecutor gameCommandExecutor = new GameCommandExecutor(state, configuration, gameCreator);
    RuleCommandExecutor ruleCommandExecutor = new RuleCommandExecutor(gameCreator.name(), gameCreator.rules());
    GameListener listener = new GameListener(gameCreator.name(), state);

    getCommand("config").setTabCompleter(configCommandExecutor);
    getCommand("config").setExecutor(configCommandExecutor);
    getCommand("start").setExecutor(gameCommandExecutor);
    getCommand("stop").setExecutor(gameCommandExecutor);
    getCommand("reset").setExecutor(gameCommandExecutor);
    getCommand("pause").setExecutor(gameCommandExecutor);
    getCommand("continue").setExecutor(gameCommandExecutor);
    getCommand("rules").setExecutor(ruleCommandExecutor);

    getServer().getPluginManager().registerEvents(listener, this);
  }
}
