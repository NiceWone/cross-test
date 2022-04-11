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
        Path inputDirectoryPath = Paths.get(new ClassPathResource("files-in").getFile().getAbsolutePath());
        List<Path> pathToInputFileList = Files.walk(inputDirectoryPath).filter(Files::isRegularFile).toList();

        for (Path pathToFile : pathToInputFileList) {
            String inputFileName = pathToFile.getFileName().toString();
            log.info("Current File is : " + inputFileName);

            Path outDirectoryName = Paths.get(new ClassPathResource("files-in").getFile()
                    .getParentFile().getAbsolutePath() + "/files-out");
            Path outFileName = Paths.get(outDirectoryName + "/" + inputFileName);
            if (!Files.exists(outDirectoryName)) {
                Files.createDirectory(outDirectoryName);
            }

            for (String line : Files.lines(pathToFile).toList()) {
                doParse(line, outFileName, dataTypes);
            }
        }

        return null;
    }

    private void doParse(String line, Path outPath, List<DataType> dataTypes) throws IOException {
        List<String> toWriteList = new ArrayList<>();
        for (DataType dataType : dataTypes) {
            Pattern patternLine = Pattern.compile(dataType.eventRegExp());
            Matcher matcherLine = patternLine.matcher(line);

            if (matcherLine.find()) {
                DataTypeOut dataTypeOut = new DataTypeOut(dataType.type(), LocalDateTime.now(), new HashMap<>());

                for (Entry<String, String> entry : dataType.data().entrySet()) {
                    Pattern patternSubString = Pattern.compile(entry.getValue());
                    Matcher matcherSubString = patternSubString.matcher(line);
                    if (matcherSubString.find()) {
                        String substring = line.substring(matcherSubString.start(), matcherSubString.end());
                        dataTypeOut.data().put(entry.getKey(), substring);
                    }
                }
                String resultLine = objectMapper.writeValueAsString(dataTypeOut);
                log.info("Строка : " + line);
                log.info("Результат: " + resultLine);
                toWriteList.add(resultLine);
            }
        }
        writeToFile(toWriteList, outPath);
    }

    private void writeToFile(List<String> toWriteList, Path outPath) throws IOException {
        if (!toWriteList.isEmpty()) {
            Files.writeString(outPath,
                    toWriteList.stream().collect(Collectors.joining(System.lineSeparator()))
                            + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }
}
