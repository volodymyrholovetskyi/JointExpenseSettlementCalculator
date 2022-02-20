package my.expense.calcuator.payer.application;

import lombok.RequiredArgsConstructor;
import my.expense.calcuator.payer.application.calculation.CalculateService;
import my.expense.calcuator.payer.application.calculation.SettlementPayer;
import my.expense.calcuator.payer.application.port.QueryPayerUseCase;
import my.expense.calcuator.payer.db.PayerJpaRepository;
import my.expense.calcuator.payer.domain.Payer;
import my.expense.calcuator.payment.db.PaymentJpaRepository;
import my.expense.calcuator.payment.domain.Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryPayerService implements QueryPayerUseCase {

    private final PayerJpaRepository repository;
    private final CalculateService calculateService;
    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public List<ExtendedPayer> getAll() {
        List<Payer> payers = repository.findAll();
        List<ExtendedPayer> extendedPayers = payers.stream().map(payer -> toExtendedPayer(payer, payers))
                .collect(Collectors.toList());
        return extendedPayers;
    }

    private ExtendedPayer toExtendedPayer(Payer payer, List<Payer> payers) {
        BigDecimal averages = paymentJpaRepository.getAveragesFromPayment();
        SettlementPayer settlement = calculateService.costExpense(averages, payers);
        ExtendedPayer extendedPayer = ExtendedPayer.builder()
                .id(settlement.getId())
                .payments(settlement.getPayments())
                .debtors(settlement.getDebtors())
                .debts(settlement.getDebts())
                .totalCost(settlement.)
                .build();

        return extendedPayer;
    }

//    private List<Payment> addPayment(List<Payment> payments) {
//        List<Payment> arrays = new ArrayList<>();
//        for (Payment payment : payments) {
//            arrays.add(payment);
//        }

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
        return repository.findByFirstNameAndLastNameStartsWithIgnoreCase(firstName, lastName);
    }


}
