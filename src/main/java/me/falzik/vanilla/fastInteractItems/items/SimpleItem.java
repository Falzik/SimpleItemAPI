package me.falzik.vanilla.fastInteractItems.items;


import me.falzik.vanilla.fastInteractItems.FastInteractItems;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class SimpleItem implements Item {

    private final ItemStack itemStack;

    private static final HashMap<Integer, SimpleItem> simpleItems = new HashMap<>();

    private Consumer<Player> action;

    private List<Action> actions;

    private boolean canDrop = true;
    private boolean canChangePosition = true;

    private final Plugin plugin;

    public SimpleItem(ItemStack itemStack, boolean isCanDrop, boolean isCanChangePosition, Plugin plugin) {
        this.itemStack = itemStack;
        this.plugin = plugin;

        int randomID = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);

        simpleItems.put(randomID, this);
        ItemMeta itemMeta = itemStack.getItemMeta();

        NamespacedKey key = new NamespacedKey(plugin, "simpleItem");
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, randomID);

        itemStack.setItemMeta(itemMeta);

        this.canDrop = isCanDrop;
        this.canChangePosition = isCanChangePosition;

        this.actions = List.of(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK);
    }

    @Override
    public ItemStack getItem() {
        return itemStack;
    }

    public void setAction(List<Action> action) {
        this.actions = action;
    }

    public void setClickAction(Consumer<Player> action) {
        this.action = action;
    }

    public List<Action> getClickAction() {
        return actions;
    }

    public boolean isCanDrop() {
        return canDrop;
    }

    public boolean isCanChangePosition() {
        return canChangePosition;
    }

    @Override
    public void click(Player player, Action action) {
        Consumer<Player> playerAction = this.action;
        if(!actions.contains(action)) return;
        if(playerAction == null) return;
        playerAction.accept(player);
    }

    public void setCanDrop(boolean canDrop) {
        this.canDrop = canDrop;
    }

    public void setCanChangePosition(boolean canChangePosition) {
        this.canChangePosition = canChangePosition;
    }

    public static SimpleItem getSimpleItem(ItemStack itemStack) {
        if (itemStack == null) return null;

        ItemMeta meta = itemStack.getItemMeta();
        if(meta.getPersistentDataContainer().has(NamespacedKey.fromString("simpleItem"))) {
            return simpleItems.get(
                    meta.getPersistentDataContainer().get(NamespacedKey.fromString("simpleItem"),
                            PersistentDataType.INTEGER)
            );
        }
        return null;
    }

    @Override
    public void give(Player player) {
        player.getInventory().addItem(itemStack);
    }

    @Override
    public void giveInSlot(Player player, int slot) {
        player.getInventory().setItem(slot, itemStack);
    }
}
