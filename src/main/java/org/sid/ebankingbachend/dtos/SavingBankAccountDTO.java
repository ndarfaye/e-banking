package org.sid.ebankingbachend.dtos;

import lombok.Data;
import org.sid.ebankingbachend.enums.AccountStatus;
import java.util.Date;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date dateAt;
    private AccountStatus status;
    private CustomerDTO customerdto;
    private double interestRate;

}
