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

  public List<String> getMessages() {
    return messages;
  }

  public String getPermission() {
    return permission;
  }

  public Long getTicks() {
    return ticks;
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

}
