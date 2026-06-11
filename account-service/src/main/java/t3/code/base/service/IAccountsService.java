package t3.code.base.service;

import t3.code.base.dto.CustomerDto;

public interface IAccountsService {
    /**
     * @param customerDto customerDto Object
     */
    void createAccounts(CustomerDto customerDto);

    /**
     * @param mobilePhone mobile phone number
     * @return info of customer
     */
    CustomerDto fetchAccounts(String mobilePhone);

    boolean updateAccounts(CustomerDto customerDto);

    boolean deleteAccounts(String mobilePhone);
}
