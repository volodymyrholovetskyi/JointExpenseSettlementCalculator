package my.expense.calcuator.event.domain;

import lombok.Getter;

@Getter
public enum MeetingEventStatus {

    NEW() {
        @Override
        public UpdateStatusResult updateStatus(MeetingEventStatus status) {
            if (status == NEW) return UpdateStatusResult.ok(DURING_SETTLEMENT);
            return super.updateStatus(status);
        }
    },

    DURING_SETTLEMENT() {
        public UpdateStatusResult updateStatus(MeetingEventStatus status) {
            if (status == DURING_SETTLEMENT) return UpdateStatusResult.ok(SETTLED);
            return super.updateStatus(status);
        }
    },

    SETTLED() {
        public UpdateStatusResult updateStatus(MeetingEventStatus status){
            if (status == SETTLED) return UpdateStatusResult.revoked(DURING_SETTLEMENT);
            return super.updateStatus(status);
        }

    };

    public UpdateStatusResult updateStatus(MeetingEventStatus status) {
        throw new IllegalArgumentException("Unable to mark " + this.name());
    }
}
