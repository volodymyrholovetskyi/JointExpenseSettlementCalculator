package my.expense.calcuator.payment.web;


import lombok.AllArgsConstructor;
import my.expense.calcuator.payment.application.port.PaymentUseCase;
import my.expense.calcuator.payment.domain.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @GetMapping
    @ResponseStatus(OK)
    List<Payment> getAll(@RequestParam Optional<String> title){
        if (title.isPresent()){
            return paymentUseCase.findByTitle(title.get());
        }
        return paymentUseCase.findAll();
    }

}
