package my.expense.calcuator.payer.domain;

import com.fasterxml.jackson.databind.deser.Deserializers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
public enum PayerStatus {

    NOT_SETTLED(), DURING_SETTLEMENT(), SETTLED();
}
