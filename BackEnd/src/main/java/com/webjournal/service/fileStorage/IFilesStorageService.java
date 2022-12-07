package com.webjournal.service.fileStorage;
/**
 * @author emilia
 * @version 1.0.0
 * @project WebJournal
 * @class FilesStorageService
 * @since 26/11/2022 - 2.59
 **/

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFilesStorageService {
    void save(MultipartFile file, String path) throws IOException;
    void delete(String filePath) throws IOException;
    void move(String oldPath, String newPath) throws IOException;
    boolean exists(String path);
    void saveOrReplace(MultipartFile file, String path) throws IOException;
}
