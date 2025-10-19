package com.parcial.dos.parcialdos.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;
import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.account.service.IAccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final IAccountService service;

    public AccountController(IAccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountRequestDTO request) {
        AccountResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAll() {
        List<AccountResponseDTO> accounts = service.getAll();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getById(@PathVariable Long id) {
        try {
            AccountResponseDTO response = service.getById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody AccountRequestDTO request) {
        try {
            String message = service.update(id, request);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-number/{numeroCuenta}")
    public ResponseEntity<AccountOwnerBalanceDTO> getByNumeroCuenta(@PathVariable String numeroCuenta) {
        try {
            AccountOwnerBalanceDTO response = service.findByNumeroCuenta(numeroCuenta);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
