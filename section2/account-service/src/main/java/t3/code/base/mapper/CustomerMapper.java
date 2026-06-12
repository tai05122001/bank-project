package t3.code.base.mapper;

import t3.code.base.dto.AccountsDto;
import t3.code.base.dto.CustomerDto;
import t3.code.base.entity.Accounts;
import t3.code.base.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobilePhone(customerDto.getMobilePhone());
        return customer;
    }

    public static CustomerDto mapToCustomerDto( Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobilePhone(customer.getMobilePhone());
        return customerDto;
    }
}
