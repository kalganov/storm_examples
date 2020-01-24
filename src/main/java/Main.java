import bolt.PrintingBolt;
import bolt.SquareBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import spout.RandomNumberSpout;

public class Main {

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        Config config = new Config();
        config.setDebug(false);

        builder.setSpout("random-spout", new RandomNumberSpout());

        builder.setBolt("square-bolt", new SquareBolt()).shuffleGrouping("random-spout");

        builder.setBolt("printer-bolt", new PrintingBolt()).shuffleGrouping("square-bolt");
        builder.setBolt("printer-bolt1", new PrintingBolt()).noneGrouping("random-spout");

        try (LocalCluster cluster = new LocalCluster()) {
            cluster.submitTopology("LogAnalyserStorm", config, builder.createTopology());
            Thread.sleep(10000);
        }
    }
}
