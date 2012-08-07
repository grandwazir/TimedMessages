/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * TimedMessages.java is part of TimedMessages.
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

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.timedmessages.management.ReloadCommand;
import name.richardson.james.bukkit.timedmessages.management.StartCommand;
import name.richardson.james.bukkit.timedmessages.management.StatusCommand;
import name.richardson.james.bukkit.timedmessages.management.StopCommand;
import name.richardson.james.bukkit.timedmessages.random.RandomMessage;
import name.richardson.james.bukkit.timedmessages.rotation.RotatingMessage;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.formatters.TimeFormatter;
import name.richardson.james.bukkit.utilities.plugin.AbstractPlugin;

public class TimedMessages extends AbstractPlugin {

  /* The default amount of time, in seconds, before the timers will start */
  public static final long START_DELAY = 5;

  /* A collection holding all active timers */
  private final Set<Message> timers = new HashSet<Message>();

  /* A list of messages for use in the timers */
  private List<ConfigurationSection> messages;

  /* Tracking boolean to check if the timers are active or not. */
  private boolean timersStarted = false;

  public String getArtifactID() {
    return "timed-messages";
  }

  public String getGroupID() {
    return "name.richardson.james.bukkit";
  }

  public int getTimerCount() {
    return this.timers.size();
  }
  
  public int getMessageCount() {
    int i = 0;
    for (Message message : timers) {
      i=+ message.getMessages().size();
    }
    return i;
  }

  public boolean isTimersStarted() {
    return this.timersStarted;
  }

  @Override
  protected void loadConfiguration() throws IOException {
    super.loadConfiguration();
    final MessagesConfiguration configuration = new MessagesConfiguration(this);
    this.messages = configuration.getConfigurationSections();
    this.startTimers(START_DELAY);
  }

  @Override
  public void onDisable() {
    this.stopTimers();
  }

  public void startTimers(long startDelay) {
    if (this.timersStarted) {
      this.stopTimers();
    }
    this.timersStarted = true;
    startDelay = startDelay * 20;
    for (final ConfigurationSection section : this.messages) {
      final Long milliseconds = TimeFormatter.parseTime(section.getString("delay", "5m"));
      final List<String> messages = section.getStringList("messages");
      final String permission = section.getString("permission");
      final String mode = section.getString("mode", "rotation");
      final String worldName = section.getString("world");
      Message task;
      if (mode.equalsIgnoreCase("rotation")) {
        task = new RotatingMessage(this.getServer(), milliseconds, messages, permission, worldName);
      } else {
        task = new RandomMessage(this.getServer(), milliseconds, messages, permission, worldName);
      }
      this.getServer().getScheduler().scheduleSyncRepeatingTask(this, task, startDelay, task.getTicks());
      this.timers.add(task);
    }
  }

  public void stopTimers() {
    this.timersStarted = false;
    this.timers.clear();
    this.getServer().getScheduler().cancelTasks(this);
  }

  public void reloadConfiguration() throws IOException {
    this.loadConfiguration();
    this.startTimers(START_DELAY);
  }
  
  protected void setupMetrics() throws IOException {
    new MetricsListener(this);
  }
  
  protected void registerCommands() {
    final CommandManager commandManager = new CommandManager(this);
    this.getCommand("tm").setExecutor(commandManager);
    commandManager.addCommand(new ReloadCommand(this));
    commandManager.addCommand(new StartCommand(this));
    commandManager.addCommand(new StatusCommand(this));
    commandManager.addCommand(new StopCommand(this));
  }

}
