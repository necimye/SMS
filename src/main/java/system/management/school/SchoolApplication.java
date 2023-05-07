package system.management.school;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import system.management.school.models.Chapter;

@SpringBootApplication
@EnableScheduling
public class SchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }

    //    From here we are publishing the messages . Then, we can see the kafka listener listening this message
//    It is for testing purpose only. When the application is run for the first time Kafka producer will send this
//    data to kafka consumer.
//    I've implemented sending data to kafka consumer in the ResourceController.java file through api request
    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, Chapter> kafkaTemplate) {
        return args -> {
            kafkaTemplate.send("chapters", new Chapter("1",
                    "Introduction to Spring Boot",
                    "Spring boot is a high performance java library"));
        };
    }
}
