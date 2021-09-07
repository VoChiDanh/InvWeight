package me.danh.InventoryWeight.commands;

import me.danh.InventoryWeight.configuration.LanguageConfig;
import me.danh.InventoryWeight.util.InventoryCheckUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetWeightCommand extends SubCommand {
   public void onCommand(Player player, String[] args) {
      double weight;
      if (args.length < 2) {
         ItemStack item = player.getInventory().getItemInMainHand();
         if (item.getType() == Material.AIR) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getItemWeight() + " " + 0));
         } else {
            weight = InventoryCheckUtil.getItemWeight(item);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getItemWeight() + " " + weight));
         }
      } else {
         String materialAllCaps = args[1].toUpperCase();
         if (Material.getMaterial(materialAllCaps) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getInvalidMaterial()));
            return;
         }

         weight = (Double)InventoryCheckUtil.mapOfWeightsByMaterial.get(materialAllCaps);
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getItemWeight() + " " + weight));
      }

   }

   public String name() {
      return "get";
   }

   public String info() {
      return "/iw get [Material Name]";
   }

   public String[] aliases() {
      return new String[0];
   }
}
