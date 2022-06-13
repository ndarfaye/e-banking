package org.sid.ebankingbachend;

import org.sid.ebankingbachend.dtos.BankAccountDTO;
import org.sid.ebankingbachend.dtos.CurrentBankAccountDTO;
import org.sid.ebankingbachend.dtos.CustomerDTO;
import org.sid.ebankingbachend.dtos.SavingBankAccountDTO;
import org.sid.ebankingbachend.entities.*;
import org.sid.ebankingbachend.enums.AccountStatus;
import org.sid.ebankingbachend.enums.OperattionType;
import org.sid.ebankingbachend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbachend.exceptions.BankAccountNotFountException;
import org.sid.ebankingbachend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbachend.repository.AccountOperationRepository;
import org.sid.ebankingbachend.repository.BankAccountRepository;
import org.sid.ebankingbachend.repository.CustomerRepository;
import org.sid.ebankingbachend.service.BankAccountService;
import org.sid.ebankingbachend.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;


import javax.swing.plaf.basic.BasicListUI;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBachendApplication {
    @Autowired
    BankAccountRepository bankAccountRepository;

    public static void main(String[] args) {

        SpringApplication.run(EbankingBachendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return arg -> {
            Stream.of("Ndar","Pdf","Mamadou").forEach(name ->{
                CustomerDTO customerdto = new CustomerDTO();
                customerdto.setName(name);
                customerdto.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customerdto);
            });
            bankAccountService.ListCustomer().forEach(customer -> {

                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

                }
                catch (CustomerNotFoundException e){
                    e.printStackTrace();
                }

            });

            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();

            for (BankAccountDTO bankAccount:bankAccounts) {


                for (int i = 0; i < 5; i++) {
                    String accountId;

                    if (bankAccount instanceof SavingBankAccountDTO) {
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    } else {
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }

                    bankAccountService.credit(accountId, 10000 + Math.random() * 12000, "Credit");

                    bankAccountService.debit(accountId, 1000 + Math.random() * 1000, "Debit");
                }
            }
        };
    }

   //@Bean
   /*CommandLineRunner start(CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository
                        , BankAccountRepository bankAccountRepository){
        return args -> {
            Stream.of("FAYE","Ndar","dev").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
               customerRepository.save(customer);
            });

           customerRepository.findAll().forEach(cus->{
               CurrentAccount currentAccount = new CurrentAccount();
               currentAccount.setId(UUID.randomUUID().toString());
               currentAccount.setBalance(Math.random()*9000);
               currentAccount.setDateAt(new Date());
               currentAccount.setStatus(AccountStatus.ACTIVATED);
               currentAccount.setCustomer(cus);
               currentAccount.setOverDecouvert(9000);
               bankAccountRepository.save(currentAccount);

               SavinAccount savinAccount = new SavinAccount();
               savinAccount.setId(UUID.randomUUID().toString());
               savinAccount.setBalance(Math.random()*9000);
               savinAccount.setDateAt(new Date());
               savinAccount.setStatus(AccountStatus.CREATED);
               savinAccount.setCustomer(cus);
               savinAccount.setInterestRate(10000);
               bankAccountRepository.save(savinAccount);
           });

           bankAccountRepository.findAll().forEach( acc->{
                       for (int i = 0; i < 10; i++){
                           AccountOperation accountOperation = new AccountOperation();
                           accountOperation.setOperationDate(new Date());
                           accountOperation.setAmount(Math.random()*1200);
                           accountOperation.setType(Math.random()>0.5 ? OperattionType.DEBITE: OperattionType.DEBITE);
                           accountOperation.setBankAccount(acc);
                           accountOperationRepository.save(accountOperation);
                       }
                   });
        };
    }
*/
}
