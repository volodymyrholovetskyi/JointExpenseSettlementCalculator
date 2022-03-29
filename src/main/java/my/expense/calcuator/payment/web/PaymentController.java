package my.expense.calcuator.payment.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import my.expense.calcuator.payment.application.port.PaymentUseCase;
import my.expense.calcuator.payment.application.port.PaymentUseCase.CreatePaymentCommand;
import my.expense.calcuator.payment.application.port.PaymentUseCase.UpdatePaymentCommand;
import my.expense.calcuator.payment.application.port.PaymentUseCase.UpdatePaymentResponse;
import my.expense.calcuator.payment.application.port.QueryPaymentUseCase;
import my.expense.calcuator.payment.domain.Payment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentUseCase paymentUseCase;
    private final QueryPaymentUseCase queryPayment;

    @GetMapping
    @ResponseStatus(OK)
    List<Payment> getAll(@RequestParam Optional<String> title) {
        if (title.isPresent()) {
            return queryPayment.findByTitle(title.get());
        }
        return queryPayment.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable Long id) {
        return queryPayment.findById(id)
                .map(payment -> ResponseEntity.ok(payment))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    ResponseEntity<Void> addPayment(@RequestBody @Validated(CreateValidation.class) RestPaymentCommand command) {
        var payment = paymentUseCase.createPayment(command.toCreateCommand());
        return ResponseEntity.created(createPaymentUri(payment)).build();
    }

    @PatchMapping("/{id}")
    void updatePayment(@PathVariable Long id,
                       @RequestBody @Validated({UpdateValidation.class}) RestPaymentCommand command) {
        UpdatePaymentResponse response = paymentUseCase.updatePayment(command.toUpdateCommand(id));

        if (!response.isSuccess()) {
            String message = String.join(",", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deletePayment(@PathVariable Long id){
        paymentUseCase.deleteById(id);
    }

    private URI createPaymentUri(Payment payment) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/" + payment.getId().toString()).build().toUri();
    }

    private interface CreateValidation{

    }
    private interface UpdateValidation{

    }

    @Data
    private static class RestPaymentCommand {

        private Long payerId;

        @NotBlank(message = "Please provide a title", groups = CreateValidation.class)
        @Size(min = 2)
        private String title;

        @NotNull(groups = {CreateValidation.class})
        @DecimalMin(value = "0.00", groups = {CreateValidation.class, UpdateValidation.class})
        private BigDecimal payment;

        @CreatedDate
        private LocalDateTime created;

        @LastModifiedDate
        private LocalDateTime updated;

        CreatePaymentCommand toCreateCommand() {
            return new CreatePaymentCommand(payerId, title, payment);
        }

        UpdatePaymentCommand toUpdateCommand(Long id) {
            return new UpdatePaymentCommand(id, title, payment);
        }
    }

}
