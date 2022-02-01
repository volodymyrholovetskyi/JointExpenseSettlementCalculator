package my.expense.calcuator.payment.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import my.expense.calcuator.payment.application.port.PayerUseCase;
import my.expense.calcuator.payment.application.port.PayerUseCase.CreatePayerCommand;
import my.expense.calcuator.payment.application.port.PayerUseCase.CreatePaymentCommand;
import my.expense.calcuator.payment.application.port.PayerUseCase.UpdatePayerCommand;
import my.expense.calcuator.payment.application.port.PayerUseCase.UpdatePayerResponse;
import my.expense.calcuator.payment.application.port.QueryPayerUseCase;
import my.expense.calcuator.payment.domain.Payer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("payers")
public class PayerController {

    private final QueryPayerUseCase queryPayer;
    private final PayerUseCase payerUseCase;


    @GetMapping()
    @ResponseStatus(OK)
    List<Payer> gitAll(@RequestParam Optional<String> firstName,
                       @RequestParam Optional<String> lastName) {

        if (firstName.isPresent()) {
            return queryPayer.findByFirstName(firstName.get());
        } else if (lastName.isPresent()) {
            return queryPayer.findByLastName(lastName.get());
        } else if (firstName.isPresent() && lastName.isPresent()) {
            return queryPayer.findByFirstNameAndLastName(firstName.get(), lastName.get());
        }
        return queryPayer.getAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable Long id) {
        return queryPayer.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    ResponseEntity<Void> addPayer(@RequestBody @Validated(CreateValidation.class) RestPayerCommand command) {
        var payer = payerUseCase.addPayer(command.toCreateCommand());
        return ResponseEntity.created(createdPayerUri(payer)).build();
    }

    private URI createdPayerUri(Payer payer) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/" + payer.getId().toString()).build().toUri();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public void updatePayer(@PathVariable Long id, @RequestBody @Validated({UpdateValidation.class}) RestPayerCommand command) {
        UpdatePayerResponse response = payerUseCase.updatePayer(command.toUpdateCommand(id));
        if (!response.isSuccess()) {
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(BAD_REQUEST, message);
        }
    }

    @PutMapping("/{id}/payment")
    @ResponseStatus(ACCEPTED)
    void addPaymentToThePayer(@PathVariable Long id, @RequestBody RestPaymentCommand command) {
      UpdatePaymentToThePayer response = payerUseCase.updatePaymentToThePayer(command.toCreatePaymentCommand(id));
    if (!response.isSuccess()) {
        String message = String.join(",", response.getErrors());
        throw new ResponseStatusException(BAD_REQUEST, message);
    }
    }


    interface UpdateValidation {
    }

    interface CreateValidation {

    }

    @Data
    private static class RestPayerCommand {

        private Long eventId;

        @NotBlank(message = "Please provide a first name", groups = CreateValidation.class)
        private String firstName;

        private String lastName;

        @NotBlank(message = "Please provide a name", groups = CreateValidation.class)
        private String email;

        CreatePayerCommand toCreateCommand() {
            return new CreatePayerCommand(eventId, firstName, lastName, email);
        }

        UpdatePayerCommand toUpdateCommand(Long id) {
            return new UpdatePayerCommand(id, firstName, lastName, email);
        }

    }

    private static class RestPaymentCommand {

        private String whatFor;

        private BigDecimal payment;

        CreatePaymentCommand toCreatePaymentCommand(Long id) {
          return new CreatePaymentCommand(id, whatFor, payment);
        }
    }
}
