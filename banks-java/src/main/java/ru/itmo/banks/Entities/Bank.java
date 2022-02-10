package ru.itmo.banks.Entities;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private int _id;
    private String _name;
    private String _address;
    private double _debitPercent;
    private double _creditCommission;
    private double _suspiciousLimit;
    private Map<Double,Double> _depositPercentDictionary = new HashMap<>();
    private ArrayList<DepositAccountImpl> _depositAccounts = new ArrayList<>();
    private ArrayList<DebitAccountImpl> _debitAccounts = new ArrayList<>();
    private ArrayList<CreditAccountImpl> _creditAccounts = new ArrayList<>();
    private ArrayList<Client> _clients = new ArrayList<>();

    public Bank(int id, String name, String address)
    {
        _id = id;
        _name = name;
        _address = address;
    }
    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_address() {
        return _address;
    }

    public double get_creditCommission() {
        return _creditCommission;
    }

    public double get_debitPercent() {
        return _debitPercent;
    }

    public double get_suspiciousLimit() {
        return _suspiciousLimit;
    }

    public Map<Double, Double> get_depositPercentDictionary() {
        return _depositPercentDictionary;
    }

    public void AddClient(Client client)
    {
        _clients.add(client);
    }
    public void AddDebitAccount(DebitAccountImpl acc)
    {
        _debitAccounts.add(acc);
    }

    public void AddDepositAccount(DepositAccountImpl acc)
    {
        _depositAccounts.add(acc);
    }

    public void AddCreditAccount(CreditAccountImpl acc)
    {
        _creditAccounts.add(acc);
    }

    public void SetDepositConditions(Map<Double, Double> percents)
    {
        _depositPercentDictionary = percents;
        for (Client client : _clients)
        {
            client.AddNotification("Deposit conditions has updated!");
        }
    }

    public void SetDebitConditions(double percent)
    {
        _debitPercent = percent;
        for (Client client : _clients)
        {
            client.AddNotification("Debit conditions has updated!");
        }
    }

    public void SetCreditConditions(double commission)
    {
        _creditCommission = commission;
        for (Client client : _clients)
        {
            client.AddNotification("Credit conditions has updated!");
        }
    }

    public void SetSuspiciousConditions(double limit)
    {
        _suspiciousLimit = limit;
        for (Client client : _clients)
        {
            client.AddNotification("Suspicious conditions has updated!");
        }
    }
}
