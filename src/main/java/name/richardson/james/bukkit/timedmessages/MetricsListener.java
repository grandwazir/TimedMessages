package name.richardson.james.bukkit.timedmessages;

import java.io.IOException;

import name.richardson.james.bukkit.utilities.metrics.Metrics;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Graph;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Plotter;

import org.bukkit.plugin.Plugin;

public class MetricsListener {

  private final TimedMessages plugin;
  
  private Metrics metrics;

  public MetricsListener(TimedMessages plugin) throws IOException {
    this.plugin = plugin;
    this.metrics = new Metrics((Plugin) plugin);
    this.createUsageStatistics();
    this.metrics.start();
  }

  private void createUsageStatistics() {
    Graph graph = this.metrics.createGraph("Usage Statistics");
    graph.addPlotter(new Plotter("Total timers configured") {
      @Override
      public int getValue() {
        return plugin.getTimerCount();
      }
    });
    graph.addPlotter(new Plotter("Total messages configured") {
      @Override
      public int getValue() {
        return plugin.getMessageCount();
      }
    });
  }
  
  
  
}
