package me.falzik.vanilla.fastInteractItems.items;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class SimpleItem implements Item {
    private static final Map<UUID, SimpleItem> registeredItems = new HashMap<>();
    private static NamespacedKey itemKey;

    private final UUID uniqueId;
    private final ItemStack itemStack;
    private Consumer<Player> clickAction;

    static {
        // Инициализация ключа для NBT тегов
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(SimpleItem.class);
        itemKey = new NamespacedKey(plugin, "simpleitem-id");
    }

    public SimpleItem(String name, ItemStack itemStack) {
        this.uniqueId = UUID.randomUUID();
        this.itemStack = markItem(itemStack, uniqueId);

        setName(name);
        registeredItems.put(uniqueId, this);
    }

    private ItemStack markItem(ItemStack item, UUID id) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(itemKey, PersistentDataType.STRING, id.toString());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public void click(Player player) {
        if (clickAction != null) {
            clickAction.accept(player);
        }
    }

    @Override
    public void action(Consumer<Player> action) {
        this.clickAction = action;
    }

    @Override
    public void give(Player player) {
        player.getInventory().addItem(itemStack.clone());
    }

    public static boolean isSimpleItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return false;

        ItemMeta meta = itemStack.getItemMeta();
        return meta.getPersistentDataContainer().has(itemKey, PersistentDataType.STRING);
    }

    public static SimpleItem getSimpleItemBy(ItemStack itemStack) {
        if (!isSimpleItem(itemStack)) return null;

        ItemMeta meta = itemStack.getItemMeta();
        String uuidString = meta.getPersistentDataContainer().get(itemKey, PersistentDataType.STRING);

        try {
            UUID id = UUID.fromString(uuidString);
            return registeredItems.get(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static void cleanup() {
        registeredItems.clear();
    }
}
