package name.richardson.james.bukkit.timedmessages;

import java.io.IOException;

import name.richardson.james.bukkit.utilities.metrics.AbstractMetricsListener;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Graph;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Plotter;

public class MetricsListener extends AbstractMetricsListener {

  private final TimedMessages plugin;

  public MetricsListener(TimedMessages plugin) throws IOException {
    super(plugin);
    this.plugin = plugin;
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
