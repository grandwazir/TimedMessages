package name.richardson.james.bukkit.timedmessages;

import java.io.IOException;

import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.configuration.AbstractConfiguration;

public class TimedMessagesConfiguration extends AbstractConfiguration {
  
  public TimedMessagesConfiguration(Plugin plugin) throws IOException {
    super(plugin, "config.yml");
  }

}
