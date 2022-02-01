package my.expense.calcuator.payment.domain;

import com.fasterxml.jackson.databind.deser.Deserializers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
public enum PayerStatus {

    NEW(), DURING_SETTLEMENT(), SETTLED();
}
