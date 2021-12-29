package my.expense.calcuator.event.web;

import lombok.AllArgsConstructor;
import my.expense.calcuator.event.application.port.MeetingEventUseCase;
import my.expense.calcuator.event.domain.MeetingEvent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("events")
public class MeetingEventController {

    private final MeetingEventUseCase useCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<MeetingEvent> getAll() {
        return useCase.getAll();
    }
}
