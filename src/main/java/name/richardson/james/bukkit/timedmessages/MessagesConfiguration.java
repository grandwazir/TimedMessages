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


import name.richardson.james.bukkit.timedmessages.random.RandomMessage;
import name.richardson.james.bukkit.timedmessages.rotation.RotatingMessage;
import name.richardson.james.bukkit.utilities.formatters.TimeFormatter;
import name.richardson.james.bukkit.utilities.persistence.AbstractYAMLStorage;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public class MessagesConfiguration extends AbstractYAMLStorage {

  private final List<ConfigurationSection> sections = new LinkedList<ConfigurationSection>();
  private final TimedMessages plugin;
  
  public MessagesConfiguration(final TimedMessages plugin) throws IOException {
    super(plugin, "messages.yml");
    this.plugin = plugin;
    this.addExamples();
    this.setConfigurationSections();
  }
  
  public enum MessageTypes {
    ROTATION,
    RANDOM
  }

  public List<ConfigurationSection> getConfigurationSections() {
    return Collections.unmodifiableList(this.sections);
  }
  
  public List<Message> getMessages() {
    final List<Message> createdMessages = new LinkedList<Message>();
    for (ConfigurationSection storage : this.sections) {
      Message message;
      final Long milliseconds = TimeFormatter.parseTime(storage.getString("delay", "5m"));
      final List<String> messages = storage.getStringList("messages");
      final String permission = storage.getString("permission");
      final List<String> worlds = storage.getStringList("worlds");
      final List<String> regions = storage.getStringList("regions");
      switch (MessageTypes.valueOf(storage.getString("mode", "ROTATION").toUpperCase())) {
      case ROTATION:
        message = new RotatingMessage(plugin, plugin.getServer(), milliseconds, messages, permission, worlds, regions);
      default:
        message = new RandomMessage(plugin, plugin.getServer(), milliseconds, messages, permission, worlds, regions);
      }
      createdMessages.add(message);
    }
    return createdMessages;
  }

  private void addExamples() throws IOException {
    if (!this.getConfiguration().isConfigurationSection("messages")) {
      this.getConfiguration().createSection("messages");
      this.getConfiguration().createSection("messages.example");
      final ConfigurationSection section = this.getConfiguration().getConfigurationSection("messages.example");
      section.set("mode", "rotation");
      section.set("delay", "1m");
      section.set("permission", "group.default");
      section.set("messages", Arrays.asList("&REDWelcome to our server", "&REDWe hope you enjoy your stay."));
    }
    this.save();
  }

  private void setConfigurationSections() {
    for (final String key : this.getConfiguration().getConfigurationSection("messages").getKeys(false)) {
      this.sections.add(this.getConfiguration().getConfigurationSection("messages." + key));
    }
  }

}
