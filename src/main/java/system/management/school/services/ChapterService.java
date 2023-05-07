package system.management.school.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import system.management.school.models.Chapter;
import system.management.school.repository.ChapterRepository;

import java.util.List;
import java.util.Optional;

@Service
@EnableCaching
public class ChapterService {

    private final ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public void save(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    @Cacheable("chapter")
    public Optional<Chapter> findByChapterNumber(String chapterNumber) {
        doLongRunningTask();
        return chapterRepository.findByChapterNumber(chapterNumber);
    }

    @CacheEvict(value = "chapter", key = "#chapter.id")
    public Optional<Chapter> updateByChapterNumber(String chapterNumber, String content) throws Exception {
        Optional<Chapter> chapter = chapterRepository.findByChapterNumber(chapterNumber);
        if (chapter.isPresent()) {
            chapter.get().setChapterContent(content);
            chapterRepository.save(chapter.get());
            return chapter;
        } else
            throw new Exception("Invalid chapter number");

    }

    public boolean existsByChapterNumber(String chapterNumber) {
        return chapterRepository.existsChapterByChapterNumber(chapterNumber);
    }

    @Cacheable("chapters")
    public List<Chapter> findAll() {
        doLongRunningTask();
        return chapterRepository.findAll();
    }

    private void doLongRunningTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
