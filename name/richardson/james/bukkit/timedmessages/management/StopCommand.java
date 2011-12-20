package name.richardson.james.bukkit.timedmessages.management;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.timedmessages.TimedMessages;
import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.command.CommandPermissionException;
import name.richardson.james.bukkit.util.command.CommandUsageException;
import name.richardson.james.bukkit.util.command.PlayerCommand;

public class StopCommand extends PlayerCommand {

  public static final String NAME = "stop";
  public static final String DESCRIPTION = "Stop all timed messages.";
  public static final String PERMISSION_DESCRIPTION = "Allow users to stop all timed messages.";
  public static final String USAGE = "";
  
  public static final Permission PERMISSION = new Permission("timedmessage.stop", PERMISSION_DESCRIPTION, PermissionDefault.OP);
  
  public StopCommand(Plugin plugin) {
    super(plugin, NAME, DESCRIPTION, USAGE, PERMISSION_DESCRIPTION, PERMISSION);
  }

  @Override
  public void execute(CommandSender sender, Map<String, Object> arguments) throws CommandPermissionException, CommandUsageException {
    if (!sender.hasPermission(this.getPermission())) throw new CommandPermissionException("You do not have permission to do that", this.getPermission());
    final TimedMessages plugin = (TimedMessages) this.plugin;
    if (!plugin.isTimersStarted()) {
      throw new CommandUsageException("Timers have not been started!", this.getUsage());
    } else {
      plugin.stopTimers();
      sender.sendMessage(ChatColor.GREEN + "All timers have been stopped.");
    }
  }

}
