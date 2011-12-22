package name.richardson.james.bukkit.timedmessages.management;

import java.io.IOException;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.timedmessages.TimedMessages;
import name.richardson.james.bukkit.util.command.CommandUsageException;
import name.richardson.james.bukkit.util.command.PlayerCommand;


public class ReloadCommand extends PlayerCommand {

  public static final String NAME = "reload";
  public static final String DESCRIPTION = "Reload messages from disk.";
  public static final String PERMISSION_DESCRIPTION = "Allow users to reload the messages from disk.";
  public static final String USAGE = "";
  
  public static final Permission PERMISSION = new Permission("timedmessage.reload", PERMISSION_DESCRIPTION, PermissionDefault.OP);
  
  private final TimedMessages plugin;
  
  public ReloadCommand(TimedMessages plugin) {
    super(plugin, NAME, DESCRIPTION, USAGE, PERMISSION_DESCRIPTION, PERMISSION);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, Map<String, Object> arguments) throws CommandUsageException {
    try {
      plugin.loadMessagesConfiguration();
    } catch (IOException exception) {
      throw new CommandUsageException("Unable to reload configuration!");
    }
    sender.sendMessage(ChatColor.GREEN + "Messages have been reloaded.");
  }

}
