package system.management.school.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {


    @KafkaListener(topics = "chapters", groupId = "group_id")
    void listener(String data) {
//        the listener accepts the data as always in the form of a string
        System.out.println("Received Message in group - group_id: " + data);
    }
}
