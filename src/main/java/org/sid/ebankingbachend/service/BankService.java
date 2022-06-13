package org.sid.ebankingbachend.service;

import org.sid.ebankingbachend.entities.BankAccount;
import org.sid.ebankingbachend.entities.CurrentAccount;
import org.sid.ebankingbachend.entities.SavinAccount;
import org.sid.ebankingbachend.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BankService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    public void consulter(){
    BankAccount bankAccount = bankAccountRepository.findById("0a9c01f5-d478-499b-ac2c-97b712fef08b").
            orElse(null);
            if(bankAccount != null) {
        System.out.println("**************************************");
        System.out.println(bankAccount.getId());
        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount.getDateAt());
        System.out.println(bankAccount.getStatus());
        System.out.println(bankAccount.getCustomer().getName());
        System.out.println(bankAccount.getClass().getSimpleName());

        if (bankAccount instanceof CurrentAccount) {
            System.out.println("Over draf =>" + ((CurrentAccount) bankAccount).getOverDecouvert());
        }
        else if (bankAccount instanceof SavinAccount) {
            System.out.println("Rate =>" + ((SavinAccount) bankAccount).getInterestRate());
        }

        bankAccount.getAccountOperation().forEach(op -> {
            System.out.println(op.getType() + " \t" + op.getOperationDate() +
                    " \t" + op.getAmount());
        });
    }
}
}
