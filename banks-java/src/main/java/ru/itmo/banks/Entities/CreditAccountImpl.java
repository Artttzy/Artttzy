package ru.itmo.banks.Entities;

import ru.itmo.banks.Interfaces.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class CreditAccountImpl implements BankAccount {
    private int _id;
    private double _limit;
    private double _commission;
    private double _funds = 0;
    private Bank _bank;
    private Client _client;
    private String _type;
    private Map<Integer, Double> _operationsHistory = new HashMap<>();

    public CreditAccountImpl(int id, double limit, double commission)
    {
        _id = id;
        _limit = limit;
        _commission = commission;
        _type = "Кредитный счет";
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

    public void Withdraw(double sum) {
        if (_funds < _limit)
        {
            _funds -= sum * (1 + _commission);
            _operationsHistory.put((_operationsHistory.size() * 2) + 1, sum * (1 + _commission));
        }
        else
        {
            _funds -= sum;
            _operationsHistory.put((_operationsHistory.size() * 2) + 1, sum);
        }
    }


    public void Deposit(double sum) {
        if (_funds < _limit)
        {
            _funds += sum * (1 - _commission);
            _operationsHistory.put(_operationsHistory.size() * 2, sum * (1 - _commission));
        }
        else
        {
            _funds += sum;
            _operationsHistory.put(_operationsHistory.size() * 2, sum);
        }
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
