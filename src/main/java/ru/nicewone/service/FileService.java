package ru.nicewone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
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
    public void doTheJob(List<DataType> dataTypes) {
        long start = System.nanoTime();
        Path inputDirectoryPath = Paths.get(new ClassPathResource("files-in").getFile().getAbsolutePath());

        Files.walk(inputDirectoryPath)
                .parallel()
                .filter(Files::isRegularFile)
                .forEach(path -> fileMethod(path, dataTypes));

        log.info("All time is " + (System.nanoTime() - start));
        log.info("All time is " + Duration.ofNanos(System.nanoTime() - start));
    }

    @SneakyThrows
    private void fileMethod(Path pathToFile, List<DataType> dataTypes) {
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
        Files.lines(pathToFile).forEach(line -> parseLineAndWrite(line, outFileName, dataTypes));
    }

    @SneakyThrows
    private void parseLineAndWrite(String line, Path outPath, List<DataType> dataTypes) {
        for (DataType dataType : dataTypes) {
            Matcher mainMatcher = Pattern.compile(dataType.eventRegExp()).matcher(line);

            if (mainMatcher.find()) {
                String subLine = line.substring(mainMatcher.start(), mainMatcher.end());
                DataTypeOut dataTypeOut = new DataTypeOut(dataType.type(), LocalDateTime.now(), new HashMap<>());

                dataType.data().forEach((key, value) -> {
                    Matcher subMatcher = Pattern.compile(value).matcher(subLine);
                    if (subMatcher.find()) {
                        String substring = subLine.substring(subMatcher.start(), subMatcher.end());
                        dataTypeOut.data().put(key, substring);
                    }
                });
                String resultLine = objectMapper.writeValueAsString(dataTypeOut);
                if (!resultLine.isEmpty()) {
                    Files.writeString(outPath, resultLine + System.lineSeparator(),
                            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
            }
        }
    }
}
