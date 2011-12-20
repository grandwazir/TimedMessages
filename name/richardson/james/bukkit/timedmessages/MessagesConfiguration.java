
package name.richardson.james.bukkit.timedmessages;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.configuration.AbstractConfiguration;

public class MessagesConfiguration extends AbstractConfiguration {

  private List<ConfigurationSection> sections = new LinkedList<ConfigurationSection>();
  
  public MessagesConfiguration(Plugin plugin) throws IOException {
    super(plugin, "messages.yml");
    addExamples();
    setConfigurationSections();
  }

  private void addExamples() throws IOException {
    if (!configuration.isConfigurationSection("messages")) {
      logger.debug("Creating examples.");
      configuration.createSection("messages");
      configuration.createSection("messages.example");
      final ConfigurationSection section = configuration.getConfigurationSection("messages.example");
      section.set("mode", "rotation");
      section.set("delay", "1m");
      section.set("permission", "group.default");
      section.set("messages", Arrays.asList("&REDWelcome to our server", "&REDWe hope you enjoy your stay."));
    }
    this.save();
  }

  private void setConfigurationSections() {
    for (String key : configuration.getConfigurationSection("messages").getKeys(false)) {
      sections.add(configuration.getConfigurationSection("messages." + key));
    }
  }

  public List<ConfigurationSection> getConfigurationSections() {
    return Collections.unmodifiableList(sections);
  }

}
