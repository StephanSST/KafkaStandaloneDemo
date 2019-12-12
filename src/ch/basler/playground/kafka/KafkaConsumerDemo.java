package ch.basler.playground.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerDemo {

  private static Logger LOG = LoggerFactory.getLogger(KafkaConsumerDemo.class);

  private static final String URL = "localhost:9092";
  private static final String TOPIC = "first_topic";
  private static final String CONSUMER_GROUP_NAME = "my-fourth_application";
  private static final String EARLIEST = "earliest";

  public static void main(String[] args) {
    Properties props = new Properties();
    props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, URL);
    props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_NAME);
    props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);

    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

    consumer.subscribe(Collections.singleton(TOPIC));

    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
    records.forEach(r -> {
      LOG.info(String.format("Key: %s, Value: %s, Partition: %s, Offset: %s", r.key(), r.value(), r.partition(), r.offset()));
    });

    consumer.close();

  }

}
