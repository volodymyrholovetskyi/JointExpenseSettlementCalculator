package my.expense.calcuator.payer.application.calculation.strategy;

import my.expense.calcuator.event.application.port.MeetingEventUseCase;
import my.expense.calcuator.event.domain.MeetingEvent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculateTotalCostOfOnePersonTest {

    MeetingEventUseCase meetingEventUseCase;

    @Test
    public void calculateTotalCost(){
        // given
        MeetingEventUseCase.CreateMeetingEventCommand meetingEvent = M
                .location("New York")
                .name("A trip to the waterfall")
                .build();
        // when

        meetingEventUseCase.addMeetingEvent(meetingEvent)


        // then
    }
}
