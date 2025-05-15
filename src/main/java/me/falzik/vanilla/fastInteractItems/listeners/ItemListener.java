package me.falzik.vanilla.fastInteractItems.listeners;


import me.falzik.vanilla.fastInteractItems.items.SimpleItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Falzik
 * Created time: 28.04.2025 11:33
 */

public class ItemListener implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getItem() == null) return;

        SimpleItem simpleItem = SimpleItem.getSimpleItem(e.getItem());
        if (simpleItem == null) return;

        if (simpleItem.getClickAction() == e.getAction()) {
            simpleItem.click(e.getPlayer(), e.getAction());
        }
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;

        SimpleItem simpleItem = SimpleItem.getSimpleItem(e.getCurrentItem());
        if (simpleItem != null && !simpleItem.isCanChangePosition()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerDropItemEvent e) {
        ItemStack droppedItem = e.getItemDrop().getItemStack();
        SimpleItem simpleItem = SimpleItem.getSimpleItem(droppedItem);

        if (simpleItem != null && !simpleItem.isCanDrop()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(BlockPlaceEvent e) {
        final Player player = e.getPlayer();

        if(SimpleItem.getSimpleItem(e.getItemInHand()) != null) {
            e.setCancelled(true);
        }
    }

}
