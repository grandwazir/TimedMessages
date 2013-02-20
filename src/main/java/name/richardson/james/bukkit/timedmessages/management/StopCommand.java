/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * StopCommand.java is part of TimedMessages.
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

import name.richardson.james.bukkit.timedmessages.TimedMessages;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;

import org.bukkit.command.CommandSender;

@ConsoleCommand
public class StopCommand extends AbstractCommand {

  private final TimedMessages plugin;

  public StopCommand(final TimedMessages plugin) {
    super(plugin, false);
    this.plugin = plugin;
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    if (this.plugin.isTimersStarted()) {
      this.plugin.stopTimers();
    }
    sender.sendMessage(this.getLocalisation().getMessage(this, "timers-stopped"));
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    return;
  }


}
