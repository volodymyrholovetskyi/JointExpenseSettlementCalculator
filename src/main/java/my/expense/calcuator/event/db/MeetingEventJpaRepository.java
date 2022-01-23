package my.expense.calcuator.event.db;


import my.expense.calcuator.event.domain.MeetingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingEventJpaRepository extends JpaRepository<MeetingEvent, Long> {

    MeetingEvent save(MeetingEvent event);

    Optional<MeetingEvent> findById(Long id);

    List<MeetingEvent> findByNameStartsWithIgnoreCase(String name);
    List<MeetingEvent> findByLocationStartsWithIgnoreCase(String location);

    @Query("SELECT e FROM MeetingEvent e JOIN e.payers p " +
            "WHERE " +
            " lower(e.name) LIKE lower(concat('%', :name, '%')) " +
            " AND lower(e.location) LIKE lower(concat('%', :location, '%')) ")
    List<MeetingEvent> findByNameAndLocation(@Param("name") String name, @Param("location") String location);

}

