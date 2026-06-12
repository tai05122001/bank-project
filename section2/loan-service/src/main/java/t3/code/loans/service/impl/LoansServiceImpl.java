package t3.code.loans.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import t3.code.loans.dto.LoansDto;
import t3.code.loans.entity.Loans;
import t3.code.loans.exception.LoansAlreadyExistsException;
import t3.code.loans.exception.ResourceNotFoundException;
import t3.code.loans.mapper.LoansMapper;
import t3.code.loans.repository.LoansRepository;
import t3.code.loans.service.ILoansService;

import java.util.Random;

import static t3.code.loans.constants.LoansConstants.HOME_LOAN;
import static t3.code.loans.constants.LoansConstants.NEW_LOAN_LIMIT;

@Service
@AllArgsConstructor
@Slf4j
public class LoansServiceImpl implements ILoansService {

    private final LoansRepository loansRepository;


    /***
     * GET loan by mobile number
     * @param mobileNumber mobile number of the user
     * @return loan details
     */
    @Override
    public LoansDto getLoanByMobileNumber(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", " mobileNumber:", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    /***
     * create loan
     * @param loansDto input loan details
     */
    @Override
    public void createLoan(LoansDto loansDto) {
        loansRepository.findByMobileNumber(loansDto.getMobileNumber()).ifPresent(loans -> {
            throw new LoansAlreadyExistsException("Loan already exists for mobile number: " + loansDto.getMobileNumber());
        });
        loansRepository.save(this.createNewLoan(loansDto.getMobileNumber()));
    }

    /***
     * create new loans
     * @param mobileNumber mobile number of the user
     * @return new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans loans = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        loans.setLoanNumber(String.valueOf(randomLoanNumber));
        loans.setMobileNumber(mobileNumber);
        loans.setLoanType(HOME_LOAN);
        loans.setTotalLoan(NEW_LOAN_LIMIT);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(NEW_LOAN_LIMIT);
        return loans;
    }

    /***
     * update info of the loan
     * @param loansDto input loan details
     * @return true if updated successfully
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", " mobileNumber:", loansDto.getLoanNumber())
        );
        loansRepository.save(LoansMapper.mapToLoans(loansDto, loans));
        return true;
    }

    /***
     * delete loan details
     * @param mobileNumber mobile number of the user
     * @return true if deleted successfully
     */
    @Override
    public boolean deleteLoanDetail(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", " mobileNumber:", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}

