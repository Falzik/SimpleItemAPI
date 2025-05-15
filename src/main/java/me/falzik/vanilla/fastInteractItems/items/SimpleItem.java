package me.falzik.vanilla.fastInteractItems.items;


import me.falzik.vanilla.fastInteractItems.FastInteractItems;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class SimpleItem implements Item {

    private final ItemStack itemStack;

    private static final HashMap<Integer, SimpleItem> simpleItems = new HashMap<>();

    private final int id;

    private Consumer<Player> action;

    private Action clickAction;

    private boolean canDrop = true;
    private boolean canChangePosition = true;

    public SimpleItem(ItemStack itemStack) {
        this.itemStack = itemStack;

        int randomID = new Random().nextInt(100000);

        id = randomID;

        simpleItems.put(randomID, this);

        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("simpleitem", FastInteractItems.getInstance()),
                PersistentDataType.INTEGER,
                id
        );
        itemStack.setItemMeta(meta);
    }

    @Override
    public ItemStack getItem() {
        return itemStack;
    }

    public void setAction(Action action) {
        this.clickAction = action;
    }

    public void setClickAction(Consumer<Player> action) {
        this.action = action;
    }

    public Action getClickAction() {
        return clickAction;
    }

    public boolean isCanDrop() {
        return canDrop;
    }

    public boolean isCanChangePosition() {
        return canChangePosition;
    }

    @Override
    public void click(Player player, Action action) {
        if(this.clickAction == action) {
            if(this.action != null) {
                this.action.accept(player);
            }
        }
    }

    public void setCanDrop(boolean canDrop) {
        this.canDrop = canDrop;
    }

    public void setCanChangePosition(boolean canChangePosition) {
        this.canChangePosition = canChangePosition;
    }

    public static SimpleItem getSimpleItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return null;

        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        NamespacedKey key = NamespacedKey.fromString("simpleitem", FastInteractItems.getInstance());

        if (pdc.has(key, PersistentDataType.INTEGER)) {
            int id = pdc.get(key, PersistentDataType.INTEGER);
            return simpleItems.get(id);
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
