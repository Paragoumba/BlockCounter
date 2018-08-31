package fr.paragoumba.blockcounter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Paragoumba on 05/05/2018.
 */

public class BlockCounter extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("count").setExecutor(new CountCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerInteractEventListener(), this);

    }
}
