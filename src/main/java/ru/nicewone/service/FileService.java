package ru.nicewone.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.nicewone.model.DataType;

@Service
@Log4j2
public class FileService {

    public Void doTheJob(List<DataType> dataTypes) throws IOException {
        Path inPath = Paths.get(new ClassPathResource("files-in").getFile().getAbsolutePath());
        List<Path> pathToFileList = Files.walk(inPath).filter(Files::isRegularFile).toList();

        for (Path pathToFile : pathToFileList) {
            String fileName = pathToFile.getFileName().toString();
            log.info("Current File is : " + fileName);

            Path outPath = Paths.get(new ClassPathResource("files-out").getFile().getAbsolutePath() + "/" + fileName);
            if (!Files.exists(outPath)) {
                Files.createFile(outPath);
            }

            for (String line : Files.lines(pathToFile).toList()) {
                doParse(line, outPath, dataTypes);
            }
        }

        return null;
    }

    private void doParse(String line, Path outPath, List<DataType> dataTypes) throws IOException {
        for (DataType dataType : dataTypes) {
            if (line.matches(dataType.eventRegExp())) {
                LocalDateTime now = LocalDateTime.now();
                Map<String, String> data = dataType.data();

                Files.writeString(outPath, "My string to save");
                System.out.println(line);
            }
        }
    }
}
