package com.darkblue97.curriculummonolith.rest;

import com.darkblue97.curriculummonolith.domain.UuidDTO;
import com.darkblue97.curriculummonolith.domain.dto.LanguagesDTO;
import com.darkblue97.curriculummonolith.exceptions.DataAlreadySavedException;
import com.darkblue97.curriculummonolith.exceptions.NotFoundException;
import com.darkblue97.curriculummonolith.service.LanguageService;
import com.darkblue97.curriculummonolith.utils.GenerationUUID;
import com.darkblue97.curriculummonolith.utils.response.ResponseEntityBuilderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LanguagesController {

    private final LanguageService languageService;

    public LanguagesController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping(value = "/language")
    public ResponseEntity<Object> getAllLanguage() {
        try {
            List<LanguagesDTO> languagesDTO = languageService.getAll(null);
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.OK)
                    .setObjectResponse(languagesDTO)
                    .setMessage("Language")
                    .build();
        } catch (NotFoundException nte) {
            return new ResponseEntityBuilderResponse<>()
                    .setError(nte.toString())
                    .setStatus(HttpStatus.NOT_FOUND)
                    .setObjectResponse(nte.getLocalizedMessage())
                    .build();
        } catch (Exception ex) {
            return new ResponseEntityBuilderResponse<>()
                    .setError("Internal server error")
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PutMapping(value = "/language")
    public ResponseEntity<Object> putLanguage(@RequestBody LanguagesDTO languagesDTO) {
        try {
            languageService.putObject(languagesDTO);
            return new ResponseEntityBuilderResponse<>()
                    .setMessage("Information successfully saved")
                    .setStatus(HttpStatus.OK)
                    .build();
        } catch (DataAlreadySavedException e) {
            return new ResponseEntityBuilderResponse<>()
                    .setError("Exception saving the desired language")
                    .setObjectResponse(e.getLocalizedMessage())
                    .setStatus(HttpStatus.NOT_ACCEPTABLE)
                    .build();
        } catch (Exception e) {
            return new ResponseEntityBuilderResponse<>()
                    .setError("Internal server error while saving")
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping(value = "/language/{language}")
    public ResponseEntity<Object> postLanguage(@RequestBody LanguagesDTO languagesDTO) {
        try {
            languageService.postObject(languagesDTO);
            return new ResponseEntityBuilderResponse<>()
                    .setMessage("Information successfully saved")
                    .setStatus(HttpStatus.OK)
                    .build();
        } catch (NotFoundException e) {
            return new ResponseEntityBuilderResponse<>()
                    .setError("Exception updating the about me")
                    .setObjectResponse(e.getLocalizedMessage())
                    .setStatus(HttpStatus.NOT_ACCEPTABLE)
                    .build();
        } catch (Exception e) {
            return new ResponseEntityBuilderResponse<>()
                    .setError("Internal server error while updating")
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @DeleteMapping(value = "/language")
    public ResponseEntity<Object> deleteLanguage(@RequestBody String uid) {
        try {
            UuidDTO uuidDTO = new UuidDTO(uid);

            if (GenerationUUID.isUUIDValid(uuidDTO.getId())) {
                languageService.deleteObject(GenerationUUID.returnUUIDFrmString(uuidDTO.getId()));
            } else {
                return new ResponseEntityBuilderResponse<>()
                        .setStatus(HttpStatus.OK)
                        .setMessage("UUID provided does not exist")
                        .build();
            }

            return new ResponseEntityBuilderResponse<>()
                    .setMessage("Information successfully deleted")
                    .setStatus(HttpStatus.OK)
                    .build();
        } catch (NotFoundException e) {
            return new ResponseEntityBuilderResponse<>()
                    .setError("Exception deleting the about me")
                    .setObjectResponse(e.getLocalizedMessage())
                    .setStatus(HttpStatus.NOT_ACCEPTABLE)
                    .build();
        } catch (Exception e) {
            return new ResponseEntityBuilderResponse<>()
                    .setError("Internal server error while saving")
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
