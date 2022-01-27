package my.expense.calcuator.payment.domain;

import com.fasterxml.jackson.databind.deser.Deserializers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum PayerStatus {

    NEW("new"), DURING_SETTLEMENT("during settlement"), SETTLED("settled");

    private String name;


}
