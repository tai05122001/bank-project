package t3.code.base.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import t3.code.base.constants.AccountsConstants;
import t3.code.base.dto.AccountsDto;
import t3.code.base.dto.CustomerDto;
import t3.code.base.entity.Accounts;
import t3.code.base.entity.Customer;
import t3.code.base.exception.CustomerAlreadyExistsException;
import t3.code.base.exception.ResourceNotFoundException;
import t3.code.base.mapper.AccountsMapper;
import t3.code.base.mapper.CustomerMapper;
import t3.code.base.repository.AccountsRepository;
import t3.code.base.repository.CustomerRepository;
import t3.code.base.service.IAccountsService;

import java.time.LocalDateTime;
import java.util.Random;

import static t3.code.base.mapper.AccountsMapper.mapToAccountsDto;
import static t3.code.base.mapper.CustomerMapper.mapToCustomer;
import static t3.code.base.mapper.CustomerMapper.mapToCustomerDto;

@Service
@AllArgsConstructor
@Slf4j
public class AccountsServiceImpl implements IAccountsService {

    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;

    /**
     * @param customerDto customerDto Object
     */
    @Override
    public void createAccounts(CustomerDto customerDto) {
        log.info("name = {}, mobile_phone = {}, email ={}", customerDto.getName(), customerDto.getMobilePhone(), customerDto.getEmail());
        Customer customer = mapToCustomer(customerDto, new Customer());
        customerRepository.findCustomerByMobilePhone(customer.getMobilePhone()).ifPresent(c -> {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber: " + customer.getMobilePhone());
        });
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(this.createNewAccounts(savedCustomer));
        log.info("Account created successfully for customer: {}", customerDto.getName());
    }

    /**
     * @param mobilePhone mobile phone number
     * @return info of customer
     */
    @Override
    public CustomerDto fetchAccounts(String mobilePhone) {
        Customer customer = customerRepository.findCustomerByMobilePhone(mobilePhone)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", " mobileNumber:", mobilePhone));
        Accounts account = accountsRepository.findAccountsByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", " customerId:", customer.getCustomerId().toString()));

        CustomerDto customerDto = mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(mapToAccountsDto(account, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccounts(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", " accountNumber:", accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accountsRepository.save(accounts);
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", " customerId:", customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
            log.info("Account updated successfully for customer: {}", customerDto.getName());
            return isUpdated;
        }
        return isUpdated;
    }

    /**
     * @param mobilePhone the number phone of customer wanna delete
     * @return true if deleted successfully, false otherwise
     */
    @Override
    public boolean deleteAccounts(String mobilePhone) {
        Customer customer = customerRepository.findCustomerByMobilePhone(mobilePhone)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", " mobileNumber:", mobilePhone));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    /**
     * @param customer Customer Object
     * @return the new account details
     */
    private Accounts createNewAccounts(Customer customer) {

        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextLong(900000000L);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }
}

