package me.falzik.vanilla.fastInteractItems.listeners;


import me.falzik.vanilla.fastInteractItems.items.SimpleItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Falzik
 * Created time: 28.04.2025 11:33
 */

public class ItemListener implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        if(e.getItem() != null) {
            final ItemStack item = e.getItem();
            if(SimpleItem.isSimpleItem(item)) {
                SimpleItem simpleItem = SimpleItem.getSimpleItemBy(item);
                if(simpleItem != null) {
                    simpleItem.click(player);
                }
            }
        }
    }

}
