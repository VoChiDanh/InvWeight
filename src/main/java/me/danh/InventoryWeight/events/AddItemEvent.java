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
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AddItemEvent implements Listener {
   @EventHandler
   public void onEntityPickup(EntityPickupItemEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.hasPermission("inventoryweight.off")) {
            ItemStack itemPickedUp = event.getItem().getItemStack();
            double weight = InventoryCheckUtil.getItemWeight(itemPickedUp);
            double amount = (double)itemPickedUp.getAmount() * weight;
            double oldWeight;
            if (PlayerWeightMap.getPlayerWeightMap().get(player.getUniqueId()) == null) {
               oldWeight = InventoryCheckUtil.getInventoryWeight(player.getInventory().getContents());
               PlayerWeightMap.getPlayerWeightMap().put(player.getUniqueId(), new PlayerWeight(oldWeight, player.getUniqueId()));
            }

            oldWeight = ((PlayerWeight)PlayerWeightMap.getPlayerWeightMap().get(player.getUniqueId())).getWeight();
            ((PlayerWeight)PlayerWeightMap.getPlayerWeightMap().get(player.getUniqueId())).setWeight(oldWeight + amount);
            if (((InventoryWeight)JavaPlugin.getPlugin(InventoryWeight.class)).showWeightChange) {
               player.sendMessage(ChatColor.RED + "Your weight has risen from " + oldWeight + " to " + (amount + oldWeight));
            }

         }
      }
   }
}
