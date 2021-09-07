package me.danh.InventoryWeight.commands;

import me.danh.InventoryWeight.InventoryWeight;
import me.danh.InventoryWeight.configuration.LanguageConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand extends SubCommand {
   public void onCommand(Player player, String[] args) {
      if (!player.hasPermission("inventoryweight.reload")) {
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getNoPermission()));
      } else {
         ((InventoryWeight)JavaPlugin.getPlugin(InventoryWeight.class)).reloadConfiguration();
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getReloadCommand()));
      }
   }

   public String name() {
      return "reload";
   }

   public String info() {
      return "/iw reload";
   }

   public String[] aliases() {
      return new String[0];
   }
}
