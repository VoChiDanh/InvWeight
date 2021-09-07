package me.danh.InventoryWeight.commands;

import me.danh.InventoryWeight.configuration.LanguageConfig;
import me.danh.InventoryWeight.util.InventoryCheckUtil;
import me.danh.InventoryWeight.util.MaterialUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetWeightCommand extends SubCommand {
   public void onCommand(Player player, String[] args) {
      if (!player.hasPermission("inventoryweight.set")) {
         player.sendMessage(LanguageConfig.getConfig().getMessages().getNoPermission());
      } else if (args.length >= 3 && args[2].matches("\\d+(\\.\\d{1,2})?")) {
         if (!MaterialUtil.isMaterialValid(args[1])) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getInvalidMaterial()));
         } else {
            String materialAllCaps = args[1].toUpperCase();
            double weight = Double.parseDouble(args[2]);
            InventoryCheckUtil.mapOfWeightsByMaterial.put(materialAllCaps, weight);
            player.sendMessage(ChatColor.GREEN + "Set the weight of " + materialAllCaps + " to " + weight);
         }
      } else {
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getInvalidCommand()));
      }
   }

   public String name() {
      return "set";
   }

   public String info() {
      return "/iw set [Material Name] [weight]";
   }

   public String[] aliases() {
      return new String[0];
   }
}
