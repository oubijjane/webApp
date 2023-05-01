package com.personal.project.webApp.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    void store(MultipartFile file, String fileName);


    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
