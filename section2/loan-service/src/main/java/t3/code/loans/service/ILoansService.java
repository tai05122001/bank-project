package t3.code.loans.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import t3.code.loans.dto.LoansDto;

public interface ILoansService {

    LoansDto getLoanByMobileNumber(String mobileNumber);

    void createLoan(LoansDto loansDto);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoanDetail(String mobileNumber);
}
