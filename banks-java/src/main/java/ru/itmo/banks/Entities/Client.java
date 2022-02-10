package ru.itmo.banks.Entities;

import ru.itmo.banks.Interfaces.BankAccount;
import java.util.ArrayList;

public class Client {
    private String _name;
    private String _surname;
    private String _address;
    private String _passport;
    private String _phone;
    private ArrayList<BankAccount> _accounts = new ArrayList<>();
    private ArrayList<String> _notifications = new ArrayList<>();

    public Client(String name, String surname, String phone)
    {
        _name = name;
        _surname = surname;
        _phone = phone;
    }

    public ArrayList<String> get_notifications() {
        return _notifications;
    }

    public String get_name() {
        return _name;
    }

    public String get_surname() {
        return _surname;
    }

    public String get_passport() {
        return _passport;
    }

    public String get_phone() {
        return _phone;
    }

    public void AddAddress(String address)
    {
        _address = address;
    }

    public void AddPassport(String passport)
    {
        if (passport.length() != 10)
        {
        }

        _passport = passport;
    }

    public void AddAccount(BankAccount account)
    {
        _accounts.add(account);
    }

    public void AddNotification(String notification)
    {
        _notifications.add(notification);
    }

    public boolean CheckSuspicion()
    {
        if (_address == null || _passport == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
