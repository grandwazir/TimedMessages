package name.richardson.james.bukkit.timedmessages.random;

import java.util.List;
import java.util.Random;

import org.bukkit.Server;

import name.richardson.james.bukkit.timedmessages.Message;


public class RandomMessage extends Message {

  public RandomMessage(Server server, Long milliseconds, List<String> messages, String permission) {
    super(server, milliseconds, messages, permission);
  }

  @Override
  protected String getNextMessage() {
    final int i = new Random().nextInt(messages.size());
    return messages.get(i);
  }
  
}
