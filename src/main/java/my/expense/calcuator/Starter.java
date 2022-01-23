package my.expense.calcuator;

import lombok.AllArgsConstructor;
import my.expense.calcuator.event.application.port.MeetingEventUseCase;
import my.expense.calcuator.event.application.port.MeetingEventUseCase.CreateMeetingEventCommand;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.payment.application.port.PayerUseCase;
import my.expense.calcuator.payment.db.PayerJpaRepository;
import my.expense.calcuator.payment.domain.Payer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

//
    }


    void addMeetingEven() {
        Payer volodymyr = new Payer();

        CreateMeetingEventCommand event = new CreateMeetingEventCommand("Wycieczka w góry", "Wrocław", List.of());

        CreateMeetingEventCommand fuel = new CreateMeetingEventCommand("paliwo", "Lublin", List.of());

        CreateMeetingEventCommand eat = new CreateMeetingEventCommand("Pizza", "Lublin", List.of());

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

    void addPayer() {
        PayerUseCase.CreatePayerCommand createPayerCommand1 = new PayerUseCase.CreatePayerCommand("Volodymyr",
                "Holovetskyi", "vova@gmail.com", 1l);
        payerUseCase.addPayer(createPayerCommand1);

        PayerUseCase.CreatePayerCommand createPayerCommand2 = new PayerUseCase.CreatePayerCommand("Volodymyr",
                "Holovetskyi", "volodymyr@gmail.com", 1l);
        payerUseCase.addPayer(createPayerCommand2);

        PayerUseCase.CreatePayerCommand createPayerCommand3 = new PayerUseCase.CreatePayerCommand("Volodymyr",
                "Holovetskyi", "holovetskyi@gmail.com", 3l);
        payerUseCase.addPayer(createPayerCommand3);

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
