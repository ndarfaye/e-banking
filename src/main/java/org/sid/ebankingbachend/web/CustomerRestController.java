package org.sid.ebankingbachend.web;

import org.sid.ebankingbachend.dtos.CustomerDTO;
import org.sid.ebankingbachend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbachend.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRestController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping(path = "/customers")
    public List<CustomerDTO> listCustomer(){

        return  bankAccountService.ListCustomer();
    }
    @GetMapping(path = "/customers/{id}")
    public CustomerDTO getCustomeId(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {

        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping(path = "/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

        return bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping(path = "/customers/{custumerId}")
    public CustomerDTO updateCustomer(@PathVariable Long custumerId,  @RequestBody CustomerDTO customerDTO){

        customerDTO.setId(custumerId);
     return bankAccountService.updateCustomer(customerDTO);

    }

    @DeleteMapping(path = "/customers/{id}")
    public void deleteCustomers(@PathVariable(value = "id") Long id){

        bankAccountService.deleteCustomer(id);
    }



}
