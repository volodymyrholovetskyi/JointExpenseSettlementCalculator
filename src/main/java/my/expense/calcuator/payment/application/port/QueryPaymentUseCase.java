package my.expense.calcuator.payment.application.port;

import my.expense.calcuator.payment.domain.Payment;

import java.util.List;
import java.util.Optional;

public interface QueryPaymentUseCase {

    List<Payment> findByTitle(String title);

    List<Payment> findAll();

    Optional<Payment> findById(Long id);
}
