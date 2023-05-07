package system.management.school.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddChapterRequest {

    @NotBlank
    private String chapterNumber;

    @NotBlank
    private String chapterName;

    @NotBlank
    private String chapterContent;
}
