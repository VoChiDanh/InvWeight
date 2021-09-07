package me.danh.InventoryWeight.commands;

import me.danh.InventoryWeight.configuration.LanguageConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
   public void onCommand(Player player, String[] args) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getHelpMessage()));
   }

   public String name() {
      return "help";
   }

   public String info() {
      return "/iw help";
   }

   public String[] aliases() {
      return new String[0];
   }
}
