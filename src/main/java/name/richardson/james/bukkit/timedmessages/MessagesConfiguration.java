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

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;
import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class MessagesConfiguration extends YAMLStorage {

  private final List<ConfigurationSection> sections = new LinkedList<ConfigurationSection>();

  public MessagesConfiguration(final SkeletonPlugin plugin) throws IOException {
    super(plugin, "messages.yml");
    this.addExamples();
    this.setConfigurationSections();
  }

  public List<ConfigurationSection> getConfigurationSections() {
    return Collections.unmodifiableList(this.sections);
  }

  private void addExamples() throws IOException {
    if (!this.configuration.isConfigurationSection("messages")) {
      this.logger.debug("Creating examples.");
      this.configuration.createSection("messages");
      this.configuration.createSection("messages.example");
      final ConfigurationSection section = this.configuration.getConfigurationSection("messages.example");
      section.set("mode", "rotation");
      section.set("delay", "1m");
      section.set("permission", "group.default");
      section.set("messages", Arrays.asList("&REDWelcome to our server", "&REDWe hope you enjoy your stay."));
    }
    this.save();
  }

  private void setConfigurationSections() {
    for (final String key : this.configuration.getConfigurationSection("messages").getKeys(false)) {
      this.sections.add(this.configuration.getConfigurationSection("messages." + key));
    }
  }

}
