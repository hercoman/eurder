package com.switchfully.eurderproject.customer.service;

import com.switchfully.eurderproject.customer.api.dto.CreateCustomerDTO;
import com.switchfully.eurderproject.customer.api.dto.CustomerDTO;
import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import com.switchfully.eurderproject.infrastructure.api.dto.AddressDTO;
import com.switchfully.eurderproject.infrastructure.api.dto.PostalCodeDTO;
import com.switchfully.eurderproject.order.api.dto.OrderReportDTO;
import com.switchfully.eurderproject.order.api.dto.ReportDTO;
import com.switchfully.eurderproject.order.domain.Order;
import com.switchfully.eurderproject.order.domain.OrderRepository;
import com.switchfully.eurderproject.order.service.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CustomerService {
    private final Logger customerServiceLogger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerMapper customerMapper;
    private final OrderMapper orderMapper;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerMapper customerMapper, OrderMapper orderMapper, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerMapper = customerMapper;
        this.orderMapper = orderMapper;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO) {
        assertEmailIsUnique(createCustomerDTO.getEmail());
        assertAddressExists(createCustomerDTO.getAddress());
        assertPostalCodeExists(createCustomerDTO.getAddress().getPostalCode());
        Customer customer = customerMapper.toCustomer(createCustomerDTO);
        customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    private void assertEmailIsUnique(String email) {
        if (customerRepository.findAll().stream()
                .anyMatch(customer -> customer.getEmail().equalsIgnoreCase(email))) {
            customerServiceLogger.error("Attempted to create customer with already existing e-mail address");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to use given e-mail address for new customer's email address");
        }
        customerServiceLogger.info("Successfully validated new customer's email address to be unique");
    }

    private void assertPostalCodeExists(PostalCodeDTO postalCode) {
        if (postalCode == null) {
            customerServiceLogger.error("Customer can't be created without postal code");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No postal code given for new customer");
        }
        customerServiceLogger.info("Successfully validated new customer's postal code");
    }

    private void assertAddressExists(AddressDTO address) {
        if (address == null) {
            customerServiceLogger.error("Customer can't be created without address");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No address given for new customer");
        }
        customerServiceLogger.info("Successfully validated new customer's address");
    }

    public List<CustomerDTO> viewAll() {
        return customerMapper.toDTO(customerRepository.findAll());
    }

    public CustomerDTO viewCustomer(String customerId) {
        assertCustomerExists(customerId);
        return customerMapper.toDTO(customerRepository.getById(customerId));
    }

    public ReportDTO viewOrderReport(String customerId) {
        assertCustomerExists(customerId);
        List<Order> customerOrders = orderRepository.findByCustomer(customerRepository.getById(customerId));
        List<OrderReportDTO> customerOrderDTOs = orderMapper.toOrderReportDTO(customerOrders);
        double allOrderPrice = customerOrderDTOs.stream()
                .map(OrderReportDTO::getTotalOrderPrice)
                .reduce(0.0, Double::sum);
        return orderMapper.toReport(customerOrderDTOs, allOrderPrice);
    }

    private void assertCustomerExists(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            customerServiceLogger.error("No customer could be found for id " + customerId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find customer for id " + customerId);
        }
    }
}
