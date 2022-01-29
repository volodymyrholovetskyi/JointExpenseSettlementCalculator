package my.expense.calcuator.payment.application;

import lombok.RequiredArgsConstructor;
import my.expense.calcuator.payment.application.port.QueryPayerUseCase;
import my.expense.calcuator.payment.db.PayerJpaRepository;
import my.expense.calcuator.payment.domain.Payer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryPayerService implements QueryPayerUseCase {

    private final PayerJpaRepository repository;

    @Override
    public List<Payer> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Payer> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Payer> findByFirstName(String firstName) {
       return repository.findByFirstNameStartsWithIgnoreCase(firstName);
    }

    @Override
    public List<Payer> findByLastName(String lastName) {
        return repository.findByLastNameStartsWithIgnoreCase(lastName);
    }

    @Override
    public List<Payer> findByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findByFistNameAndLastNameStartsWithIgnoreCase(firstName, lastName);
    }
}
