package ru.itmo.banks.Interfaces;

import ru.itmo.banks.Entities.Bank;
import ru.itmo.banks.Entities.Client;

import java.util.Map;

public interface BankAccount {
    int get_id();
    String get_type();
    Map<Integer, Double> get_operationsHistory();
    Bank get_bank();
    void set_bank(Bank _bank);
    Client get_client();
    void set_client(Client _client);
    double get_funds();
    void set_funds(double _funds);
    void Withdraw(double sum);
    void Deposit(double sum);
    void AnnulTransaction(int id);
}
