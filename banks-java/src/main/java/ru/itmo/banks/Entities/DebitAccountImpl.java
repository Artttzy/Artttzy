package ru.itmo.banks.Entities;

import ru.itmo.banks.Interfaces.BankAccount;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DebitAccountImpl implements BankAccount {
    private Date _dateTime;
    private int _id;
    private double _funds = 0;
    private double _percent;
    private Bank _bank;
    private Client _client;
    private String _type;
    private Map<Integer, Double> _operationsHistory = new HashMap<>();

    public DebitAccountImpl(int id, double percent)
    {
        _id = id;
        _percent = percent;
        _dateTime = new Date();
        _type = "Дебетовый счет";
    }

    public int get_id() {
        return _id;
    }

    public String get_type() {
        return _type;
    }

    public Map<Integer, Double> get_operationsHistory() {
        return _operationsHistory;
    }

    public Bank get_bank() {
        return _bank;
    }

    public void set_bank(Bank _bank) {
        this._bank = _bank;
    }

    public Client get_client() {
        return _client;
    }

    public void set_client(Client _client) {
        this._client = _client;
    }

    public double get_funds() {
        return _funds;
    }

    public void set_funds(double _funds) {
        this._funds = _funds;
    }

    public double get_percent() {return _percent;}

    public void Withdraw(double sum) {
        if (_funds >= sum)
        {
            _funds -= sum;
            _operationsHistory.put((_operationsHistory.size() * 2) + 1, sum);
        }
        else
        {
        }
    }

    public void Deposit(double sum) {
        _funds += sum;
        _operationsHistory.put(_operationsHistory.size() * 2, sum);
    }

    public void AnnulTransaction(int id) {
        int idd = -1;
        for (Integer key: _operationsHistory.keySet()) {
            if (key == id) {
                idd = key;
                break;
            }
        }
        if (idd % 2 == 0 && idd != -1)
        {
            _funds -= _operationsHistory.get(idd);
            _operationsHistory.put((_operationsHistory.size() * 2) + 1, _operationsHistory.get(idd));
        }
        else if(idd != -1)
        {
            _funds += _operationsHistory.get(idd);
            _operationsHistory.put(_operationsHistory.size() * 2, _operationsHistory.get(idd));
        }
    }
}
