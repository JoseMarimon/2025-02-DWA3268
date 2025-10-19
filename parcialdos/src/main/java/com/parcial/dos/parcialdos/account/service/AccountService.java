package com.parcial.dos.parcialdos.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;
import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.account.entity.Account;
import com.parcial.dos.parcialdos.account.repository.AccountRepository;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountResponseDTO create(AccountRequestDTO request) {
        Account account = new Account();
        account.setAccountNumber(request.getNumeroCuenta());
        account.setOwnerName(request.getDueno());
        account.setBalance(request.getBalanceActual() != null ? request.getBalanceActual() : BigDecimal.ZERO);
        account.setActive(true);

        Account saved = repository.save(account);
        return toResponseDTO(saved);
    }

    @Override
    public List<AccountResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDTO getById(Long id) {
        Account account = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return toResponseDTO(account);
    }

    @Override
    public String update(Long id, AccountRequestDTO request) {
        Account account = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        
        BigDecimal oldBalance = account.getBalance();
        account.setBalance(request.getBalanceActual());
        repository.save(account);

        return String.format("La cuenta %s fue actualizada: balanceAnterior=%.2f, balanceActual=%.2f",
                account.getAccountNumber(), oldBalance, account.getBalance());
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public AccountOwnerBalanceDTO findByNumeroCuenta(String numeroCuenta) {
        Account account = repository.findByAccountNumber(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return new AccountOwnerBalanceDTO(account.getOwnerName(), account.getBalance());
    }

    private AccountResponseDTO toResponseDTO(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getOwnerName(),
                account.getBalance(),
                account.getActive()
        );
    }
}
