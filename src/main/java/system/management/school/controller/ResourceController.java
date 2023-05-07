package system.management.school.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.management.school.models.Chapter;
import system.management.school.payload.request.AddChapterRequest;
import system.management.school.payload.response.MessageResponse;
import system.management.school.services.ChapterService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/api/chapter"})
public class ResourceController {

    private final ChapterService chapterService;
    private final KafkaTemplate<String, Chapter> kafkaTemplate;

    public ResourceController(ChapterService chapterService, KafkaTemplate<String, Chapter> kafkaTemplate) {
        this.chapterService = chapterService;
        this.kafkaTemplate = kafkaTemplate;
    }


    //    both student and teacher can access the cover page
    @GetMapping("/cover")
    public ResponseEntity<?> getCover() {
        return ResponseEntity.ok(new MessageResponse("This is the cover page!"));
    }


    @PostMapping
    public ResponseEntity<?> uploadChapter(@Valid @RequestBody AddChapterRequest addChapterRequest) {

        if (chapterService.existsByChapterNumber(addChapterRequest.getChapterNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Chapter number already exists!"));
        }

        Chapter chapter = new Chapter(
                addChapterRequest.getChapterNumber(),
                addChapterRequest.getChapterName(),
                addChapterRequest.getChapterContent()
        );
        chapterService.save(chapter);
        return ResponseEntity.ok(new MessageResponse("Chapter added successfully!"));
    }


    @PutMapping("/{chapterNumber}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> updateChapter(@PathVariable String chapterNumber, @RequestBody String content) throws Exception {

        if (!chapterService.existsByChapterNumber(chapterNumber)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Chapter number does not exist!"));
        }

        chapterService.updateByChapterNumber(chapterNumber, content);
        return ResponseEntity.ok(new MessageResponse("Chapter updated successfully!"));
    }


    @GetMapping("/{chapterNumber}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<?> getChapterContent(@PathVariable String chapterNumber) {

        if (!chapterService.existsByChapterNumber(chapterNumber)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Chapter number does not exist!"));
        }

        return ResponseEntity.ok(chapterService.findByChapterNumber(chapterNumber));
    }


    @GetMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<?> getAllChapters() {
        return ResponseEntity.ok(chapterService.findAll());
    }


    /**
     * When we send the api request to this url, the kafka listener will listen to this message and print it
     * Currently there are two listeners in the project. One is in the KafkaListeners.java file and the other is run
     * from the command line
     */
    @PostMapping("/message")
    @PreAuthorize("hasRole('TEACHER')")
    public void sendMessage(@Valid @RequestBody AddChapterRequest chapterRequestMessage) {
        kafkaTemplate.send("chapters", new Chapter(
                chapterRequestMessage.getChapterNumber(),
                chapterRequestMessage.getChapterName(),
                chapterRequestMessage.getChapterContent()));
    }

}
