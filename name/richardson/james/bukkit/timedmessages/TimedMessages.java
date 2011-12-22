
package name.richardson.james.bukkit.timedmessages;

import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.timedmessages.management.ReloadCommand;
import name.richardson.james.bukkit.timedmessages.management.StartCommand;
import name.richardson.james.bukkit.timedmessages.management.StopCommand;
import name.richardson.james.bukkit.timedmessages.random.RandomMessage;
import name.richardson.james.bukkit.timedmessages.rotation.RotatingMessage;
import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.Time;
import name.richardson.james.bukkit.util.command.CommandManager;

public class TimedMessages extends Plugin {

  public static final long START_DELAY = 30;

  private TimedMessagesConfiguration configuration;
  private List<ConfigurationSection> messages;
  private CommandManager commandManager;
  private boolean timersStarted = false;

  @Override
  public void onDisable() {
    this.stopTimers();
    logger.info(String.format("%s is disabled.", this.getDescription().getName()));
  }

  @Override
  public void onEnable() {
    logger.setPrefix("[TimedMessages] ");

    try {
      this.loadConfiguration();
      this.loadMessagesConfiguration();
      this.setPermission();
      this.startTimers(START_DELAY);
      this.registerCommands();
      logger.info(String.format("%d timers started.", messages.size()));
    } catch (IOException exception) {
      logger.severe("Unable to load configuration!");
    }

    logger.info(String.format("%s is enabled.", this.getDescription().getFullName()));
  }

  private void registerCommands() {
    commandManager = new CommandManager(this.getDescription());
    this.getCommand("tm").setExecutor(commandManager);
    commandManager.registerCommand("reload", new ReloadCommand(this));
    commandManager.registerCommand("start", new StartCommand(this));
    commandManager.registerCommand("stop", new StopCommand(this));
  }

  public void startTimers(long startDelay) {
    this.timersStarted = true;
    startDelay = startDelay * 20;
    for (ConfigurationSection section : messages) {
      Long milliseconds = Time.parseTime(section.getString("delay", "5m"));
      List<String> messages = section.getStringList("messages");
      String permission = section.getString("permission");
      String mode = section.getString("mode", "rotation");
      Message task;
      if (mode.equalsIgnoreCase("rotation")) {
        task = new RotatingMessage(this.getServer(), milliseconds, messages, permission);
      } else {
        task = new RandomMessage(this.getServer(), milliseconds, messages, permission);
      }
      this.getServer().getScheduler().scheduleSyncRepeatingTask(this, task, startDelay, task.getTicks());
    }
  }
  
  public void stopTimers() {
    this.timersStarted = false;
    this.getServer().getScheduler().cancelTasks(this);
  }
  
  public boolean isTimersStarted() {
    return timersStarted;
  }

  public void loadMessagesConfiguration() throws IOException {
    final MessagesConfiguration configuration = new MessagesConfiguration(this);
    this.messages = configuration.getConfigurationSections();
  }

  private void loadConfiguration() throws IOException {
    this.configuration = new TimedMessagesConfiguration(this);
    if (this.configuration.getDebugging()) Logger.enableDebugging("timedmessages");
  }

}
