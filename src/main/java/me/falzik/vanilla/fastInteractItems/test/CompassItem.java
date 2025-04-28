package me.falzik.vanilla.fastInteractItems.test;


import me.falzik.vanilla.fastInteractItems.items.SimpleItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Falzik
 * Created time: 28.04.2025 11:36
 */

public class CompassItem {

    public static void compass(Player player) {
        SimpleItem simpleItem = new SimpleItem("&cKill Item", new ItemStack(Material.REDSTONE));
        simpleItem.action((player1 -> {
            player1.sendRichMessage("<red>Ou shit!");
            player1.setHealth(0.0);
        }));
        simpleItem.give(player);
    }

}
