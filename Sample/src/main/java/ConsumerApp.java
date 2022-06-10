import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.*;
//import org.json.simple.JSONObject;

public class ConsumerApp {

    public static void main(String[] args){
        Properties config = new Properties();

        config.put("bootstrap.servers","127.0.0.1:9092");
        config.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id","CountryCounter");
        config.put(ConsumerConfig.CLIENT_ID_CONFIG,"sampleConsumer");

        System.out.println("Create config and set all properties done.");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(config);
        System.out.println("Consumer created");

        kafkaConsumer.subscribe(Collections.singletonList("CustomerCountry")); // CustomerCountry is topicName
        // we can use regex to specify topic names so that the consumer subscribes to all those topics.

        // poll() takes in a parameter called  timeout which decides how much time it will take to return,
        // without or without data. poll() returns a list of records.
        HashMap<String, Integer> custCountryMap = new HashMap<>();
        try{
            while (true){
                ConsumerRecords<String,String> records = kafkaConsumer.poll(100);
                for(ConsumerRecord<String,String> record : records){

                    System.out.printf("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n",
                    record.topic(), record.partition(), record.offset(),
                            record.key(), record.value());

                    int updatedCount = 1;
                    if (custCountryMap.containsValue(record.value())) {
                        updatedCount = custCountryMap.get(record.value()) + 1;
                    }
                    custCountryMap.put(record.value(), updatedCount);
//                    JSONObject json = new JSONObject(custCountryMap);
//                    System.out.println(json.toString(4));
                }
            }
        } finally {
            kafkaConsumer.close();
                }
            }

//        while (true){
//            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
//            for (ConsumerRecord<String,String> rec : records){
//                System.out.println(rec.value());
//                System.out.println("Printed value");
//                System.out.println("--------------------");
//            }
//        }
    }

