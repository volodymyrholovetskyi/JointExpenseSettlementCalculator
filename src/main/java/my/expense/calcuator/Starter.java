package my.expense.calcuator;

import lombok.AllArgsConstructor;

import my.expense.calcuator.event.application.port.MeetingEventUseCase;
import my.expense.calcuator.event.application.port.MeetingEventUseCase.CreateMeetingEventCommand;
import my.expense.calcuator.event.domain.MeetingEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class Starter implements CommandLineRunner {

    private final MeetingEventUseCase useCase;

    @Override
    public void run(String... args) throws Exception {

        CreateMeetingEventCommand event = CreateMeetingEventCommand.builder()
                .name("Wycieczka w góry")
                .location("Wrocław")
                .build();

        CreateMeetingEventCommand fuel = CreateMeetingEventCommand.builder()
                .name("Paliwo")
                .location("Lublin")
                .build();

        CreateMeetingEventCommand eat = CreateMeetingEventCommand.builder()
                .name("Pizza")
                .location("Lublin")
                .build();

        useCase.addMeetingEvent(event);
        useCase.addMeetingEvent(fuel);
        useCase.addMeetingEvent(eat);
        useCase.getAll().forEach(System.out::println);
        useCase.findByName("wycieczka").forEach(System.out::println);
        useCase.findByLocation("lub").forEach(System.out::println);
        useCase.findByNameAndLocation("wycieczka", "udd")
                .forEach(System.out::println);
        Long byId = 2L;
        Optional<MeetingEvent> byIdEvent = useCase.findById(byId);
        MeetingEvent meetingEvent = byIdEvent
                .orElseThrow(() ->
                        new RuntimeException("Meeting event for id: " + byId + " not found")
                );
        System.out.println(meetingEvent);

    }
}
