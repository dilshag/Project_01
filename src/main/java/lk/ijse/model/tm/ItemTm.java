package lk.ijse.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ItemTm {
    private String ItemCode;
    private String ItemName;
    private double Price;
    private int QtyOnHand;
}
