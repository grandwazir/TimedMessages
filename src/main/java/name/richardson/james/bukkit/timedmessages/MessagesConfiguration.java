/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * MessagesConfiguration.java is part of TimedMessages.
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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.utilities.configuration.AbstractConfiguration;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

public class MessagesConfiguration extends AbstractConfiguration {

  private List<ConfigurationSection> sections = new LinkedList<ConfigurationSection>();

  public MessagesConfiguration(SimplePlugin plugin) throws IOException {
    super(plugin, "messages.yml");
    addExamples();
    setConfigurationSections();
  }

  public List<ConfigurationSection> getConfigurationSections() {
    return Collections.unmodifiableList(sections);
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

}
