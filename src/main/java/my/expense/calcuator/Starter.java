package my.expense.calcuator;

import lombok.AllArgsConstructor;
import my.expense.calcuator.event.application.port.MeetingEventUseCase;
import my.expense.calcuator.event.application.port.MeetingEventUseCase.CreateMeetingEventCommand;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.payer.application.port.PayerUseCase;
import my.expense.calcuator.payer.db.PayerJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static my.expense.calcuator.payer.application.port.PayerUseCase.*;

@Component
@AllArgsConstructor
public class Starter implements CommandLineRunner {

    private final MeetingEventUseCase useCase;
    private final PayerUseCase payerUseCase;
    private final PayerJpaRepository repository;

    @Override
    public void run(String... args) throws Exception {

        addMeetingEven();
        addPayer();

    }


    void addMeetingEven() {

        CreateMeetingEventCommand event = new CreateMeetingEventCommand("Wycieczka w góry", "Wrocław");

        CreateMeetingEventCommand fuel = new CreateMeetingEventCommand("paliwo", "Lublin");

        CreateMeetingEventCommand eat = new CreateMeetingEventCommand("Pizza", "Lublin");

        useCase.addMeetingEvent(event);
        useCase.addMeetingEvent(fuel);
        useCase.addMeetingEvent(eat);
        useCase.getAll().forEach(System.out::println);
//        useCase.findByName("wycieczka").forEach(System.out::println);
//        useCase.findByLocation("lub").forEach(System.out::println);
//        useCase.findByNameAndLocation("wycieczka", "udd")
//                .forEach(System.out::println);
        Long byId = 1L;
        Optional<MeetingEvent> byIdEvent = useCase.findById(byId);
        MeetingEvent meetingEvent = byIdEvent
                .orElseThrow(() ->
                        new RuntimeException("Meeting event for id: " + byId + " not found")
                );
        System.out.println(meetingEvent);
    }

    void addPayer() {
        CreatePayerCommand volodymyr = CreatePayerCommand
                .builder()
                .firstName("Volodymyr")
                .lastName("Holovetskyi")
                .email("vova@gmail.com")
                .eventId(1l)
                .build();

        payerUseCase.addPayer(volodymyr);

        CreatePayerCommand maryk = CreatePayerCommand
                .builder()
                .firstName("Maryk")
                .lastName("Kyryl")
                .email("mark@gmail.com")
                .eventId(1l)
                .build();

        payerUseCase.addPayer(maryk);

        CreatePayerCommand mariia = CreatePayerCommand
                .builder()
                .firstName("Mariia")
                .lastName("Khort")
                .email("mariia@gmail.com")
                .eventId(1l)
                .build();

        payerUseCase.addPayer(mariia);

        CreatePayerCommand bogdan = CreatePayerCommand
                .builder()
                .firstName("Bogndan")
                .lastName("Bentsal")
                .email("bogdan@gmail.com")
                .eventId(1l)
                .build();

        payerUseCase.addPayer(bogdan);

//        List<Payer> payers = repository.getAllWithJoinFetch();

//        List<Payer> payers = payerUseCase.getAll();
//        for (Payer payer : payers) {
//            System.out.println(payer);
//        }
//        Optional<Payer> byId = payerUseCase.getById(4l);
//        System.out.println(byId.get());
////        System.out.println(byId.get().getEvent());
//        Optional<MeetingEvent> byId = useCase.findById(1l);
//        System.out.println(byId.get());
    }
}
