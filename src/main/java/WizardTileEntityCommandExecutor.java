package java;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class WizardTileEntityCommandExecutor implements CommandExecutor {

  private WizardTileEntityBlocker plugin;

  private List<Material> allowedMaterials = Arrays.asList(
    Material.GRINDSTONE, Material.ANVIL
  );

  WizardTileEntityCommandExecutor(WizardTileEntityBlocker plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args) {
    if(args.length < 2) {
      return false;
    }

    if(!"list".equalsIgnoreCase(args[0]) && !"block".equalsIgnoreCase(args[0])){
      return false;
    }

    String worldName = args[1];
    List<String> worldList = plugin.getWorldConfig(worldName);

    //case list
    if("list".equalsIgnoreCase(args[0])){
      commandSender.sendMessage("Blocked Tile Entities in world " + worldName + ": " + worldList);
      return true;
    } else if("block".equalsIgnoreCase(args[0])){
      //case block a material
      if(args.length < 3) return false;

      String blockName = args[2];
      Material blockMaterial = Material.getMaterial(blockName);

      if(blockMaterial == null){
        commandSender.sendMessage("unrecognized material " + blockName + ", check the list on https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html");
        return true;
      }

      if(!allowedMaterials.contains(blockMaterial)){
        commandSender.sendMessage("material is not allowed, must be one of: " + allowedMaterials);
        return true;
      }

      List<String> currentBlockedMaterials = plugin.blockMaterialInWorld(worldName, blockMaterial);
      commandSender.sendMessage("Material " + blockMaterial.name() + " blocked in world " + worldName + " with success!");
      commandSender.sendMessage("Current blocked materials on this world: " + currentBlockedMaterials);
      return true;
    }

    return true;
  }


}
