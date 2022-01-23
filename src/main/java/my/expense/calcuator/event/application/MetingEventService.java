package my.expense.calcuator.event.application;

import lombok.AllArgsConstructor;
import my.expense.calcuator.event.application.port.MeetingEventUseCase;
import my.expense.calcuator.event.db.MeetingEventJpaRepository;
import my.expense.calcuator.event.domain.MeetingEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MetingEventService implements MeetingEventUseCase {

    private final MeetingEventJpaRepository repository;

    @Override
    public List<MeetingEvent> getAll() {
        return repository.findAll();
    }

    @Override
    public List<MeetingEvent> findByName(String name) {
        return repository.findByNameStartsWithIgnoreCase(name);
    }

    @Override
    public List<MeetingEvent> findByLocation(String location) {
        return repository.findByLocationStartsWithIgnoreCase(location);
    }

    @Override
    public Optional<MeetingEvent> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<MeetingEvent> findByNameAndLocation(String name, String location) {
        return repository.findByNameAndLocation(name, location);
    }

    @Override
    @Transactional
    public MeetingEvent addMeetingEvent(CreateMeetingEventCommand command) {
        var meetingEvent = toMeetingEvent(command);
        return repository.save(meetingEvent);
    }

    private MeetingEvent toMeetingEvent(CreateMeetingEventCommand command) {
        var meetingEvent = MeetingEvent.builder()
                .name(command.getName())
                .location(command.getLocation())
                .build();
        return meetingEvent;
    }

    @Transactional
    @Override
    public UpdateMeetingEventResponse updateMeetingEvent(UpdateMeetingEventCommand command) {
        return repository.findById(command.getId())
                .map(meetingEvent -> {
                    var updateEvent = updateFields(command, meetingEvent);
                    return UpdateMeetingEventResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateMeetingEventResponse(false,
                        Collections.singletonList("Meeting event not fond with id: " + command.getId())));
    }

    private MeetingEvent updateFields(UpdateMeetingEventCommand command, MeetingEvent meetingEvent) {

        if (command.getName() != null) {
            meetingEvent.setName(command.getName());
        }

        if (command.getLocation() != null) {
            meetingEvent.setLocation(command.getLocation());
        }
        return meetingEvent;
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }
}
