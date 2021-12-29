package my.expense.calcuator.event.application;

import lombok.AllArgsConstructor;
import my.expense.calcuator.event.application.port.MeetingEventUseCase;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.event.domain.MeetingEventRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MetingEventService implements MeetingEventUseCase {

    private final MeetingEventRepository repository;

    @Override
    public List<MeetingEvent> getAll() {
        return repository.findAll();
    }

    @Override
    public List<MeetingEvent> findByName(String name) {
        List<MeetingEvent> events = repository.findAll();
        return events.stream()
                .filter(event ->
                        StringUtils.startsWithIgnoreCase(event.getName(), name)
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingEvent> findByLocation(String location) {
        return repository.findAll()
                .stream()
                .filter(event ->
                        StringUtils.startsWithIgnoreCase(event.getLocation(), location)
                )
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MeetingEvent> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<MeetingEvent> findByNameAndLocation(String name, String location) {
        return repository.findAll()
                .stream()
                .filter(event ->
                        StringUtils.startsWithIgnoreCase(event.getName(), name)
                                && StringUtils.startsWithIgnoreCase(event.getLocation(), location)
                )
                .collect(Collectors.toList());
    }

    @Override
    public MeetingEvent addMeetingEvent(CreateMeetingEventCommand command) {
        MeetingEvent meetingEvent = toMeetingEvent(command);
        return repository.save(meetingEvent);
    }

    private MeetingEvent toMeetingEvent(CreateMeetingEventCommand command) {
        MeetingEvent meetingEvent = MeetingEvent.builder()
                .name(command.getName())
                .location(command.getLocation())
                .build();
        return meetingEvent;
    }

    @Override
    public void updateMeetingEvent(MeetingEvent meetingEvent) {
    }

    @Override
    public void removeById(Long id) {

    }
}
