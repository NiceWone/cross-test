package ru.nicewone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
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

    public Void doTheJob(List<DataType> dataTypes) throws IOException {
        Path inPath = Paths.get(new ClassPathResource("files-in").getFile().getAbsolutePath());
        List<Path> pathToFileList = Files.walk(inPath).filter(Files::isRegularFile).toList();

        for (Path pathToFile : pathToFileList) {
            String fileName = pathToFile.getFileName().toString();
            log.info("Current File is : " + fileName);

            Path directoryOutName = Paths.get(new ClassPathResource("files-in").getFile()
                    .getParentFile().getAbsolutePath() + "/files-out");
            if (!Files.exists(directoryOutName)) {
                Files.createDirectory(directoryOutName);
            }
            Path outPath = Paths.get(directoryOutName + "/" + fileName);
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
        List<String> toWriteList = new ArrayList<>(100);
        for (DataType dataType : dataTypes) {
            Pattern patternLine = Pattern.compile(dataType.eventRegExp());
            Matcher matcherLine = patternLine.matcher(line);

            if (matcherLine.find()) {
                DataTypeOut dataTypeOut = new DataTypeOut(dataType.type(), LocalDateTime.now(), new HashMap<>());

                for (Entry<String, String> entry : dataType.data().entrySet()) {
                    Pattern patternSubString = Pattern.compile(entry.getValue());
                    Matcher matcherSubString = patternSubString.matcher(line);
                    if (matcherSubString.find()) {
                        dataTypeOut.data().put(entry.getKey(), line.substring(matcherLine.start(), matcherLine.end()));
                        String resultLine = objectMapper.writeValueAsString(dataTypeOut);
                        log.info("Строка : " + line);
                        log.info("Результат: " + resultLine);
                        toWriteList.add(resultLine);
                    }
                }
            }
        }

        if (!toWriteList.isEmpty()) {
            String stingToWrite =
                    toWriteList.stream().collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator();
            Files.writeString(outPath, stingToWrite, StandardOpenOption.APPEND);
        }
    }
}
