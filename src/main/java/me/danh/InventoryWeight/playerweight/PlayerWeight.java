package me.danh.InventoryWeight.playerweight;

import java.text.DecimalFormat;
import java.util.UUID;
import me.danh.InventoryWeight.configuration.LanguageConfig;
import me.danh.InventoryWeight.events.FreezePlayerEvent;
import me.danh.InventoryWeight.util.WorldList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerWeight {
   private static boolean disableMovement;
   public static int defaultMaxCapacity;
   private static float minSpeed;
   private static float maxSpeed;
   private double weight;
   private UUID playerId;
   private int maxCapacity;
   private boolean isPlayerFrozen;

   public PlayerWeight(double weight, UUID id) {
      this.weight = weight;
      this.playerId = id;
      this.maxCapacity = defaultMaxCapacity;
      this.isPlayerFrozen = false;
      this.changeSpeed();
   }

   public double getWeight() {
      DecimalFormat priceFormatter = new DecimalFormat("#0.00");
      return Double.parseDouble(priceFormatter.format(this.weight));
   }

   public void setMaxWeight(int max) {
      this.maxCapacity = max;
      this.changeSpeed();
   }

   public int getMaxWeight() {
      return this.maxCapacity;
   }

   public void setWeight(double newWeight) {
      this.weight = newWeight;
      this.changeSpeed();
   }

   private void changeSpeed() {
      Player player = Bukkit.getPlayer(this.playerId);
      if (this.isPluginDisabledForUserOrWorld(player)) {
         player.setWalkSpeed(0.2F);
      } else if (this.weight > (double)this.maxCapacity) {
         this.handleMaxCapacity(player);
      } else {
         if (this.isPlayerFrozen) {
            FreezePlayerEvent.unfreezePlayer(this.playerId);
            this.isPlayerFrozen = false;
         }

         float weightRatio = (float)this.weight / (float)this.maxCapacity;
         float weightFloat = maxSpeed - weightRatio * (maxSpeed - minSpeed);
         if (this.weight == 0.0D) {
            weightFloat = maxSpeed;
         }

         player.setWalkSpeed(weightFloat);
      }
   }

   private boolean isPluginDisabledForUserOrWorld(Player player) {
      WorldList worldList = WorldList.getInstance();
      if (player == null) {
         return true;
      } else if (player.hasPermission("inventoryweight.off")) {
         return true;
      } else if (player.getGameMode().equals(GameMode.CREATIVE)) {
         return true;
      } else {
         return !worldList.isInventoryWeightEnabled(player.getWorld().getName());
      }
   }

   private void handleMaxCapacity(Player player) {
      if (disableMovement && !this.isPlayerFrozen) {
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getCantMoveMessage()));
         FreezePlayerEvent.freezePlayer(this.playerId);
         this.isPlayerFrozen = true;
      } else {
         player.setWalkSpeed(minSpeed);
      }

   }

   public String getSpeedDisplay() {
      StringBuilder speedDisplay = new StringBuilder();
      double ratio = this.weight / (double)this.maxCapacity;
      int result = 20 - (int)(ratio * 20.0D);

      for(int i = 0; i < 20; ++i) {
         if (i < result) {
            speedDisplay.append(ChatColor.GREEN);
            speedDisplay.append("|");
         } else {
            speedDisplay.append(ChatColor.RED);
            speedDisplay.append("|");
         }
      }

      return speedDisplay.toString();
   }

   public int getPercentage() {
      double ratio = this.weight / (double)this.maxCapacity;
      int finalRatio = 100 - (int)(ratio * 100.0D);
      return finalRatio < 0 ? 0 : finalRatio;
   }

   public static void initialize(boolean disableMovement, int capacity, float min, float max) {
      PlayerWeight.disableMovement = disableMovement;
      defaultMaxCapacity = capacity;
      minSpeed = min;
      maxSpeed = max;
   }
}
