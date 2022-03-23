package my.expense.calcuator.payer.application.port;

import my.expense.calcuator.payer.domain.Payer;

import java.util.List;
import java.util.Optional;

public interface QueryPayerUseCase {

    List<Payer> getAll();

    Optional<Payer> getById(Long id);

    List<Payer> findByFirstName(String firstName);

    List<Payer> findByLastName(String lastName);

    List<Payer> findByFirstNameAndLastName(String firstName, String lastName);
}
