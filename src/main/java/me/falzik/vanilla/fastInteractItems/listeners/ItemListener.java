package me.falzik.vanilla.fastInteractItems.listeners;


import me.falzik.vanilla.fastInteractItems.items.SimpleItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Falzik
 * Created time: 28.04.2025 11:33
 */

public class ItemListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR &&
                e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = e.getPlayer();
        ItemStack item = e.getItem();

        if (SimpleItem.isSimpleItem(item)) {
            e.setCancelled(true);

            SimpleItem simpleItem = SimpleItem.getSimpleItemBy(item);
            if (simpleItem != null) {
                simpleItem.click(player);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;

        ItemStack clicked = e.getCurrentItem();
        if (SimpleItem.isSimpleItem(clicked)) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            SimpleItem simpleItem = SimpleItem.getSimpleItemBy(clicked);
            if (simpleItem != null) {
                simpleItem.click(player);
            }
        }
    }
}
