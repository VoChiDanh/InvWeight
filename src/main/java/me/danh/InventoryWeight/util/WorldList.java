package me.danh.InventoryWeight.util;

import java.util.ArrayList;
import java.util.List;

public class WorldList {
   private static WorldList instance;
   private List<String> worlds = new ArrayList();

   public static void initializeWorldList(List<String> worlds) {
      instance = new WorldList(worlds);
   }

   public static WorldList getInstance() {
      return instance == null ? new WorldList(new ArrayList()) : instance;
   }

   private WorldList(List<String> worlds) {
      this.worlds.addAll(worlds);
   }

   public boolean isInventoryWeightEnabled(String world) {
      return this.worlds.isEmpty() || this.worlds.contains(world);
   }
}
