package system.management.school.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "chapters")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Chapter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String chapterNumber;

    @NotBlank
    @Size(max = 80)
    private String chapterName;

    @Size(max = 1000)
    private String chapterContent;

    public Chapter(String chapterNumber, String chapterName, String chapterContent) {
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.chapterContent = chapterContent;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", chapterNumber='" + chapterNumber + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", chapterContent='" + chapterContent + '\'' +
                '}';
    }
}
