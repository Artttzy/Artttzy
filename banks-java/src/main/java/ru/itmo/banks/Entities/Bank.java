package ru.itmo.banks.Entities;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private int id;
    private String name;
    private String address;
    private double debitPercent;
    private double creditCommission;
    private double suspiciousLimit;
    private Map<Double,Double> depositPercentDictionary = new HashMap<>();
    private ArrayList<DepositAccountImpl> depositAccounts = new ArrayList<>();
    private ArrayList<DebitAccountImpl> debitAccounts = new ArrayList<>();
    private ArrayList<CreditAccountImpl> creditAccounts = new ArrayList<>();
    private ArrayList<Client> _clients = new ArrayList<>();

    public Bank() {}

    public Bank(int id, String name, String address)
    {
        this.id = id;
        this.name = name;
        this.address = address;
    }
    public int get_id() {
        return id;
    }

    public String get_name() {
        return name;
    }

    public String get_address() {
        return address;
    }

    public double get_creditCommission() {
        return creditCommission;
    }

    public double get_debitPercent() {
        return debitPercent;
    }

    public double get_suspiciousLimit() {
        return suspiciousLimit;
    }

    public Map<Double, Double> get_depositPercentDictionary() {
        return depositPercentDictionary;
    }

    public void addClient(Client client)
    {
        _clients.add(client);
    }
    public void addDebitAccount(DebitAccountImpl acc)
    {
        debitAccounts.add(acc);
    }

    public void addDepositAccount(DepositAccountImpl acc)
    {
        depositAccounts.add(acc);
    }

    public void addCreditAccount(CreditAccountImpl acc)
    {
        creditAccounts.add(acc);
    }

    public void setDepositConditions(Map<Double, Double> percents)
    {
        depositPercentDictionary = percents;
        for (Client client : _clients)
        {
            client.addNotification("Deposit conditions has updated!");
        }
    }

    public void setDebitConditions(double percent)
    {
        debitPercent = percent;
        for (Client client : _clients)
        {
            client.addNotification("Debit conditions has updated!");
        }
    }

    public void setCreditConditions(double commission)
    {
        creditCommission = commission;
        for (Client client : _clients)
        {
            client.addNotification("Credit conditions has updated!");
        }
    }

    public void setSuspiciousConditions(double limit)
    {
        suspiciousLimit = limit;
        for (Client client : _clients)
        {
            client.addNotification("Suspicious conditions has updated!");
        }
    }
}
