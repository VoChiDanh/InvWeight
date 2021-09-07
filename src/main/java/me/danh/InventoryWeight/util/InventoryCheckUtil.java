package me.danh.InventoryWeight.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.inventory.ItemStack;

public class InventoryCheckUtil {
   public static HashMap<String, Double> mapOfWeightsByMaterial = new HashMap();
   public static HashMap<String, Double> mapOfWeightsByDisplayName = new HashMap();
   public static HashMap<String, Double> mapOfWeightsByLore = new HashMap();
   public static boolean armorOnlyMode = false;
   public static String loreTag = "";
   public static double defaultWeight;

   public static double getInventoryWeight(ItemStack[] items) {
      double totalWeight = 0.0D;
      int stackSize;
      if (armorOnlyMode) {
         for(int i = 0; i < items.length; ++i) {
            if (items[i] != null && i >= 36 && i <= 40) {
               stackSize = items[i].getAmount();
               totalWeight += (double)stackSize * getItemWeight(items[i]);
            }
         }
      } else {
         ItemStack[] var8 = items;
         stackSize = items.length;

         for(int var5 = 0; var5 < stackSize; ++var5) {
            ItemStack item = var8[var5];
            if (item != null) {
               totalWeight += (double)stackSize * getItemWeight(item);
            }
         }
      }

      return totalWeight;
   }

   public static double getItemWeight(ItemStack item) {
      String name = item.getItemMeta().getDisplayName();
      String type = item.getType().toString();
      String lore = "";
      if (item.getItemMeta().hasLore()) {
         double weight = getWeightFromTag(item.getItemMeta().getLore());
         if (weight > 0.0D) {
            return weight;
         }
      }

      if (mapOfWeightsByDisplayName.containsKey(name)) {
         return (Double)mapOfWeightsByDisplayName.get(name);
      } else if (mapOfWeightsByLore.containsKey(lore)) {
         return (Double)mapOfWeightsByLore.get(lore);
      } else {
         return mapOfWeightsByMaterial.containsKey(type) ? (Double)mapOfWeightsByMaterial.get(type) : defaultWeight;
      }
   }

   public static double getWeightFromTag(List<String> lore) {
      double weight = -1.0D;
      Iterator var3 = lore.iterator();

      while(var3.hasNext()) {
         String line = (String)var3.next();
         if (line.contains(loreTag)) {
            Pattern pattern = Pattern.compile("(?<=\\s)-?\\d+(?:\\.\\d+)?");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
               weight = Double.parseDouble(matcher.group(0));
            }
         }
      }

      return weight;
   }

   public static String convertListToSingleString(List<String> lore) {
      StringBuilder builder = new StringBuilder();
      Iterator var2 = lore.iterator();

      while(var2.hasNext()) {
         String element = (String)var2.next();
         builder.append(element);
      }

      return builder.toString();
   }
}
