package name.richardson.james.bukkit.timedmessages.rotation;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Server;

import name.richardson.james.bukkit.timedmessages.Message;


public class RotatingMessage extends Message {

  private Iterator<String> iterator;

  public RotatingMessage(Server server, Long time, List<String> messages, String permission) {
    super(server, time, messages, permission);
    refreshIterator();
  }

  @Override
  protected String getNextMessage() {
    if (!iterator.hasNext())refreshIterator();
    return iterator.next().toString();
  }
  
  private void refreshIterator() {
    iterator = messages.iterator();
  }

  
}
