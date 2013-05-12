package name.richardson.james.bukkit.timedmessages.timer;

import java.util.List;

import org.bukkit.entity.Player;

public interface Timer extends Runnable {

  public List<String> getMessages();

  public String getPermission();
  
  public List<String> getRegions();
  
  public List<String> getWorldNames();
  
  public Long getTicks();
  
  public String getNextMessage();
  
  public boolean isPlayerInRegion(Player region);
  
  public boolean isPlayerValidTarget(Player player);
  
  public String toString();
  
}
