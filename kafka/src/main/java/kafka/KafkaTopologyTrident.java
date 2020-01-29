package kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.spout.Func;
import org.apache.storm.kafka.spout.trident.KafkaTridentSpoutConfig;
import org.apache.storm.kafka.spout.trident.KafkaTridentSpoutOpaque;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.Consumer;
import org.apache.storm.trident.operation.MapFunction;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.List;

import static org.apache.storm.kafka.spout.FirstPollOffsetStrategy.UNCOMMITTED_EARLIEST;

public class KafkaTopologyTrident {

    private static final String KAFKA_TOPIC = "test";
    private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";

    public static void main(String[] args) throws Exception {
        TridentTopology tridentTopology = new TridentTopology();

        KafkaTridentSpoutOpaque<String, String> spout = new KafkaTridentSpoutOpaque<>(KafkaTridentSpoutConfig.builder(BOOTSTRAP_SERVER, KAFKA_TOPIC)
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_TOPIC)
                .setRecordTranslator((Func<ConsumerRecord<String, String>, List<Object>>) record ->
                        new Values(record.value()), new Fields("str"))
                .setFirstPollOffsetStrategy(UNCOMMITTED_EARLIEST)
                .build());

        tridentTopology.newStream("spout", spout)
                .map((MapFunction) input -> {
                    Long integer = Long.valueOf(input.getString(0));
                    return new Values(integer * integer);
                }, new Fields("square"))
                .filter(new BaseFilter() {
                    @Override
                    public boolean isKeep(TridentTuple tuple) {
                        return tuple.getLong(0) > 1;
                    }
                })
                .peek((Consumer) input -> System.out.println(input));


        try (LocalCluster cluster = new LocalCluster()) {
            Config config = new Config();
            config.setDebug(false);
            cluster.submitTopology("TridentSample", config, tridentTopology.build());
            while (true) {
            }
        }
    }
}
