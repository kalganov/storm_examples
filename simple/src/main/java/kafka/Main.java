package kafka;

import kafka.bolt.PrintingBolt;
import kafka.bolt.SquareBolt;
import kafka.spout.RandomNumberSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class Main {

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        Config config = new Config();
        config.setDebug(false);

        builder.setSpout("random-simple.spout", new RandomNumberSpout());

        builder.setBolt("square-simple.bolt", new SquareBolt()).shuffleGrouping("random-simple.spout");

        builder.setBolt("printer-simple.bolt", new PrintingBolt()).shuffleGrouping("square-simple.bolt");
        builder.setBolt("printer-bolt1", new PrintingBolt()).noneGrouping("random-simple.spout");

        try (LocalCluster cluster = new LocalCluster()) {
            cluster.submitTopology("LogAnalyserStorm", config, builder.createTopology());
            Thread.sleep(10000);
        }
    }
}
