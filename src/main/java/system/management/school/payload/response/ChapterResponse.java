package system.management.school.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChapterResponse {

    private int chapterNumber;
    private String chapterName;
    private String content;
}
