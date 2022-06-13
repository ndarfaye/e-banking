package org.sid.ebankingbachend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbachend.entities.BankAccount;
import org.sid.ebankingbachend.enums.OperattionType;

import javax.persistence.*;
import java.util.Date;

@Data
public class AccountOperationDTO {

    private Long Id;
    private Date OperationDate;
    private double amount;
    private OperattionType type;
    private String description;


}
