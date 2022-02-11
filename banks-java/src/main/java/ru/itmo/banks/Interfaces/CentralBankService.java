package ru.itmo.banks.Interfaces;

import ru.itmo.banks.Entities.Bank;
import ru.itmo.banks.Entities.Client;

import java.util.ArrayList;
import java.util.Map;

public interface CentralBankService {
    public ArrayList<Bank> get_banks();
    Bank RegistrateBank(String name, String address);
    void SetDebitConditions(Bank bank, double percent);
    void SetDepositConditions(Bank bank, Map<Double, Double> percents);
    void SetCreditConditions(Bank bank, double commission);
    Client RegistrateClient(String name, String surname, String phone);
    void AddClientAdress(Client client, String address);
    void AddClientPassport(Client client, String passport);
    void OpenDebitAccount(Bank bank, Client client);
    void OpenDepositAccount(Bank bank, Client client, double sum, int months);
    void OpenCreditAccount(Bank bank, Client client, double limit);
    void Withdraw(int accid, double sum);
    void Deposit(int accid, double sum);
    void Transfer(int accid1, int accid2, double sum);
    void RewindTime(int months);
    void AnnulOperation(int accid, int id);
    Map<Integer, Double> GetOperationsHistory(int accid);
    void SetSuspiciousConditions(Bank bank, double limit);
    ArrayList<String> GetNotifications(Client client);
    Client FindClient(String phone);
    Bank FindBank(String name);
}
