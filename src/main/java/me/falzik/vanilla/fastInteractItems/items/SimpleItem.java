package me.falzik.vanilla.fastInteractItems.items;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Author: Falzik
 * Created time: 28.04.2025 11:23
 */

public class SimpleItem implements Item {

    private Map<ItemStack, Consumer<Player>> actions = new HashMap<>();
    private static final List<ItemStack> simpleItems = new ArrayList<>();

    private static List<SimpleItem> simpleItemList = new ArrayList<>();

    private String name;
    private ItemStack itemStack;

    public SimpleItem(String name, ItemStack itemStack) {
        this.name = name;
        this.itemStack = itemStack;

        simpleItems.add(itemStack);
        simpleItemList.add(this);

        setName(name);
    }

    @Override
    public void setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public void click(Player player) {
        final Consumer<Player> action = actions.get(itemStack);
        if(action != null) {
            action.accept(player);
        }
    }

    @Override
    public void action(Consumer<Player> action) {
        if(actions.containsKey(itemStack)) {
            actions.remove(itemStack);
            actions.put(itemStack, action);
        } else {
            actions.put(itemStack, action);
        }
    }

    @Override
    public void give(Player player) {
        player.getInventory().addItem(itemStack);
    }

    public static boolean isSimpleItem(ItemStack itemStack) {
        return simpleItems.contains(itemStack);
    }

    public static SimpleItem getSimpleItemBy(ItemStack itemStack) {
        for (SimpleItem simpleItem: simpleItemList) {
            if(simpleItem.itemStack.equals(itemStack)) {
                return simpleItem;
            }
        }

        return null;
    }
}
