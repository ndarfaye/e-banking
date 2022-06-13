package org.sid.ebankingbachend.web;

import org.sid.ebankingbachend.dtos.AccountHistoryDTO;
import org.sid.ebankingbachend.dtos.AccountOperationDTO;
import org.sid.ebankingbachend.dtos.BankAccountDTO;
import org.sid.ebankingbachend.exceptions.BankAccountNotFountException;
import org.sid.ebankingbachend.service.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SavingBankAccounttRestCotroller {

    private BankAccountService bankAccountService;

    public SavingBankAccounttRestCotroller(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @GetMapping(path = "/accounts/{accountId}")
    public BankAccountDTO getBankAccountDTO(@PathVariable String accountId) throws BankAccountNotFountException {

        return bankAccountService.getBankAccount(accountId);

    }
    @GetMapping(path = "/accounts")
    public List<BankAccountDTO> listBankAccount(){

        return bankAccountService.bankAccountList();
    }
    @GetMapping(path = "/accounts/{accoundId}/operations")
    public List<AccountOperationDTO> accountOperationDTOS(@PathVariable String accoundId){

        return bankAccountService.bankAccountOperationDto(accoundId);
    }

    @GetMapping(path = "/accounts/{accoundId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accoundId,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFountException {

        return bankAccountService.getAccountHistory(accoundId,page,size);
    }

}
