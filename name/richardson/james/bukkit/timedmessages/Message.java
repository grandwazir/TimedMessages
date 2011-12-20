
package name.richardson.james.bukkit.timedmessages;

import java.util.List;

import org.bukkit.Server;

import name.richardson.james.bukkit.util.Colour;
import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Time;

public abstract class Message implements Runnable {

  protected final Logger logger = new Logger(this.getClass());
  protected final List<String> messages;

  private final Long ticks;
  private final String permission;
  private final Server server;

  public Message(Server server, Long milliseconds, List<String> messages, String permission) {
    final long seconds = milliseconds / 1000;
    this.ticks = seconds * 20;
    this.messages = messages;
    this.permission = permission;
    this.server = server;
    logger.debug(String.format("Creating new message broadcasting every %s (%d ticks)", Time.millisToLongDHMS(milliseconds), ticks));
  }

  @Override
  public void run() {
    String message = this.getNextMessage();
    message = Colour.replace("&", message);
    String[] parts = message.split("`");
    if (permission == null) {
      for (String part : parts) {
        logger.debug("Broadcasting message: " + part);
        server.broadcast(part, Server.BROADCAST_CHANNEL_USERS);
      }
    } else {
      for (String part : parts) {
        logger.debug(String.format("Broadcasting message to users with %s : %s", permission, part));
        server.broadcast(part, permission);
      }
    }
  }

  protected abstract String getNextMessage();

  public Long getTicks() {
    return ticks;
  }

  public List<String> getMessages() {
    return messages;
  }

  public String getPermission() {
    return permission;
  }

}
