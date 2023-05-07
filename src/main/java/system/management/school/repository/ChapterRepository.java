package system.management.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.management.school.models.Chapter;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    Optional<Chapter> findByChapterNumber(String chapterNumber);

    Boolean existsChapterByChapterNumber(String chapterNumber);

}
