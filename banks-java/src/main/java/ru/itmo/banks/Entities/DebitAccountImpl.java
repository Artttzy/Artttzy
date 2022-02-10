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

    @Override
    public void Withdraw(double sum) {

    }

    @Override
    public void Deposit(double sum) {

    }

    @Override
    public void AnnulTransaction(int id) {

    }
}
