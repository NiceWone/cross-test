package ru.nicewone.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nicewone.entity.AttribEntity;
import ru.nicewone.entity.EventTypeEntity;
import ru.nicewone.model.DataType;
import ru.nicewone.repository.DataTypeRepository;

@Service
@AllArgsConstructor
public class EventTypeService {

    private final DataTypeRepository dataTypeRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<DataType> receiveAllEventType() {
        return dataTypeRepository.findAll().stream()
                .map(event -> new DataType(event.getEventType(), event.getMainRegExp(),
                        event.getAttribEntities().stream()
                                .collect(
                                        Collectors.toMap(AttribEntity::getAttribName, AttribEntity::getAttribRegExp))))
                .toList();
    }

    @Transactional
    public EventTypeEntity save(DataType dataType) {
        EventTypeEntity eventTypeEntity = new EventTypeEntity();
        eventTypeEntity.setEventType(dataType.type());
        eventTypeEntity.setMainRegExp(dataType.eventRegExp());
        eventTypeEntity.setAttribEntities(dataType.data()
                .entrySet().stream()
                .map(s -> AttribEntity.builder()
                        .attribName(s.getKey())
                        .attribRegExp(s.getValue())
                        .build())
                .toList());
        return dataTypeRepository.save(eventTypeEntity);
    }

    @Transactional(readOnly = true)
    public Void run() throws IOException {
        return fileService.doTheJob(receiveAllEventType());
    }
}
