package my.expense.calcuator.event.db;


import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.event.domain.MeetingEventRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Profile(value = "prod")
@Repository
public interface MeetingEventJpaRepository extends MeetingEventRepository, JpaRepository<MeetingEvent, Long> {

    MeetingEvent save(MeetingEvent event);

    @Override
    Optional<MeetingEvent> findById(Long id);
}

