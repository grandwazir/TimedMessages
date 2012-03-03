/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ReloadCommand.java is part of TimedMessages.
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

package name.richardson.james.bukkit.timedmessages.management;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.timedmessages.TimedMessages;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;
import name.richardson.james.bukkit.utilities.command.PluginCommand;

@ConsoleCommand
public class StatusCommand extends PluginCommand {
  
  private final TimedMessages plugin;

  public StatusCommand(TimedMessages plugin) {
    super(plugin);
    this.plugin = plugin;
    this.registerPermissions();
  }

  private void registerPermissions() {
    final String prefix = this.plugin.getDescription().getName().toLowerCase() + ".";
    // create the base permission
    final Permission base = new Permission(prefix + this.getName(), this.plugin.getMessage("statuscommand-permission-description"), PermissionDefault.OP);
    base.addParent(this.plugin.getRootPermission(), true);
    this.addPermission(base);
  }

  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, name.richardson.james.bukkit.utilities.command.CommandUsageException {
    
    if (plugin.isTimersStarted()) {
      sender.sendMessage(ChatColor.GREEN + this.getFormattedTimerStatusMessage());
    } else {
      sender.sendMessage(ChatColor.YELLOW + this.getMessage("no-timers-running"));
    }
      
  }
  
  public String getFormattedTimerStatusMessage() {
    Object[] arguments = {this.plugin.getTimerCount()};
    double[] limits = {0, 1, 2};
    String[] formats = {this.getMessage("no-timers"), this.getMessage("one-timer"), this.getMessage("many-timers")};
    return this.getChoiceFormattedMessage("timers-running", arguments, formats, limits);
  }

  public void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException {
    // TODO Auto-generated method stub
    
  }



}
