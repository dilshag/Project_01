package lk.ijse.model.tm;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CustomerTm {
    private String CustomerID;
    private String Customer_Name;
    private String Customer_Address;
    private String PhoneNumber;

}
