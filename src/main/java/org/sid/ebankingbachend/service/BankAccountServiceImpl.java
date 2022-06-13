package org.sid.ebankingbachend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbachend.dtos.*;
import org.sid.ebankingbachend.entities.*;
import org.sid.ebankingbachend.enums.OperattionType;
import org.sid.ebankingbachend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbachend.exceptions.BankAccountNotFountException;
import org.sid.ebankingbachend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbachend.mappers.BankAccountMapperImpl;
import org.sid.ebankingbachend.repository.AccountOperationRepository;
import org.sid.ebankingbachend.repository.BankAccountRepository;
import org.sid.ebankingbachend.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;



@Service
@Transactional
@AllArgsConstructor
@Slf4j
class BankAccountServiceImpl implements BankAccountService{

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtomappers;

    //cette instruction permettre de logger des message

   public final Logger log = LoggerFactory.getLogger(this.getClass().getName());

   /* @Override
    public Customer saveCustomer(Customer customer) {

        log.info("saving new customer");
        Customer customer1 = customerRepository.save(customer);
        return  customer1 ;
    }*/

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerdto) {

        log.info("saving new customer");
        Customer customer = dtomappers.fromCustomerDTO(customerdto);
        Customer saveCustomer = customerRepository.save(customer);
        return  dtomappers.fromCustomer(saveCustomer) ;
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interesRate, Long customerId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("customer not found");
        }
        SavinAccount savingAccount = new SavinAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setDateAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interesRate);
        savingAccount.setCustomer(customer);
        SavinAccount savinBankAccount = bankAccountRepository.save(savingAccount);
        return dtomappers.fromSavinBankAccount(savinBankAccount);

    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDecouvert, Long customerId)
            throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("customer not found");
        }

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setDateAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        CurrentAccount currentBankAccount = bankAccountRepository.save(currentAccount);
        return dtomappers.fromCurrentBankAccount(currentBankAccount);
    }

   /* @Override
    public List<Customer> ListCustomer() {
        return customerRepository.findAll();
    }*/

    @Override
    public List<CustomerDTO> ListCustomer(){
     //La programmation reactive
       List<Customer> customers= customerRepository.findAll();
          List<CustomerDTO> customerDTOS = customers.stream().map(
                  customer->dtomappers.fromCustomer(customer)).collect(Collectors.toList());
        /* List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer:customers){
            CustomerDTO customerDTO = dtomappers.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }
        *  La programmation classique
        */
        return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFountException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(
                ()-> new BankAccountNotFountException("Account not found"));
        if(bankAccount instanceof SavinAccount){
            SavinAccount savinAccount = (SavinAccount) bankAccount;
            return dtomappers.fromSavinBankAccount(savinAccount);
        }
        else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return dtomappers.fromCurrentBankAccount(currentAccount);
        }

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFountException, BalanceNotSufficientException {

        //BankAccount bankAccount = getBankAccount(accountId);
        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(
                        ()-> new BankAccountNotFountException("Account not found"));

        if(bankAccount.getBalance() < amount){

            throw new BalanceNotSufficientException("Desoler mais le solde de votre montant est inferieur au solde");

        }

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperattionType.DEBITE);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount); //mise a jour du compte
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFountException {
            //BankAccount bankAccount = getBankAccount(accountId);
        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(
                        ()-> new BankAccountNotFountException("Account not found"));

            AccountOperation accountOperation = new AccountOperation();
            accountOperation.setType(OperattionType.CREDIT);
            accountOperation.setAmount(amount);
            accountOperation.setOperationDate(new Date());
            accountOperation.setBankAccount(bankAccount);
            accountOperationRepository.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance() + amount);

            bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFountException, BalanceNotSufficientException {

        debit(accountIdSource, amount,"Tranfert from"+accountIdDestination );

        credit(accountIdDestination,amount,"Transfert from "+ accountIdSource);
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
        
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTO = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavinAccount) {
                SavinAccount savinAccount = (SavinAccount) bankAccount;
                return dtomappers.fromSavinBankAccount(savinAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtomappers.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());

        return bankAccountDTO;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {

        Customer customer = dtomappers.fromCustomerDTO(customerDTO);
        Customer customer1 = customerRepository.save(customer);

        return dtomappers.fromCustomer(customer1);
    }

    @Override
    public void deleteCustomer(Long customerId)  {

            customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
      Customer customer =  customerRepository.findById(customerId).orElseThrow(
              ()-> new CustomerNotFoundException("Customer not found"));
        return dtomappers.fromCustomer(customer);
    }

    @Override
    public List<AccountOperationDTO> bankAccountOperationDto(String accountId) {
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op->dtomappers.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accoundId, int page, int size) throws BankAccountNotFountException {

        BankAccount bankAccount = bankAccountRepository.findById(accoundId).orElse(null);
        if(bankAccount == null) throw new BankAccountNotFountException("Account not found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accoundId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationsDTO = accountOperations.stream().map(op->dtomappers.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationsDTO);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }


}
