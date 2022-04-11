package my.expense.calcuator.event.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import my.expense.calcuator.event.application.port.MeetingEventUseCase;
import my.expense.calcuator.event.application.port.MeetingEventUseCase.CreateMeetingEventCommand;
import my.expense.calcuator.event.application.port.MeetingEventUseCase.UpdateMeetingEventCommand;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.event.domain.MeetingEventStatus;
import my.expense.calcuator.payer.domain.Payer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("events")
public class MeetingEventController {

    private final MeetingEventUseCase useCase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<MeetingEvent> getAll(@RequestParam Optional<String> name,
                              @RequestParam Optional<String> location) {
        if (name.isPresent()) {
            return useCase.findByName(name.get());
        } else if (location.isPresent()) {
            return useCase.findByLocation(location.get());
        } else if (location.isPresent() && name.isPresent()) {
            return useCase.findByNameAndLocation(name.get(), location.get());
        }
        return useCase.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return useCase
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ResponseEntity<Void> addMeetingEvent(@RequestBody @Valid RestEventCommand command) {
        var meetingEvent = useCase.addMeetingEvent(command.toCreateCommand());
        return ResponseEntity.created(createMeetingEventUri(meetingEvent)).build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/{id}")
    void updateMeetingEvent(@PathVariable Long id, @RequestBody @Valid RestEventCommand command) {
        var response = useCase.updateMeetingEvent(command.toUpdateCommand(id));
        if (!response.isSuccess()) {
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteMeetingEvent(@PathVariable Long id) {
        useCase.removeById(id);
    }

    private URI createMeetingEventUri(MeetingEvent meetingEvent) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + meetingEvent.getId().toString()).build().toUri();

    }

    @Data
    private static class RestEventCommand {
        @NotBlank(message = "Please provide a name")
        @Size(min = 2)
        private String name;

        @NotBlank(message = "Please provide a location")
        @Size(min = 2)
        private String location;

        private List<Payer> payers;

        private MeetingEventStatus status;

        CreateMeetingEventCommand toCreateCommand() {
            return new CreateMeetingEventCommand(name, location);
        }

        UpdateMeetingEventCommand toUpdateCommand(Long id) {
            return new UpdateMeetingEventCommand(id, name, location, payers, status);
        }
    }
}
