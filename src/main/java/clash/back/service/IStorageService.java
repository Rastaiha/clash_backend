package clash.back.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public interface IStorageService {
    void init();

    String storeAnswer(MultipartFile file);

    Path loadAnswer(String fileName);

    Resource loadAnswerAsResource(String fileName) throws FileNotFoundException;

    Resource loadQuestionAsResource(String category, String fileName) throws FileNotFoundException;
}
