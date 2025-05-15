package me.falzik.vanilla.fastInteractItems.items;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface Item {

    ItemStack getItem();
    void click(Player player, Action action);
    void give(Player player);
    void giveInSlot(Player player, int slot);

}
