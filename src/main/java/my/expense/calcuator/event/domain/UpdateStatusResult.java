package my.expense.calcuator.event.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
public class UpdateStatusResult {

    MeetingEventStatus newStatus;
    boolean revoked;

    static UpdateStatusResult ok(MeetingEventStatus newStatus) {
        return new UpdateStatusResult(newStatus, true);
    }

    static UpdateStatusResult revoked(MeetingEventStatus newStatus) {
        return new UpdateStatusResult(newStatus, false);
    }
}
