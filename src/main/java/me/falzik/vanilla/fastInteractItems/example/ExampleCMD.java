package me.falzik.vanilla.fastInteractItems.example;


import me.falzik.vanilla.fastInteractItems.items.SimpleItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Author: Falzik
 * Created time: 15.05.2025 15:31
 */

public class ExampleCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return false;
        ItemStack itemStack = new ItemStack(Material.REDSTONE);
        SimpleItem simpleItem = new SimpleItem(itemStack);
        simpleItem.setAction(Action.RIGHT_CLICK_AIR);
        simpleItem.setCanDrop(true);
        simpleItem.setCanChangePosition(false);
        simpleItem.setClickAction((player1 -> {
            player1.sendMessage("Ou! You click on the cool Item!");
        }));
        simpleItem.give(player);
        return true;
    }
}
