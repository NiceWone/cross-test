package ru.nicewone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.nicewone.model.DataType;
import ru.nicewone.model.DataTypeOut;

@Service
@Log4j2
@AllArgsConstructor
public class FileService {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public List<Boolean> doTheJob(List<DataType> dataTypes) {
        Path inputDirectoryPath = Paths.get(new ClassPathResource("files-in").getFile().getAbsolutePath());

        return Files.walk(inputDirectoryPath)
                .filter(Files::isRegularFile)
                .map(path -> fileMethod(path, dataTypes))
                .toList();
    }

    @SneakyThrows
    private Boolean fileMethod(Path pathToFile, List<DataType> dataTypes) {
        String inputFileName = pathToFile.getFileName().toString();
        log.info("Current File is : " + inputFileName);

        Path outDirectoryName = Paths.get(
                new ClassPathResource("files-in").getFile().getParentFile().getAbsolutePath() + "/files-out");
        Path outFileName = Paths.get(outDirectoryName + "/" + inputFileName.replaceAll(".txt", ".json"));
        if (!Files.exists(outDirectoryName)) {
            log.info("Create directory" + outDirectoryName);
            Files.createDirectory(outDirectoryName);
        }
        if (Files.exists(outFileName)) {
            log.info("Erase all data in file :" + outFileName);
            FileChannel.open(outFileName, StandardOpenOption.WRITE).truncate(0).close();
        }
        Files.lines(pathToFile).forEach(line -> lineMethod(line, outFileName, dataTypes));
        return true;
    }

    @SneakyThrows
    private void lineMethod(String line, Path outPath, List<DataType> dataTypes) {
        for (DataType dataType : dataTypes) {
            Pattern patternLine = Pattern.compile(dataType.eventRegExp());
            Matcher matcherLine = patternLine.matcher(line);

            if (matcherLine.find()) {
                DataTypeOut dataTypeOut = new DataTypeOut(dataType.type(), LocalDateTime.now(), new HashMap<>());

                dataType.data().forEach((key, value) -> {
                    Pattern patternSubString = Pattern.compile(value);
                    Matcher matcherSubString = patternSubString.matcher(line);
                    if (matcherSubString.find()) {
                        String substring = line.substring(matcherSubString.start(), matcherSubString.end());
                        dataTypeOut.data().put(key, substring);
                    }
                });
                String resultLine = objectMapper.writeValueAsString(dataTypeOut);
                log.info("Line : " + line);
                log.info("Json: " + resultLine);
                writeToFile(resultLine, outPath);
            }
        }
    }

    @SneakyThrows
    private void writeToFile(String lineToWrite, Path outPath) {
        if (!lineToWrite.isEmpty()) {
            Files.writeString(outPath,
                    lineToWrite + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }
}
