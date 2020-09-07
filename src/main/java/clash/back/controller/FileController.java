package clash.back.controller;

import clash.back.domain.dto.FileDto;
import clash.back.exception.ChallengeNotFoundException;
import clash.back.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/file/")
public class FileController {
    private final IStorageService storageService;

    @Autowired
    public FileController(IStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/download/answer/{filename:.+}")
    public ResponseEntity<Resource> serveAnswer(@PathVariable String filename, HttpServletRequest request) throws FileNotFoundException {
        Resource resource = storageService.loadAnswerAsResource(filename);
        return serve(resource, request);
    }

    @GetMapping("/download/question/{category}/{filename:.+}")
    public ResponseEntity<Resource> serveQuestion(@PathVariable String category, @PathVariable String filename, HttpServletRequest request) throws FileNotFoundException {
        Resource resource = storageService.loadQuestionAsResource(category, filename);
        return serve(resource, request);
    }

    @GetMapping("/download/answer/{category}/{challengeId}")
    public ResponseEntity<Resource> serveAnswer(@PathVariable String category, @PathVariable String challengeId, HttpServletRequest request) throws ChallengeNotFoundException, FileNotFoundException {
        Resource resource = storageService.loadAnswerAsResource(category, challengeId);
        return serve(resource, request);
    }

    @PostMapping(value = "/upload/answer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileDto storeAnswer(@RequestParam MultipartFile file) {
        return FileDto.builder().fileName(storageService.storeAnswer(file)).build();
    }


    private ResponseEntity<Resource> serve(Resource resource, HttpServletRequest request) {
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException("error in controller");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
