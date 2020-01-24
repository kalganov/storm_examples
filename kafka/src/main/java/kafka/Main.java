package kafka;

import kafka.bolt.PrintBolt;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

import static org.apache.storm.kafka.spout.FirstPollOffsetStrategy.UNCOMMITTED_EARLIEST;

public class Main {

    private static final String KAFKA_TOPIC = "test";
    private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.setDebug(false);
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        TopologyBuilder builder = new TopologyBuilder();

        KafkaSpout<String, String> spout = new KafkaSpout<>(KafkaSpoutConfig.builder(BOOTSTRAP_SERVER, KAFKA_TOPIC)
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_TOPIC)
                .setFirstPollOffsetStrategy(UNCOMMITTED_EARLIEST)
                .build());
        builder.setSpout("kafka-simple", spout);
        builder.setBolt("print-word", new PrintBolt()).shuffleGrouping("kafka-simple");

        try (LocalCluster cluster = new LocalCluster()) {
            cluster.submitTopology("KafkaStormSample", config, builder.createTopology());
            while (true) {
            }
        }
    }
}
