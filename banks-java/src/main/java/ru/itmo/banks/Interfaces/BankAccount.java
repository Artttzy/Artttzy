package ru.itmo.banks.Interfaces;

import ru.itmo.banks.Entities.Bank;
import ru.itmo.banks.Entities.Client;
import ru.itmo.banks.Exceptions.LackOfFundsCentralBankServiceException;

import java.util.Map;

public interface BankAccount {
    int get_id();
    String get_type();
    Map<Integer, Double> get_operationsHistory();
    Bank get_bank();
    void set_bank(Bank bank);
    Client get_client();
    void set_client(Client client);
    double get_funds();
    void set_funds(double funds);
    void withdraw(double sum) throws LackOfFundsCentralBankServiceException;
    void deposit(double sum);
    void annulTransaction(int id);
}
