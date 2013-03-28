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

import java.util.ArrayList;
import java.util.List;

import name.richardson.james.bukkit.timedmessages.TimedMessages;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;
import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@ConsoleCommand
public class StatusCommand extends AbstractCommand {

  private final TimedMessages plugin;
  
  private final ChoiceFormatter formatter;

  public StatusCommand(final TimedMessages plugin) {
    super(plugin);
    this.plugin = plugin;
    this.formatter = new ChoiceFormatter(this.getLocalisation());
    this.formatter.setLimits(0, 1, 2);
    this.formatter.setMessage(this, "timers-running");
    this.formatter.setFormats(
      this.getLocalisation().getMessage(TimedMessages.class, "no-timers"), 
      this.getLocalisation().getMessage(TimedMessages.class, "one-timer"), 
      this.getLocalisation().getMessage(TimedMessages.class, "many-timers")
    );
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    if (this.plugin.isTimersStarted()) {
      this.formatter.setArguments(this.plugin.getTimerCount());
      sender.sendMessage(this.formatter.getMessage());
    } else {
      sender.sendMessage(this.getLocalisation().getMessage(this, "no-timers-running"));
    }
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    return;
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] arguments) {
    return new ArrayList<String>();
  }

}
