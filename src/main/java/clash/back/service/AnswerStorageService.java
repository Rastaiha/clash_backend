package clash.back.service;

import clash.back.configuration.StorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class AnswerStorageService implements IStorageService {
    protected final Path answersPath;

    @Autowired
    public AnswerStorageService(StorageConfig storageConfig) {
        this.answersPath = Paths.get(storageConfig.getAnswersPath());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(answersPath);
        } catch (IOException e) {
            throw new RuntimeException("could not initialize");
        }
    }

    @Override
    public String storeAnswer(MultipartFile file) {
        return store(file, answersPath);
    }

    @Override
    public Path loadAnswer(String fileName) {
        return load(fileName, answersPath);
    }

    @Override
    public Resource loadAnswerAsResource(String fileName) throws FileNotFoundException {
        return loadAsResource(fileName, answersPath);
    }

    private String store(MultipartFile file, Path path) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        filename = new Date().getTime() + "."
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        try {
            if (file.isEmpty())
                throw new IllegalArgumentException("failed to store empty file" + filename);
            if (filename.contains(".."))
                throw new IllegalArgumentException("relative path error" + filename);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, path.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                return filename;
            }

        } catch (IOException e) {
            throw new RuntimeException("store failed ");
        }
    }

    private Path load(String filename, Path path) {
        return path.resolve(filename);
    }

    private Resource loadAsResource(String filename, Path path) throws FileNotFoundException {
        try {
            Path file = load(filename, path);
            return new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("can't find file");
        }
    }
}
