package me.danh.InventoryWeight.configuration;

public class MessagesModel {
   private String noPermission;
   private String invalidCommand;
   private String invalidMaterial;
   private String itemWeight;
   private String weight;
   private String speed;
   private String reloadCommand;
   private String helpMessage;
   private String cantMoveMessage;

   public MessagesModel(String noPermission, String invalidCommand, String invalidMaterial, String itemWeight, String weight, String speed, String reloadCommand, String helpMessage, String cantMoveMessage) {
      this.noPermission = noPermission;
      this.invalidCommand = invalidCommand;
      this.invalidMaterial = invalidMaterial;
      this.itemWeight = itemWeight;
      this.weight = weight;
      this.speed = speed;
      this.reloadCommand = reloadCommand;
      this.helpMessage = helpMessage;
      this.cantMoveMessage = cantMoveMessage;
   }

   public String getNoPermission() {
      return this.noPermission;
   }

   public String getInvalidCommand() {
      return this.invalidCommand;
   }

   public String getInvalidMaterial() {
      return this.invalidMaterial;
   }

   public String getItemWeight() {
      return this.itemWeight;
   }

   public String getWeight() {
      return this.weight;
   }

   public String getSpeed() {
      return this.speed;
   }

   public String getReloadCommand() {
      return this.reloadCommand;
   }

   public String getHelpMessage() {
      return this.helpMessage;
   }

   public String getCantMoveMessage() {
      return this.cantMoveMessage;
   }
}
