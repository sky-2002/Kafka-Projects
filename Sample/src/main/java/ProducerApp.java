import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.Scanner;

public class ProducerApp {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Properties config = new Properties();

        // these are the three mandatory properties
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        // maybe we can try declaring it a Producer instead of KafkaProducer
        KafkaProducer<String, String> producer = new KafkaProducer<String,String>(config);

        // there are many constructors of this class, we are using this one
        ProducerRecord<String,String> record1 = new ProducerRecord<>("CustomerCountry","Precision Products","France");

        try{
            producer.send(record1); // to make this synchronous, just add a .get() after send(record)
            System.out.println("Message sent successfully");
        }catch (Exception e){ // this exception can be a SerializationException or a BufferExhaustedException
            e.printStackTrace();
        }
        producer.close();


//        KafkaProducer producer = new KafkaProducer<String,String>(config);
        // asynchronous send using callback
//        ProducerRecord<String,String> record2 = new ProducerRecord<>("CustomerCountry","Biomedical Materials","USA");
//        producer.send(record2, new DemoProducerCallback());
//        System.out.println("Message sent successfully");
//        producer.close();

//        ProducerRecord<String,String> rec_test = new ProducerRecord<String,String>("search","This is data 1 sent using rec_test");
//        producer.send(rec_test);
//        System.out.println("Data sent successfully");
//        while (true){
//            System.out.println("Enter data to send to kafka: ");
//            String key = scanner.nextLine();
//            ProducerRecord<String,String> rec = new ProducerRecord<String,String>("search",key);
//            producer.send(rec);
//        }
    }
    private static class DemoProducerCallback implements Callback{
        @Override
        public void onCompletion(RecordMetadata recordMetadata,Exception e){
            if(e != null){
                e.printStackTrace();
            }
        }
    }

    // suppose instead of just recording customer name, we make use of a class Customer
    public class Customer{
        private String customerName;
        private int customerID;

        // constructor
        public Customer(String customerName,int customerID){
            this.customerName = customerName;
            this.customerID = customerID;
        }

        public String getCustomerName() {
            return customerName;
        }

        public int getCustomerID() {
            return customerID;
        }
    }

    // Now we implement a serializer (a custom serializer) for this customer class
//    public class CustomerSerializer implements Serializer{
//        @Override
//        public void configure(Map configs, boolean isKey) {
//            // nothing to configure
//        }
//
//
//        /**
//         We are serializing Customer as:
//         4 byte int representing customerId
//         4 byte int representing length of customerName in UTF-8 bytes (0 if name is
//         Null)
//         N bytes representing customerName in UTF-8
//         */
//        @Override
//        public byte[] serialize(String topic, Customer data){
////            try {
//                byte[] serializedName;
//                int stringSize;
//                if (data == null)
////                    return null;
//                    return "".getBytes(StandardCharsets.UTF_8);
//                else {
//                    if (data.getCustomerName() != null) {
//                        serializedName = data.getCustomerName().getBytes("UTF-8");
//                        stringSize = serializedName.length;
//                    } else {
//                        serializedName = new byte[0];
//                        stringSize = 0;
//                    }
//                }
//                ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + stringSize);
//                buffer.putInt(data.getCustomerID());
//                buffer.putInt(stringSize);
//                buffer.put(serializedName);
//                return buffer.array();
//
////            } catch (Exception e) {
////                throw new SerializationException("Error when serializing Customer to byte[] " + e);
////                return "".getBytes(StandardCharsets.UTF_8);
////            }
//        }
//
//        @Override
//        public byte[] serialize(String topic, Headers headers, Object data) {
//            return Serializer.super.serialize(topic, headers, data);
//        }
//
//        @Override
//        public void close() {
//            // nothing to close
//        }
//
////        @Override
////        public byte[] serialize(){
////
////        }
//    }

}

