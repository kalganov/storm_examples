package kafka.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

public class RandomNumberKafkaSpout extends BaseRichSpout {

    private Random random;
    private SpoutOutputCollector outputCollector;

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.outputCollector = collector;
        random = new Random();
    }

    @Override
    public void nextTuple() {
        outputCollector.emit(new Values("key", String.valueOf(random.nextInt(6))));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("key", "message"));
    }
}
