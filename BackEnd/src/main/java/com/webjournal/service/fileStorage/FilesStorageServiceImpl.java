package com.webjournal.service.fileStorage;
/*
  @author emilia
  @project WebJournal
  @class FilesStorageServiceImpl
  @version 1.0.0
  @since 26.11.2022 - 3:00
*/

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FilesStorageServiceImpl implements IFilesStorageService {
    private Path root;

    public FilesStorageServiceImpl(@Value("${file.storage}") String path) {
        root = Paths.get(path);
    }

    public void setRoot(String root) {
        this.root = Paths.get(root);
    }

    @Override
    public void save(MultipartFile file, String path) throws IOException {
        Files.copy(file.getInputStream(), this.root.resolve(path));
    }

    @Override
    public void delete(String filePath) throws IOException {
        Files.deleteIfExists(this.root.resolve(filePath));
    }

    @Override
    public void move(String oldPath, String newPath) throws IOException {
        Files.move(this.root.resolve(oldPath), this.root.resolve(newPath), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public boolean exists(String path) {
        return Files.exists(this.root.resolve(path));
    }

    @Override
    public void saveOrReplace(MultipartFile file, String path) throws IOException {
        Files.copy(file.getInputStream(), this.root.resolve(path), StandardCopyOption.REPLACE_EXISTING);
    }
}