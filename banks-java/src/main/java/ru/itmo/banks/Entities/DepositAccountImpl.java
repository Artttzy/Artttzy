package ru.itmo.banks.Entities;

import ru.itmo.banks.Exceptions.CentralBankServiceException;
import ru.itmo.banks.Exceptions.DepositAccountNotClosedCentralBankServiceException;
import ru.itmo.banks.Exceptions.LackOfFundsCentralBankServiceException;
import ru.itmo.banks.Interfaces.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class DepositAccountImpl implements BankAccount {
    private int id;
    private int endMonths;
    private double funds = 0;
    private double percent;
    private Bank bank;
    private Client client;
    private String type;
    private Map<Integer, Double> operationsHistory = new HashMap<>();

    public DepositAccountImpl(int id, double firstSum, double percent, int months)
    {
        this.id = id;
        this.funds += firstSum;
        this.percent = percent;
        this.endMonths = months;
        this.type = "Депозитный счет";
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

    public int get_endMonths() {return endMonths;}

    public void set_endMonths(int _endMonths) {this.endMonths = _endMonths;}

    public void withdraw(double sum) {
        if (endMonths > 0)
        {
            try {
                throw new DepositAccountNotClosedCentralBankServiceException("Невозможно вывести средства! Срок депозитного счета еще не подошел к концу!");
            } catch (DepositAccountNotClosedCentralBankServiceException e) {
                e.printStackTrace();
            }
        }
        else
        {
            if (funds >= sum)
            {
                funds -= sum;
                operationsHistory.put((operationsHistory.size() * 2) + 1, sum);
            }
            else
            {
                try {
                    throw new LackOfFundsCentralBankServiceException("Недостаточно средств!");
                } catch (LackOfFundsCentralBankServiceException e) {
                    e.printStackTrace();
                }
            }
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
