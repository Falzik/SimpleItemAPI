package me.falzik.vanilla.fastInteractItems.items;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface Item {

    void setName(String name);
    void click(Player player);
    void action(Consumer<Player> action);
    void give(Player player);

}
