package com.darkblue97.curriculummonolith.domain;

import com.darkblue97.curriculummonolith.utils.LanguageEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(value = "aboutMe")
public class AboutMe {
    @Id
    private UUID id;
    private String title;
    private String text;
    private String mediaId;
    private LanguageEnum languageCode;

    public AboutMe(UUID id, String title, String text, String mediaId, LanguageEnum languageCode) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.mediaId = mediaId;
        this.languageCode = languageCode;
    }
}
