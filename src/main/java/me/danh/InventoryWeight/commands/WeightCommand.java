package me.danh.InventoryWeight.commands;

import me.danh.InventoryWeight.configuration.LanguageConfig;
import me.danh.InventoryWeight.playerweight.PlayerWeight;
import me.danh.InventoryWeight.playerweight.PlayerWeightMap;
import me.danh.InventoryWeight.util.WorldList;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class WeightCommand extends SubCommand {
   public void onCommand(Player player, String[] args) {
      WorldList worldList = WorldList.getInstance();
      if (!player.hasPermission("inventoryweight.off") && !player.getGameMode().equals(GameMode.CREATIVE) && worldList.isInventoryWeightEnabled(player.getWorld().getName())) {
         PlayerWeight playerWeight = (PlayerWeight)PlayerWeightMap.getPlayerWeightMap().get(player.getUniqueId());
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getWeight() + ": &a" + playerWeight.getWeight() + " &f / &c" + playerWeight.getMaxWeight()));
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getSpeed() + ": &a" + playerWeight.getPercentage() + "%"));
         player.sendMessage(ChatColor.WHITE + "[" + playerWeight.getSpeedDisplay() + ChatColor.WHITE + "]");
      } else {
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getNoPermission()));
      }
   }

   public String name() {
      return "weight";
   }

   public String info() {
      return "/iw weight";
   }

   public String[] aliases() {
      return new String[0];
   }
}
