package name.richardson.james.bukkit.timedmessages;

import java.io.IOException;

import name.richardson.james.bukkit.util.Plugin;

public class TimedMessages extends Plugin {

  private TimedMessagesConfiguration configuration;

  @Override
  public void onDisable() {
    logger.info(String.format("%s is disabled.", this.getDescription().getName()));
  }

  @Override
  public void onEnable() {
    logger.setPrefix("[TimedMessages] ");
    
    try {
      this.loadConfiguration();
    } catch (IOException exception) {
      logger.severe("Unable to load configuration!");
    }
    
    logger.info(String.format("%s is enabled.", this.getDescription().getFullName()));
  }

  private void loadConfiguration() throws IOException {
    this.configuration = new TimedMessagesConfiguration(this);
  }

}
