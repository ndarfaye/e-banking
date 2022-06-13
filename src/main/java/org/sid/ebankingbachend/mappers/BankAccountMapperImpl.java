package org.sid.ebankingbachend.mappers;


import org.sid.ebankingbachend.dtos.AccountOperationDTO;
import org.sid.ebankingbachend.dtos.CurrentBankAccountDTO;
import org.sid.ebankingbachend.dtos.CustomerDTO;
import org.sid.ebankingbachend.dtos.SavingBankAccountDTO;
import org.sid.ebankingbachend.entities.AccountOperation;
import org.sid.ebankingbachend.entities.CurrentAccount;
import org.sid.ebankingbachend.entities.Customer;
import org.sid.ebankingbachend.entities.SavinAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

    public CustomerDTO fromCustomer(Customer customer){

        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        //customerDTO.setId(customer.getId());
        //customerDTO.setEmail(customer.getEmail());
        //customerDTO.setName(customer.getName());
        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO){

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return  customer;
    }

    public SavingBankAccountDTO fromSavinBankAccount(SavinAccount savinAccount){

        SavingBankAccountDTO savingAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savinAccount,savingAccountDTO);
        savingAccountDTO.setCustomerdto(fromCustomer(savinAccount.getCustomer()));
        savingAccountDTO.setType(savinAccount.getClass().getSimpleName());
        return savingAccountDTO;

    }

    public   SavinAccount fromSavinBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO){

        SavinAccount savinAccount = new SavinAccount();
        BeanUtils.copyProperties(savingBankAccountDTO, savinAccount);
        savinAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerdto()));

        return savinAccount;

    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){

        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerdto(currentBankAccountDTO.getCustomerdto());
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }

    public   CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){

        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO, currentAccount);
        currentAccount.setCustomer(currentAccount.getCustomer());
        return currentAccount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;

    }
}
