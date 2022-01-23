
package my.expense.calcuator.event.application.port;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.payment.domain.Payer;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface MeetingEventUseCase {

    List<MeetingEvent> getAll();

    List<MeetingEvent> findByName(String name);

    List<MeetingEvent> findByLocation(String location);

    Optional<MeetingEvent> findById(Long id);

    List<MeetingEvent> findByNameAndLocation(String name, String location);

    MeetingEvent addMeetingEvent(CreateMeetingEventCommand command);

    UpdateMeetingEventResponse updateMeetingEvent(UpdateMeetingEventCommand command);

    void removeById(Long id);


    @Value
    class CreateMeetingEventCommand {
        String name;
        String location;
        List<Payer> payers;
    }

    @Valid
    @Builder
    @AllArgsConstructor
    @Getter
    class UpdateMeetingEventCommand {
        Long id;
        String name;
        String location;
        List<Payer> payers;
    }

    @Value
    class UpdateMeetingEventResponse {
        public static UpdateMeetingEventResponse SUCCESS = new UpdateMeetingEventResponse(true, Collections.emptyList());

        boolean success;
        List<String> errors;
    }
}
