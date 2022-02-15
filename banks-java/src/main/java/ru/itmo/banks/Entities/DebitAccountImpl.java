package ru.itmo.banks.Entities;

import ru.itmo.banks.Exceptions.CentralBankServiceException;
import ru.itmo.banks.Exceptions.LackOfFundsCentralBankServiceException;
import ru.itmo.banks.Interfaces.BankAccount;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DebitAccountImpl implements BankAccount {
    private Date dateTime;
    private int id;
    private double funds = 0;
    private double percent;
    private Bank bank;
    private Client client;
    private String type;
    private Map<Integer, Double> operationsHistory = new HashMap<>();

    public DebitAccountImpl(int id, double percent)
    {
        this.id = id;
        this.percent = percent;
        this.dateTime = new Date();
        this.type = "Дебетовый счет";
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

    public double get_percent() {return percent;}

    public void withdraw(double sum) throws LackOfFundsCentralBankServiceException {
        if (funds >= sum)
        {
            funds -= sum;
            operationsHistory.put((operationsHistory.size() * 2) + 1, sum);
        }
        else
        {
            throw new LackOfFundsCentralBankServiceException("Недостаточно средств!");

        }
    }

    public void deposit(double sum) {
        funds += sum;
        operationsHistory.put(operationsHistory.size() * 2, sum);
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
