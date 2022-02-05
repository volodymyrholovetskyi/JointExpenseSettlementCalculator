package my.expense.calcuator.payment.application.port;

import my.expense.calcuator.payment.domain.Payment;

import java.util.List;

public interface PaymentUseCase {
    List<Payment> findByTitle(String title);

    List<Payment> findAll();
}
