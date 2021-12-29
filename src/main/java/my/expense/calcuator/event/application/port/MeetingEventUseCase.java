package my.expense.calcuator.event.application.port;

import lombok.Builder;
import lombok.Value;
import my.expense.calcuator.event.domain.MeetingEvent;

import java.util.List;
import java.util.Optional;

public interface MeetingEventUseCase {

    List<MeetingEvent> getAll();

    List<MeetingEvent> findByName(String name);

    List<MeetingEvent> findByLocation(String location);

    Optional<MeetingEvent> findById(Long id);

    List<MeetingEvent> findByNameAndLocation(String name, String location);

    MeetingEvent addMeetingEvent(CreateMeetingEventCommand meetingEvent);

    void updateMeetingEvent(MeetingEvent meetingEvent);

    void removeById(Long id);


    @Value
    @Builder
    class CreateMeetingEventCommand {
        String name;
        String location;
    }
}
