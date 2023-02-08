package beansplusplus.beansgameplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListener implements Listener {
  private String name;
  private GameState state;

  GameListener(String name, GameState state) {
    this.name = name;
    this.state = state;
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    if (state.isPaused()) {
      e.getTo().setX(e.getFrom().getX());
      e.getTo().setZ(e.getFrom().getZ());
    }
  }

  @EventHandler
  public void onBreakBlock(BlockBreakEvent e) {
    if (state.isPaused()) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlaceBlock(BlockPlaceEvent e) {
    if (state.isPaused()) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent e) {
    if (state.isPaused()) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();

    player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Welcome to " + name);
    player.sendMessage(ChatColor.RED + "/rules" + ChatColor.WHITE + " to see the game rules");
    player.sendMessage(ChatColor.RED + "/config" + ChatColor.WHITE + " to adjust game configuration");
    player.sendMessage(ChatColor.RED + "/start" + ChatColor.WHITE + " to start the game");
  }
}
