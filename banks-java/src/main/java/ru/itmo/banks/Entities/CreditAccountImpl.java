package ru.itmo.banks.Entities;

import ru.itmo.banks.Interfaces.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class CreditAccountImpl implements BankAccount {
    private int id;
    private double limit;
    private double commission;
    private double funds = 0;
    private Bank bank;
    private Client client;
    private String type;
    private Map<Integer, Double> operationsHistory = new HashMap<>();

    public CreditAccountImpl(int id, double limit, double commission)
    {
        this.id = id;
        this.limit = limit;
        this.commission = commission;
        this.type = "Кредитный счет";
    }

    public int get_id() {
        return id;
    }

    public String get_type() {
        return type;
    }

    public Map<Integer, Double> get_operationsHistory() {
        return operationsHistory;
    }

    public Bank get_bank() {
        return bank;
    }

    public void set_bank(Bank _bank) {
        this.bank = _bank;
    }

    public Client get_client() {
        return client;
    }

    public void set_client(Client _client) {
        this.client = _client;
    }

    public double get_funds() {
        return funds;
    }

    public void set_funds(double _funds) {
        this.funds = _funds;
    }

    public void withdraw(double sum) {
        if (funds < limit)
        {
            funds -= sum * (1 + commission);
            operationsHistory.put((operationsHistory.size() * 2) + 1, sum * (1 + commission));
        }
        else
        {
            funds -= sum;
            operationsHistory.put((operationsHistory.size() * 2) + 1, sum);
        }
    }


    public void deposit(double sum) {
        if (funds < limit)
        {
            funds += sum * (1 - commission);
            operationsHistory.put(operationsHistory.size() * 2, sum * (1 - commission));
        }
        else
        {
            funds += sum;
            operationsHistory.put(operationsHistory.size() * 2, sum);
        }
    }


    public void annulTransaction(int id) {
        int idd = -1;
        for (Integer key: operationsHistory.keySet()) {
            if (key == id) {
                idd = key;
                break;
            }
        }
        if (idd % 2 == 0 && idd != -1)
        {
            funds -= operationsHistory.get(idd);
            operationsHistory.put((operationsHistory.size() * 2) + 1, operationsHistory.get(idd));
        }
        else if(idd != -1)
        {
            funds += operationsHistory.get(idd);
            operationsHistory.put(operationsHistory.size() * 2, operationsHistory.get(idd));
        }
    }
}
