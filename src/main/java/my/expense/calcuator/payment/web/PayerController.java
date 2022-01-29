package my.expense.calcuator.payment.web;

import lombok.RequiredArgsConstructor;
import my.expense.calcuator.payment.application.port.QueryPayerUseCase;
import my.expense.calcuator.payment.domain.Payer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("payers")
public class PayerController {

    private final QueryPayerUseCase queryPayer;


    @GetMapping()
    public List<Payer> gitAll(@RequestParam Optional<String> firstName,
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
}
