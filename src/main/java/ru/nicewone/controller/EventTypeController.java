package ru.nicewone.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nicewone.entity.EventTypeEntity;
import ru.nicewone.model.DataType;
import ru.nicewone.service.EventTypeService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
public class EventTypeController {

    private final EventTypeService eventTypeService;

    @GetMapping("/run")
    @Operation(summary = "run")
    public void run() {
        eventTypeService.run();
    }

    @PostMapping("/add")
    @Operation(summary = "add")
    public EventTypeEntity addNewEventType(@RequestBody DataType dataType) {
        return eventTypeService.save(dataType);
    }

    @GetMapping("/receive")
    @Operation(summary = "receive")
    public List<DataType> receiveAllEventType() {
        return eventTypeService.receiveAllEventType();
    }

}
