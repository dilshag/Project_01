package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String CustomerID;
    private String Customer_Name;
    private String Customer_Address;
    private String PhoneNumber;
}
