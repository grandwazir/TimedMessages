package name.richardson.james.bukkit.timedmessages.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.timedmessages.TimedMessages;
import name.richardson.james.bukkit.util.command.CommandArgumentException;
import name.richardson.james.bukkit.util.command.CommandPermissionException;
import name.richardson.james.bukkit.util.command.CommandUsageException;
import name.richardson.james.bukkit.util.command.PlayerCommand;


public class StartCommand extends PlayerCommand {

  public static final String NAME = "start";
  public static final String DESCRIPTION = "Start all timed messages.";
  public static final String PERMISSION_DESCRIPTION = "Allow users to start all timed messages.";
  public static final String USAGE = "[inital_delay]";
  
  public static final Permission PERMISSION = new Permission("timedmessage.start", PERMISSION_DESCRIPTION, PermissionDefault.OP);
  
  private final TimedMessages plugin;
  
  public StartCommand(TimedMessages plugin) {
    super(plugin, NAME, DESCRIPTION, USAGE, PERMISSION_DESCRIPTION, PERMISSION);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, Map<String, Object> arguments) throws CommandPermissionException, CommandUsageException {
    if (plugin.isTimersStarted()) {
      throw new CommandUsageException("Timers have already been started!");
    } else {
      final int delay = (Integer) arguments.get(0);
      plugin.startTimers(delay);
      sender.sendMessage(String.format(ChatColor.GREEN + "Timers started with an inital %d second delay.", TimedMessages.START_DELAY / 20));
    }
  }
  
  @Override
  public Map<String, Object> parseArguments(final List<String> arguments) throws CommandArgumentException {
    HashMap<String, Object> map = new HashMap<String, Object>();
    if (arguments.isEmpty()) {
      map.put("delay", TimedMessages.START_DELAY);
    } else {
      try {
        map.put("delay", Integer.parseInt(arguments.get(0)));
      } catch (NumberFormatException exception) {
        throw new CommandArgumentException("You must specify a valid number!", "The time should be in seconds");
      }
    }
    return map;
  }
  

}
