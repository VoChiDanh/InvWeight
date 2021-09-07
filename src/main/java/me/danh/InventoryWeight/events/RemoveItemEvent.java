package me.danh.InventoryWeight.events;

import me.danh.InventoryWeight.InventoryWeight;
import me.danh.InventoryWeight.playerweight.PlayerWeight;
import me.danh.InventoryWeight.playerweight.PlayerWeightMap;
import me.danh.InventoryWeight.util.InventoryCheckUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class RemoveItemEvent implements Listener {
   @EventHandler
   public void onEntityDropEvent(PlayerDropItemEvent event) {
      Player player = event.getPlayer();
      if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.hasPermission("inventoryweight.off")) {
         ItemStack itemDropped = event.getItemDrop().getItemStack();
         int amountDropped = itemDropped.getAmount();
         double weight = InventoryCheckUtil.getItemWeight(itemDropped);
         double oldWeight = ((PlayerWeight)PlayerWeightMap.getPlayerWeightMap().get(player.getUniqueId())).getWeight();
         ((PlayerWeight)PlayerWeightMap.getPlayerWeightMap().get(player.getUniqueId())).setWeight(oldWeight - (double)amountDropped * weight);
         if (((InventoryWeight)JavaPlugin.getPlugin(InventoryWeight.class)).showWeightChange) {
            player.sendMessage(ChatColor.GREEN + "Your weight has fallen from " + oldWeight + " to " + (oldWeight - (double)amountDropped * weight));
         }

      }
   }
}
