package fr.paragoumba.blockcounter;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * Created by Paragoumba on 05/05/2018.
 */

public class PlayerInteractEventListener implements Listener {

    static HashMap<Player, Entry<Entry<Location, Location>, Boolean>> hashMap = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        Player player = event.getPlayer();

        if (hashMap.get(player) == null) hashMap.put(player, new SimpleEntry<>(null, false));

        Entry<Entry<Location, Location>, Boolean> firstEntry = hashMap.get(player);
        Entry<Location, Location> secondEntry = firstEntry.getKey();

        if (firstEntry.getValue()) {

            Action action = event.getAction();
            Block block = event.getClickedBlock();
            Location blockLoc = block.getLocation();

            if (action == Action.LEFT_CLICK_BLOCK) {

                hashMap.put(player, new SimpleEntry<>(new SimpleEntry<>(blockLoc, secondEntry != null ? secondEntry.getValue() : null), firstEntry.getValue()));
                player.sendMessage("First selection set to x" + blockLoc.getBlockX() + " y" + blockLoc.getBlockY() + " z" + blockLoc.getBlockZ());

            } else if (action == Action.RIGHT_CLICK_BLOCK) {

                hashMap.put(player, new SimpleEntry<>(new SimpleEntry<>(secondEntry != null ? secondEntry.getKey() : null, block.getLocation()), firstEntry.getValue()));

                player.sendMessage("Second selection set to x" + blockLoc.getBlockX() + " y" + blockLoc.getBlockY() + " z" + blockLoc.getBlockZ());

            }

            event.setCancelled(true);

        }
    }
}
