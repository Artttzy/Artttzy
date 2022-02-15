package ru.itmo.banks.Interfaces;

import ru.itmo.banks.Entities.Bank;
import ru.itmo.banks.Entities.Client;
import ru.itmo.banks.Exceptions.LackOfFundsCentralBankServiceException;

import java.util.ArrayList;
import java.util.Map;

public interface CentralBankService {
    public ArrayList<Bank> get_banks();
    Bank registrateBank(String name, String address);
    void setDebitConditions(Bank bank, double percent);
    void setDepositConditions(Bank bank, Map<Double, Double> percents);
    void setCreditConditions(Bank bank, double commission);
    Client registrateClient(String name, String surname, String phone);
    void addClientAdress(Client client, String address);
    void addClientPassport(Client client, String passport);
    void openDebitAccount(Bank bank, Client client);
    void openDepositAccount(Bank bank, Client client, double sum, int months);
    void openCreditAccount(Bank bank, Client client, double limit);
    void withdraw(int accid, double sum) throws LackOfFundsCentralBankServiceException;
    void deposit(int accid, double sum);
    void transfer(int accid1, int accid2, double sum) throws LackOfFundsCentralBankServiceException;
    void rewindTime(int months);
    void annulOperation(int accid, int id);
    Map<Integer, Double> getOperationsHistory(int accid);
    void setSuspiciousConditions(Bank bank, double limit);
    ArrayList<String> getNotifications(Client client);
    Client findClient(String phone);
    Bank findBank(String name);
}
