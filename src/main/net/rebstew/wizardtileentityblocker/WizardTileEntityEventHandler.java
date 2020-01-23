package net.rebstew.wizardtileentityblocker;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class WizardTileEntityEventHandler implements Listener {

  private WizardTileEntityBlocker plugin;

  WizardTileEntityEventHandler(WizardTileEntityBlocker plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onInventoryOpen(InventoryOpenEvent e){
    String worldName = e.getPlayer().getWorld().getName();
    Inventory inventoryType = e.getInventory();

    List<String> worldConfig = plugin.getWorldConfig(worldName);
    if(!worldConfig.isEmpty()){
      if( (inventoryType instanceof AnvilInventory && worldConfig.contains(Material.ANVIL.name()))
        | (inventoryType instanceof GrindstoneInventory && worldConfig.contains(Material.GRINDSTONE.name()))
      ){
        e.setCancelled(true);
        e.getPlayer().sendMessage("You can't use this block on this world!");
      }
    }

  }

//  public void onRightClick(PlayerInteractEvent e){
//    if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
//      String worldName = e.getPlayer().getWorld().getName();
//      Material material = e.getMaterial();
//
//      List<String> worldConfig = plugin.getWorldConfig(worldName);
//      if(!worldConfig.isEmpty()){
//        if(worldConfig.contains(material.name())){
//          e.setCancelled(true);
//        }
//      }
//    }
//  }
}
