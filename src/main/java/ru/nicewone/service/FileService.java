package ru.nicewone.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.nicewone.model.DataType;

@Service
@Log4j2
public class FileService {

    public Void doTheJob(List<DataType> dataTypes) throws IOException {
        Resource resource = new ClassPathResource("files-in");
        Path pathFolder = Paths.get(resource.getFile().getAbsolutePath());
        List<Path> pathToFileList = Files.walk(pathFolder).filter(Files::isRegularFile).toList();
        for (Path pathToFile : pathToFileList) {
            log.info("Current File is : " + pathToFile.toString());
            Stream<String> lines = Files.lines(pathToFile);
            log.info("Lines in File are : " + lines.toList().toString());
        }

        return null;
    }
}
