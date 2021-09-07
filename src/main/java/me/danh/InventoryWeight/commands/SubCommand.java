package me.danh.InventoryWeight.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {
   public abstract void onCommand(Player var1, String[] var2);

   public abstract String name();

   public abstract String info();

   public abstract String[] aliases();
}
