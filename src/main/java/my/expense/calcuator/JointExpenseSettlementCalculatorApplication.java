package my.expense.calcuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class JointExpenseSettlementCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(JointExpenseSettlementCalculatorApplication.class, args);
    }

}
