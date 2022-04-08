package com.switchfully.eurderproject.customer.api;

import com.switchfully.eurderproject.customer.api.dto.CreateCustomerDTO;
import com.switchfully.eurderproject.customer.api.dto.CustomerDTO;
import com.switchfully.eurderproject.customer.service.CustomerService;
import com.switchfully.eurderproject.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.switchfully.eurderproject.security.Feature.*;

@RestController
@RequestMapping(path = "customers")
public class CustomerController {

    private final CustomerService customerService;
    private final SecurityService securityService;

    public CustomerController(CustomerService customerService, SecurityService securityService) {
        this.customerService = customerService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        return customerService.createCustomer(createCustomerDTO);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> viewAll() {
        return customerService.viewAll();
    }

    @GetMapping(path = "/{customerId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO viewCustomer(@PathVariable String customerId) {
        return customerService.viewCustomer(customerId);
    }
}
