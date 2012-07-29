/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * StartCommand.java is part of TimedMessages.
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

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.timedmessages.TimedMessages;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;
import name.richardson.james.bukkit.utilities.command.PluginCommand;
import name.richardson.james.bukkit.utilities.formatters.TimeFormatter;

@ConsoleCommand
public class StartCommand extends PluginCommand {

  private final TimedMessages plugin;

  /** The delay (in seconds) before starting the messages */
  private long delay;

  public StartCommand(final TimedMessages plugin) {
    super(plugin);
    this.plugin = plugin;
    this.registerPermissions();
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    // stop the timers if necessary
    if (this.plugin.isTimersStarted()) {
      this.plugin.stopTimers();
    }

    this.plugin.startTimers(this.delay);
    sender.sendMessage(this.getFormattedTimerStartMessage(this.delay));
  }
  
  public String getFormattedTimerStartMessage(final long delay) {
    final Object[] arguments = { this.plugin.getTimerCount(), delay };
    final double[] limits = { 0, 1, 2 };
    final String[] formats = { this.getMessage("no-timers"), this.getMessage("one-timer"), this.getMessage("many-timers") };
    return this.getChoiceFormattedMessage("timers-started", arguments, formats, limits);
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {

    if (arguments.length >= 1) {
      try {
        this.delay = TimeFormatter.parseTime(arguments[0]);
      } catch (final NumberFormatException exception) {
        throw new CommandArgumentException(this.getMessage("invalid-time"), this.getMessage("time-format-help"));
      }
      // check it is sane
      this.delay = this.delay / 1000;
      if (this.delay < 1) {
        throw new CommandArgumentException(this.getMessage("invalid-time"), this.getMessage("time-format-help"));
      }
    } else {
      this.delay = TimedMessages.START_DELAY;
    }

  }

  private void registerPermissions() {
    final String prefix = this.plugin.getDescription().getName().toLowerCase() + ".";
    // create the base permission
    final Permission base = new Permission(prefix + this.getName(), this.getMessage("permission-description"), PermissionDefault.OP);
    base.addParent(this.plugin.getRootPermission(), true);
    this.addPermission(base);
  }

}
