package me.danh.InventoryWeight.configuration;

import java.io.File;
import me.danh.InventoryWeight.InventoryWeight;
import org.bukkit.configuration.file.YamlConfiguration;

public class LanguageConfig {
   private static LanguageConfig config;
   private YamlConfiguration yamlConfiguration;
   private MessagesModel messages;
   private InventoryWeight plugin = (InventoryWeight)InventoryWeight.getPlugin(InventoryWeight.class);
   private File configFile;

   public static LanguageConfig getConfig() {
      if (config == null) {
         config = new LanguageConfig();
      }

      return config;
   }

   public LanguageConfig() {
      this.configFile = new File(this.plugin.getDataFolder(), "messages.yml");
      this.configFile = new File(this.plugin.getDataFolder(), "messages.yml");
      if (!this.configFile.exists()) {
         this.configFile.getParentFile().mkdirs();
         this.plugin.saveResource("messages.yml", false);
      }

      this.yamlConfiguration = new YamlConfiguration();

      try {
         this.yamlConfiguration.load(this.configFile);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public MessagesModel getMessages() {
      return this.messages;
   }

   public void setUpLanguageConfig() {
      String noPermission = this.yamlConfiguration.getString("noPermission");
      String invalidCommand = this.yamlConfiguration.getString("invalidCommand");
      String invalidMaterial = this.yamlConfiguration.getString("invalidMaterial");
      String itemWeight = this.yamlConfiguration.getString("itemWeight");
      String weight = this.yamlConfiguration.getString("weight");
      String speed = this.yamlConfiguration.getString("speed");
      String reloadCommand = this.yamlConfiguration.getString("reloadCommand");
      String helpMessage = this.yamlConfiguration.getString("helpMessage");
      String cantMove = this.yamlConfiguration.getString("cantMoveMessage");
      if (cantMove == null || cantMove.isEmpty()) {
         cantMove = "&cYou can't carry your weight anymore, you're going to need to drop some items!";
      }

      this.messages = new MessagesModel(noPermission, invalidCommand, invalidMaterial, itemWeight, weight, speed, reloadCommand, helpMessage, cantMove);
      config = this;
   }
}
