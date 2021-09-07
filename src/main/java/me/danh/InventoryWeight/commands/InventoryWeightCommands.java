package me.danh.InventoryWeight.commands;

import java.util.HashMap;
import me.danh.InventoryWeight.InventoryWeight;
import me.danh.InventoryWeight.configuration.LanguageConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryWeightCommands implements CommandExecutor {
   private InventoryWeight plugin = (InventoryWeight)JavaPlugin.getPlugin(InventoryWeight.class);
   private HashMap<String, SubCommand> subCommands;
   private final String main = "iw";

   public void setup() {
      this.plugin.getCommand("iw").setExecutor(this);
      this.subCommands = new HashMap();
      this.subCommands.put("weight", new WeightCommand());
      this.subCommands.put("help", new HelpCommand());
      this.subCommands.put("get", new GetWeightCommand());
      this.subCommands.put("reload", new ReloadCommand());
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         return false;
      } else {
         Player player = (Player)sender;
         if (args.length < 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getHelpMessage()));
            return true;
         } else {
            String sub = args[0];
            if (this.subCommands.containsKey(sub)) {
               ((SubCommand)this.subCommands.get(sub)).onCommand(player, args);
               return true;
            } else {
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getInvalidCommand()));
               return true;
            }
         }
      }
   }
}
