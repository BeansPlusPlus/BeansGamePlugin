package beansplusplus.beansgameplugin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class RuleCommandExecutor implements CommandExecutor {
  private String name;
  private List<String> pages;

  RuleCommandExecutor(String name, List<String> pages) {
    this.name = name;
    this.pages = pages;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) return false;

    Player player = (Player) sender;

    ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    BookMeta bookMeta = (BookMeta) book.getItemMeta();
    bookMeta.setAuthor("SomeBean");
    bookMeta.setTitle(name + " rules");

    for (String page : pages) {
      bookMeta.addPage(page);
    }

    book.setItemMeta(bookMeta);

    player.getInventory().addItem(book);

    return true;
  }
}
