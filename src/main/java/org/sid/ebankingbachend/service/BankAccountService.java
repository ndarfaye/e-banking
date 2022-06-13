package org.sid.ebankingbachend.service;

import org.sid.ebankingbachend.dtos.*;

import org.sid.ebankingbachend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbachend.exceptions.BankAccountNotFountException;
import org.sid.ebankingbachend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDecouvert, Long customerId )
            throws CustomerNotFoundException;

    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interesRate, Long customerId)
            throws  CustomerNotFoundException;
    //List<Customer> ListCustomer();
    List<CustomerDTO> ListCustomer();

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFountException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFountException, BalanceNotSufficientException;

    void credit (String accountId, double amount, String description) throws BankAccountNotFountException;

    void transfer(String accountIdSource, String accountIdDestination , double amount)
            throws BankAccountNotFountException, BalanceNotSufficientException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    public List<AccountOperationDTO> bankAccountOperationDto(String accountId);

    AccountHistoryDTO getAccountHistory(String accoundId, int page, int size) throws BankAccountNotFountException;
}
