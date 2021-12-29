package my.expense.calcuator.event.infrastructure;

import lombok.extern.log4j.Log4j2;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.event.domain.MeetingEventRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Profile(value = "dev")
@Log4j2
@Repository
public class MemoryMeetingEventRepository implements MeetingEventRepository {

    private Map<Long, MeetingEvent> events = initialization();

    @Override
    public MeetingEvent save(MeetingEvent event) {
        event.setCreateAt(LocalDateTime.now());
        long keyId = getNextId();
        log.info("Key next: {}", keyId);
        return events.put(keyId, event);
    }

    @Override
    public List<MeetingEvent> findAll() {
        return new ArrayList<>(events.values());
    }

    @Override
    public List<MeetingEvent> findByName(String name) {
        return null;
    }

    @Override
    public Optional<MeetingEvent> findById(Long id) {
        return Optional.ofNullable(events.get(id));
    }

    private Map<Long, MeetingEvent> initialization() {
        return new HashMap<>();
    }

    private Long getNextId() {
        return events.entrySet().stream().count() + 1;
    }
}
