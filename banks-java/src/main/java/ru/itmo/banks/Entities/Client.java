package ru.itmo.banks.Entities;

import ru.itmo.banks.Exceptions.CentralBankServiceException;
import ru.itmo.banks.Exceptions.IncorrectPassportCentralBankServiceException;
import ru.itmo.banks.Interfaces.BankAccount;
import java.util.ArrayList;

public class Client {
    private String name;
    private String surname;
    private String address;
    private String passport;
    private String phone;
    private ArrayList<BankAccount> accounts = new ArrayList<>();
    private ArrayList<String> notifications = new ArrayList<>();

    public Client() {}

    public Client(String name, String surname, String phone)
    {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public ArrayList<String> get_notifications() {
        return notifications;
    }

    public String get_name() {
        return name;
    }

    public String get_surname() {
        return surname;
    }

    public String get_passport() {
        return passport;
    }

    public String get_phone() {
        return phone;
    }

    public ArrayList<BankAccount> get_accounts() {return accounts;}

    public void addAddress(String address)
    {
        this.address = address;
    }

    public void addPassport(String passport)
    {
        if (passport.length() != 10)
        {
            try {
                throw new IncorrectPassportCentralBankServiceException("Серия и номер паспорта должны вводится 10ю цифрами без пробелов!");
            } catch (IncorrectPassportCentralBankServiceException e) {
                e.printStackTrace();
            }
        }

        this.passport = passport;
    }

    public void addAccount(BankAccount account)
    {
        accounts.add(account);
    }

    public void addNotification(String notification)
    {
        notifications.add(notification);
    }

    public boolean checkSuspicion()
    {
        if (address == null || passport == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
