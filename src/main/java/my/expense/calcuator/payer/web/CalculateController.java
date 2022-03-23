package my.expense.calcuator.payer.web;

import lombok.AllArgsConstructor;
import my.expense.calcuator.payer.application.calculation.CalculateService;
import my.expense.calcuator.payer.application.calculation.RichPayer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("calculates")
public class CalculateController {

    private final CalculateService calculateService;

    @GetMapping("/{id}")
    List<RichPayer> getSettlement(@PathVariable Long id){
        return calculateService.getCalculatedExpenses(id);
    }
}
