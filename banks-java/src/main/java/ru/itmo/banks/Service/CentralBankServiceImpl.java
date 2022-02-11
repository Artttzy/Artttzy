package ru.itmo.banks.Service;

import ru.itmo.banks.Entities.*;
import ru.itmo.banks.Interfaces.BankAccount;
import ru.itmo.banks.Interfaces.CentralBankService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CentralBankServiceImpl implements CentralBankService {
    private int lastid = 0;
    private ArrayList<Bank> _banks = new ArrayList<Bank>();
    private ArrayList<Client> _clients = new ArrayList<Client>();
    private ArrayList<DebitAccountImpl> _debitAccounts = new ArrayList<DebitAccountImpl>();
    private ArrayList<DepositAccountImpl> _depositAccounts = new ArrayList<DepositAccountImpl>();
    private ArrayList<CreditAccountImpl> _creditAccounts = new ArrayList<CreditAccountImpl>();
    private ArrayList<BankAccount> _accounts = new ArrayList<BankAccount>();

    public ArrayList<Bank> get_banks() {
        return _banks;
    }

    public Bank RegistrateBank(String name, String address)
    {
        var bank = new Bank(lastid++, name, address);
        _banks.add(bank);
        return bank;
    }

    public void SetDepositConditions(Bank bank, Map<Double, Double> percents)
    {
        bank.SetDepositConditions(percents);
    }

    public void SetDebitConditions(Bank bank, double percent)
    {
        bank.SetDebitConditions(percent);
    }

    public void SetCreditConditions(Bank bank, double commission)
    {
        bank.SetCreditConditions(commission);
    }

    public void SetSuspiciousConditions(Bank bank, double limit)
    {
        bank.SetSuspiciousConditions(limit);
    }

    public Client RegistrateClient(String name, String surname, String phone)
    {
        var client = new Client(name, surname, phone);
        _clients.add(client);
        return client;
    }
    public void AddClientAdress(Client client, String address)
    {
        client.AddAddress(address);
    }

    public void AddClientPassport(Client client, String passport)
    {
        client.AddPassport(passport);
    }

    public void OpenDebitAccount(Bank bank, Client client)
    {
        var acc = new DebitAccountImpl(_accounts.size() + 1, bank.get_debitPercent());
        _debitAccounts.add(acc);
        _accounts.add(acc);
        bank.AddDebitAccount(acc);
        bank.AddClient(client);
        client.AddAccount(acc);
        acc.set_bank(bank);
        acc.set_client(client);
    }

    public void OpenDepositAccount(Bank bank, Client client, double sum, int months)
    {
        double percent = 0;
        for (Double key: bank.get_depositPercentDictionary().keySet()) {
            if (key > sum) {
                percent = bank.get_depositPercentDictionary().get(key);
                break;
            }
        }
        var acc = new DepositAccountImpl(_accounts.size() + 1, sum, percent, months);
        _depositAccounts.add(acc);
        _accounts.add(acc);
        bank.AddDepositAccount(acc);
        bank.AddClient(client);
        client.AddAccount(acc);
        acc.set_bank(bank);
        acc.set_client(client);
    }

    public void OpenCreditAccount(Bank bank, Client client, double limit)
    {
        var acc = new CreditAccountImpl(_accounts.size() + 1, limit, bank.get_creditCommission());
        _creditAccounts.add(acc);
        _accounts.add(acc);
        bank.AddCreditAccount(acc);
        bank.AddClient(client);
        client.AddAccount(acc);
        acc.set_bank(bank);
        acc.set_client(client);
    }

    public void Withdraw(int accid, double sum)
    {
        for (var acc : _accounts) {
            if (acc.get_id() == accid) {
                CheckSuspicion(acc, sum);
                acc.Withdraw(sum);
                break;
            }
        }
    }

    public void Deposit(int accid, double sum)
    {
        for (var acc : _accounts) {
            if (acc.get_id() == accid) {
                acc.Deposit(sum);
                break;
            }
        }
    }

    public void Transfer(int accid1,  int accid2, double sum)
    {
        for (var acc1 : _accounts) {
            if (acc1.get_id() == accid1) {
                CheckSuspicion(acc1, sum);
                acc1.Withdraw(sum);
                break;
            }
        }
        for (var acc2 : _accounts) {
            if (acc2.get_id() == accid2) {
                acc2.Deposit(sum);
                break;
            }
        }
    }

    public Map<Integer, Double> GetOperationsHistory(int accid)
    {
        Map<Integer, Double> map = new HashMap<>();
        for (var acc : _accounts) {
            if (acc.get_id() == accid) {
                map = acc.get_operationsHistory();
                break;
            }
        }
        return map;
    }

    public void AnnulOperation(int accid, int id)
    {
        for (var acc : _accounts) {
            if (acc.get_id() == accid) {
                acc.AnnulTransaction(id);
                break;
            }
        }
    }

    public void RewindTime(int months)
    {
        for (int i = 0; i < months; i++)
        {
            for (DebitAccountImpl debitAccount : _debitAccounts)
            {
                debitAccount.set_funds(debitAccount.get_funds() + (debitAccount.get_funds() * debitAccount.get_percent()));
            }

            for (DepositAccountImpl depositAccount : _depositAccounts)
            {
                depositAccount.set_funds(depositAccount.get_funds() + (depositAccount.get_funds() * depositAccount.get_percent()));
                depositAccount.set_endMonths(depositAccount.get_endMonths() - 1);
            }
        }
    }

    public void CheckSuspicion(BankAccount acc, double sum)
    {
        if (acc.get_client().CheckSuspicion() == true)
        {
            if (acc.get_bank().get_suspiciousLimit() < sum)
            {
                throw new RuntimeException("Аккаунт подозрительный и сумма вывода выше лимита!");
            }
        }
    }

    public ArrayList<String> GetNotifications(Client client)
    {
        return client.get_notifications();
    }

    public Client FindClient(String phone)
    {
        Client client = new Client();
        for (Client _client : _clients) {
            if (phone == _client.get_phone()) {
                client = _client;
            }
        }
        if (client != null)
        {
            return client;
        }
        else
        {
            throw new RuntimeException("Клиент не найден!");
        }
    }

    public Bank FindBank(String name)
    {
        Bank bank = new Bank();
        for (Bank _bank : _banks) {
            if (name == _bank.get_name()) {
                bank = _bank;
            }
        }
        if (bank != null)
        {
            return bank;
        }
        else
        {
            throw new RuntimeException("Банк не найден!");
        }
    }
}
