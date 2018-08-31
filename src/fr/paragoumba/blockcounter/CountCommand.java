package fr.paragoumba.blockcounter;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Colorable;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Paragoumba on 05/05/2018.
 */

public class CountCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player){

            Player player = (Player) commandSender;

            if (PlayerInteractEventListener.hashMap.get(player) == null) PlayerInteractEventListener.hashMap.put(player, new SimpleEntry<>(null, false));

            Entry<Entry<Location, Location>, Boolean> firstEntry = PlayerInteractEventListener.hashMap.get(player);
            Entry<Location, Location> secondEntry = firstEntry.getKey();

            if (strings.length > 0) {

                if (strings[0].equalsIgnoreCase("toggleselect") || strings[0].equalsIgnoreCase("ts")){

                    boolean selecting = !firstEntry.getValue();

                    firstEntry.setValue(selecting);

                    if (selecting){

                        player.sendMessage(ChatColor.GREEN + "Entering selection mode.");

                    } else {

                        player.sendMessage(ChatColor.GREEN + "Exiting selection mode.");

                    }
                }
                else commandSender.sendMessage(ChatColor.RED + "Wrong argument.");

            } else if (secondEntry != null){

                Location firstLoc = secondEntry.getKey();
                Location secondLoc = secondEntry.getValue();

                if (firstLoc != null && secondLoc != null && firstLoc.getWorld() == secondLoc.getWorld()) {

                    SortedMap<String, Integer> blocks = new TreeMap<>();
                    int firstX = firstLoc.getBlockX();
                    int firstY = firstLoc.getBlockY();
                    int firstZ = firstLoc.getBlockZ();
                    int secondX = secondLoc.getBlockX();
                    int secondY = secondLoc.getBlockY();
                    int secondZ = secondLoc.getBlockZ();

                    if (firstX > secondX){

                        int x = firstX;
                        firstX = secondX;
                        secondX = x;

                    }

                    if (firstY > secondY){

                        int y = firstY;
                        firstY = secondY;
                        secondY = y;

                    }

                    if (firstZ > secondZ){

                        int z = firstZ;
                        firstZ = secondZ;
                        secondZ = z;

                    }

                    for (int x = firstX; x < secondX + 1; ++x) {
                        for (int y = firstY; y < secondY + 1; ++y) {
                            for (int z = firstZ; z < secondZ + 1; ++z) {

                                Location loc = new Location(secondLoc.getWorld(), x, y, z);
                                Block block = loc.getBlock();
                                Material blockType = block.getType();

                                if (blockType != Material.AIR) {

                                    String materialName = blockType.name() + (block.getState() instanceof Colorable ? " (Color:" + ((Colorable) block).getColor().name() + ")" : "");
                                    Integer n = blocks.get(materialName);

                                    blocks.put(materialName, n == null ? 1 : n + 1);

                                }
                            }
                        }
                    }

                    StringBuilder builder = new StringBuilder();

                    builder.append("Block list :");

                    for (Entry<String, Integer> entry : blocks.entrySet()) builder.append("\n").append(entry.getValue()).append(" ").append(entry.getKey());

                    player.sendMessage(builder.toString());

                } else player.sendMessage(ChatColor.RED + "Select a zone before.");

            }  else player.sendMessage(ChatColor.RED + "Select a zone before.");

        }

        return true;

    }
}
