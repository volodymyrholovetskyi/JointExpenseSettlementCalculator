package my.expense.calcuator.payment.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import my.expense.calcuator.payment.application.port.PayerUseCase;
import my.expense.calcuator.payment.application.port.PayerUseCase.CreatePayerCommand;
import my.expense.calcuator.payment.application.port.QueryPayerUseCase;
import my.expense.calcuator.payment.domain.Payer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("payers")
public class PayerController {

    private final QueryPayerUseCase queryPayer;
    private final PayerUseCase payerUseCase;


    @GetMapping()
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
    ResponseEntity<Void> addPayer(@RequestBody @Valid RestPayerCommand command){
        var payer = payerUseCase.addPayer(command.toCreateCommand());
        return ResponseEntity.created(createdPayerUri(payer)).build();
    }

    private URI createdPayerUri(Payer payer) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/" + payer.getId().toString()).build().toUri();
    }
}
