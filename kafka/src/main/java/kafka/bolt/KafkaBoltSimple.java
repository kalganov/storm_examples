package kafka.bolt;

import kafka.spout.RandomNumberKafkaSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Properties;

public class KafkaBoltSimple {

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new RandomNumberKafkaSpout());

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaBolt bolt = new KafkaBolt<>()
                .withProducerProperties(props)
                .withTopicSelector(new DefaultTopicSelector("test"))
                .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper<>());
        builder.setBolt("forwardToKafka", bolt, 8).shuffleGrouping("spout");

        Config conf = new Config();

        try (LocalCluster cluster = new LocalCluster()) {
            cluster.submitTopology("KafkaStormSample", conf, builder.createTopology());
            while (true) {
            }
        }
    }
}
