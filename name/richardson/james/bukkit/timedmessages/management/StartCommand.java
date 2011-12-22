package name.richardson.james.bukkit.timedmessages.management;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.timedmessages.TimedMessages;
import name.richardson.james.bukkit.util.command.CommandPermissionException;
import name.richardson.james.bukkit.util.command.CommandUsageException;
import name.richardson.james.bukkit.util.command.PlayerCommand;


public class StartCommand extends PlayerCommand {

  public static final String NAME = "start";
  public static final String DESCRIPTION = "Start all timed messages.";
  public static final String PERMISSION_DESCRIPTION = "Allow users to start all timed messages.";
  public static final String USAGE = "";
  
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
      plugin.startTimers();
      sender.sendMessage(String.format(ChatColor.GREEN + "Timers started with an inital %d second delay.", TimedMessages.START_DELAY / 20));
    }
  }

}
