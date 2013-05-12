/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * Message.java is part of TimedMessages.
 * 
 * TimedMessages is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * TimedMessages is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * TimedMessages. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.timedmessages.timer;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.timedmessages.TimedMessages;
import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.managers.RegionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractMessage.
 */
public abstract class AbstractTimer implements Timer {
  
  /** A list of messages. */
  protected final List<String> messages;

  /** The number of ticks before this timer will execute. */
  private final Long ticks;
  
  /** The permission required to receive this message */
  private final String permission;
  
  /** Reference to the Bukkit server. */
  private final Server server;

  /** Reference to the plugin PermissionManager. */
  private final PermissionManager permissionManager;

  /** The names of the worlds you must be in to receive messages from this timer. */
  private final List<String> worlds = new LinkedList<String>();
  
  /** The names of the regions you must be in to receive messages from this timer. */
  private final Set<String> regions = new HashSet<String>();

  /** Reference to the TimedMessages plugin. */
  private final TimedMessages plugin;

  /**
   * Instantiates a new abstract message.
   *
   * @param milliseconds the milliseconds
   * @param messages the messages
   * @param permission the permission
   * @param worlds the worlds
   * @param regions the regions
   */
  public AbstractTimer(final Long milliseconds, final List<String> messages, final String permission, final List<String> worlds, List<String> regions) {
    final long seconds = milliseconds / 1000;
    this.plugin = (TimedMessages) this.server.getPluginManager().getPlugin("TimedMessages");
    this.server = this.plugin.getServer();
    this.ticks = seconds * 20;
    this.messages = messages;
    this.permission = permission;
    this.permissionManager = plugin.getPermissionManager();
    this.worlds.addAll(worlds);
    this.regions.addAll(regions);
    this.plugin.getCustomLogger().debug(this, String.format("Creating %s which broadcasts every %s seconds", this.getClass().getSimpleName(), seconds));
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.timedmessages.message.Message#getMessages()
   */
  public List<String> getMessages() {
    return Collections.unmodifiableList(this.messages);
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.timedmessages.message.Message#getPermission()
   */
  public String getPermission() {
    return this.permission;
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.timedmessages.message.Message#getTicks()
   */
  public Long getTicks() {
    return this.ticks;
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  public void run() {
    this.plugin.getCustomLogger().debug(this, String.format("Running %s.", this.getClass().getSimpleName()));
    String message = this.getNextMessage();
    message = ColourFormatter.replace("&", message);
    final String[] parts = message.split("\n");
    final List<Player> players = new LinkedList<Player>();
 
    for (final Player player : this.server.getOnlinePlayers()) {
      if (this.isPlayerValidTarget(player)) players.add(player);
    }
  
    if (!players.isEmpty()) {
      this.plugin.getCustomLogger().debug(this, String.format("Sending message to following players: %s", players.toString()));
      for (final String part : parts) {
        for (final Player player : players) {
          player.sendMessage(part);
        }
      }
    }
  
  }
  
  /**
   * Checks if is player in region.
   *
   * @param player the player
   * @return true, if is player in region
   */
  public boolean isPlayerInRegion(Player player) {
    if (this.worlds.isEmpty()) return true;
    if (this.plugin.getGlobalRegionManager() == null) return true;
    if (this.regions.isEmpty()) return true;
    for (String worldName : this.worlds) {
      if (!player.getWorld().getName().equals(worldName)) continue;
      RegionManager manager = this.plugin.getRegionManager(worldName);
      final int x = (int) player.getLocation().getX();
      final int y = (int) player.getLocation().getY();
      final int z = (int) player.getLocation().getZ();
      for (String regionName : this.regions) {
        if (manager.getRegion(regionName).contains(x, y, z)) return true;
      }
    }
    return false;
  }
  
  public List<String> getRegions() {
    return Collections.unmodifiableList(this.getRegions());
  }

  public List<String> getWorldNames() {
    return Collections.unmodifiableList(this.getWorldNames());
  }

  public boolean isPlayerValidTarget(Player player) {
    if (!worlds.isEmpty() && !worlds.contains(player.getWorld().getName())) return false;    
    // if the player is not in the correct region ignore them
    if (!this.isPlayerInRegion(player)) return false;
    // ignore the player if they do not have the correct permission
    if (this.permission != null && !this.permissionManager.hasPlayerPermission(player, this.permission)) return false;
    return true;
  }

}
