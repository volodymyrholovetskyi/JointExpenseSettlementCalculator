package my.expense.calcuator.event.domain;

import java.util.List;
import java.util.Optional;

public interface MeetingEventRepository {

    MeetingEvent save(MeetingEvent event);

    List<MeetingEvent> findAll();

    List<MeetingEvent> findByName(String name);

    Optional<MeetingEvent> findById(Long id);
}
