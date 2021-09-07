package me.danh.InventoryWeight;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import me.danh.InventoryWeight.commands.InventoryWeightCommands;
import me.danh.InventoryWeight.configuration.LanguageConfig;
import me.danh.InventoryWeight.events.AddItemEvent;
import me.danh.InventoryWeight.events.FreezePlayerEvent;
import me.danh.InventoryWeight.events.JoinEvent;
import me.danh.InventoryWeight.events.RemoveItemEvent;
import me.danh.InventoryWeight.playerweight.PlayerWeight;
import me.danh.InventoryWeight.playerweight.PlayerWeightMap;
import me.danh.InventoryWeight.util.InventoryCheckUtil;
import me.danh.InventoryWeight.util.WorldList;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class InventoryWeight extends JavaPlugin {
   private InventoryWeightCommands commands;
   private LanguageConfig languageConfig;
   public boolean showWeightChange;

   public void onEnable() {
      this.getLogger().info("Đã hoạt động");
      this.registerEvents();
      this.commands = new InventoryWeightCommands();
      this.commands.setup();
      this.saveDefaultConfig();
      this.initConfig();
      this.setUpMessageConfig();
      BukkitScheduler scheduler = this.getServer().getScheduler();
      int timer = this.getConfig().getInt("checkInventoryTime");
      if (timer < 1) {
         timer = 3;
      }

      scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
         public void run() {
            HashMap<UUID, PlayerWeight> mapReference = PlayerWeightMap.getPlayerWeightMap();
            Iterator hmIterator = mapReference.entrySet().iterator();

            while(hmIterator.hasNext()) {
               Entry element = (Entry)hmIterator.next();
               UUID playerId = (UUID)element.getKey();
               PlayerWeight playerWeight = (PlayerWeight)element.getValue();
               Server server = InventoryWeight.this.getServer();
               if (server.getPlayer(playerId) != null) {
                  Inventory inv = server.getPlayer(playerId).getInventory();
                  if (inv != null) {
                     playerWeight.setWeight(InventoryCheckUtil.getInventoryWeight(inv.getContents()));
                  }
               }
            }

         }
      }, 0L, (long)(timer * 20));

      Iterator var3 = this.getServer().getOnlinePlayers().iterator();

      while(var3.hasNext()) {
         Player player = (Player)var3.next();
         JoinEvent.addPlayerToWeightMap(player);
      }

   }

   public void onDisable() {
      this.getLogger().info("Đã ngưng hoạt động");
   }

   private void initConfig() {
      boolean disableMovement = this.getConfig().getBoolean("disableMovement");
      int capacity = this.getConfig().getInt("weightLimit");
      this.showWeightChange = this.getConfig().getBoolean("showWeightChange");
      boolean armorOnly = this.getConfig().getBoolean("armorOnly");
      InventoryCheckUtil.armorOnlyMode = armorOnly;
      float minWeight = (float)this.getConfig().getDouble("minWalkSpeed");
      float maxWeight = (float)this.getConfig().getDouble("maxWalkSpeed");
      List<?> matWeights = this.getConfig().getList("materialWeights");
      List<?> nameWeights = this.getConfig().getList("customItemWeights");
      List<?> loreWeights = this.getConfig().getList("customLoreWeights");
      InventoryCheckUtil.defaultWeight = this.getConfig().getDouble("defaultWeight");
      Iterator var9;
      Object item;
      LinkedHashMap map;
      double weight;
      String itemName;
      if (matWeights != null) {
         var9 = matWeights.iterator();

         while(var9.hasNext()) {
            item = var9.next();
            map = (LinkedHashMap)item;
            weight = this.getDoubleFromConfigValue(map.get("weight"));
            itemName = ((String)map.get("material")).toUpperCase();
            InventoryCheckUtil.mapOfWeightsByMaterial.put(itemName, weight);
         }
      }

      if (nameWeights != null) {
         var9 = nameWeights.iterator();

         while(var9.hasNext()) {
            item = var9.next();
            map = (LinkedHashMap)item;
            weight = this.getDoubleFromConfigValue(map.get("weight"));
            itemName = (String)map.get("name");
            InventoryCheckUtil.mapOfWeightsByDisplayName.put(itemName, weight);
         }
      }

      if (loreWeights != null) {
         var9 = loreWeights.iterator();

         while(var9.hasNext()) {
            item = var9.next();
            map = (LinkedHashMap)item;
            weight = this.getDoubleFromConfigValue(map.get("weight"));
            itemName = (String)map.get("name");
            InventoryCheckUtil.mapOfWeightsByLore.put(itemName, weight);
         }
      }

      InventoryCheckUtil.loreTag = this.getConfig().getString("loreTag");
      PlayerWeight.initialize(disableMovement, capacity, minWeight, maxWeight);
      List<String> worlds = this.getConfig().getStringList("worlds");
      WorldList.initializeWorldList(worlds);
   }

   private double getDoubleFromConfigValue(Object weight) {
      if (weight instanceof Double) {
         return (Double)weight;
      } else {
         int tempInt = (Integer)weight;
         return (double)tempInt;
      }
   }

   private void registerEvents() {
      this.getServer().getPluginManager().registerEvents(new AddItemEvent(), this);
      this.getServer().getPluginManager().registerEvents(new RemoveItemEvent(), this);
      this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
      this.getServer().getPluginManager().registerEvents(new FreezePlayerEvent(), this);
   }

   private void setUpMessageConfig() {
      this.languageConfig = new LanguageConfig();
      this.languageConfig.setUpLanguageConfig();
   }

   public void reloadConfiguration() {
      this.reloadConfig();
      this.setUpMessageConfig();
      this.initConfig();
   }
}
