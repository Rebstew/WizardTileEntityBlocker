package java;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class WizardTileEntityBlocker extends JavaPlugin {

    private Map<String, List<String>> worldConfigsMap;

    @Override
    public void onEnable() {
        loadWorldConfigMap();
        this.getCommand("wtileentityblocker").setExecutor(new WizardTileEntityCommandExecutor(this));
        Bukkit.getPluginManager().registerEvents(new WizardTileEntityEventHandler(this), this);
    }

    @Override
    public void onDisable() {

    }

    @NotNull
    List<String> getWorldConfig(String worldName){
        return worldConfigsMap.containsKey(worldName) ? worldConfigsMap.get(worldName) : new ArrayList<>();
    }

    List<String> blockMaterialInWorld(String worldName, Material material){
        List<String> currentStrings = getConfig().getStringList("worlds." + worldName);

        if(!currentStrings.contains(material.name())){
            currentStrings.add(material.name());
        }

        writeWorldsMap();

        return currentStrings;
    }

    private void loadWorldConfigMap(){
        worldConfigsMap = new HashMap<>();

        ConfigurationSection worldSection = getConfig().getConfigurationSection("worlds");
        if(worldSection != null) {
            Set<String> keys = worldSection.getKeys(false);

            for (String key : keys) {
                List<String> worldBlocks = worldSection.getStringList(key);
                worldConfigsMap.put(key, worldBlocks);
            }
        }
    }

    private void writeWorldsMap(){
        for(Map.Entry<String, List<String>> entry : worldConfigsMap.entrySet()){
            String worldName = entry.getKey();
            List<String> worldBlockedItems = entry.getValue();

            getConfig().set("worlds." + worldName, worldBlockedItems);
        }

        saveConfig();
    }
}
