/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * RotatingMessage.java is part of TimedMessages.
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

package name.richardson.james.bukkit.timedmessages.timer;

import java.util.Iterator;
import java.util.List;

public class RotatingTimer extends AbstractTimer {

  private Iterator<String> iterator;

  public RotatingTimer(final Long time, final List<String> messages, final String permission, final List<String> worlds, List<String> regions) {
    super(time, messages, permission, worlds, regions);
    this.refreshIterator();
  }

  public String getNextMessage() {
    if (!this.iterator.hasNext()) {
      this.refreshIterator();
    }
    return this.iterator.next().toString();
  }

  private void refreshIterator() {
    this.iterator = this.getMessages().iterator();
  }



}
