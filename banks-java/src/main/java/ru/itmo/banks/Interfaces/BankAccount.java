package ru.itmo.banks.Interfaces;

public interface BankAccount {
    void Withdraw(double sum);
    void Deposit(double sum);
    void AnnulTransaction(int id);
}
