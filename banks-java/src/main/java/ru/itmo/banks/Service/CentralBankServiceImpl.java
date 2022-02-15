package ru.itmo.banks.Service;

import ru.itmo.banks.Entities.*;
import ru.itmo.banks.Exceptions.*;
import ru.itmo.banks.Interfaces.BankAccount;
import ru.itmo.banks.Interfaces.CentralBankService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CentralBankServiceImpl implements CentralBankService {
    private int lastid = 0;
    private ArrayList<Bank> banks = new ArrayList<Bank>();
    private ArrayList<Client> clients = new ArrayList<Client>();
    private ArrayList<DebitAccountImpl> debitAccounts = new ArrayList<DebitAccountImpl>();
    private ArrayList<DepositAccountImpl> depositAccounts = new ArrayList<DepositAccountImpl>();
    private ArrayList<CreditAccountImpl> creditAccounts = new ArrayList<CreditAccountImpl>();
    private ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();

    public ArrayList<Bank> get_banks() {
        return banks;
    }

    public Bank registrateBank(String name, String address)
    {
        var bank = new Bank(lastid++, name, address);
        banks.add(bank);
        return bank;
    }

    public void setDepositConditions(Bank bank, Map<Double, Double> percents)
    {
        bank.setDepositConditions(percents);
    }

    public void setDebitConditions(Bank bank, double percent)
    {
        bank.setDebitConditions(percent);
    }

    public void setCreditConditions(Bank bank, double commission)
    {
        bank.setCreditConditions(commission);
    }

    public void setSuspiciousConditions(Bank bank, double limit)
    {
        bank.setSuspiciousConditions(limit);
    }

    public Client registrateClient(String name, String surname, String phone)
    {
        var client = new Client(name, surname, phone);
        clients.add(client);
        return client;
    }

    public void addClientAdress(Client client, String address)
    {
        client.addAddress(address);
    }

    public void addClientPassport(Client client, String passport)
    {
        client.addPassport(passport);
    }

    public void openDebitAccount(Bank bank, Client client)
    {
        var acc = new DebitAccountImpl(accounts.size() + 1, bank.get_debitPercent());
        debitAccounts.add(acc);
        accounts.add(acc);
        bank.addDebitAccount(acc);
        bank.addClient(client);
        client.addAccount(acc);
        acc.set_bank(bank);
        acc.set_client(client);
    }

    public void openDepositAccount(Bank bank, Client client, double sum, int months)
    {
        double percent = 0;
        for (Double key: bank.get_depositPercentDictionary().keySet()) {
            if (key > sum) {
                percent = bank.get_depositPercentDictionary().get(key);
                break;
            }
        }
        var acc = new DepositAccountImpl(accounts.size() + 1, sum, percent, months);
        depositAccounts.add(acc);
        accounts.add(acc);
        bank.addDepositAccount(acc);
        bank.addClient(client);
        client.addAccount(acc);
        acc.set_bank(bank);
        acc.set_client(client);
    }

    public void openCreditAccount(Bank bank, Client client, double limit)
    {
        var acc = new CreditAccountImpl(accounts.size() + 1, limit, bank.get_creditCommission());
        creditAccounts.add(acc);
        accounts.add(acc);
        bank.addCreditAccount(acc);
        bank.addClient(client);
        client.addAccount(acc);
        acc.set_bank(bank);
        acc.set_client(client);
    }

    public void withdraw(int accid, double sum) throws LackOfFundsCentralBankServiceException {
        for (var acc : accounts) {
            if (acc.get_id() == accid) {
                checkSuspicion(acc, sum);
                acc.withdraw(sum);
                break;
            }
        }
    }

    public void deposit(int accid, double sum)
    {
        for (var acc : accounts) {
            if (acc.get_id() == accid) {
                acc.deposit(sum);
                break;
            }
        }
    }

    public void transfer(int accid1,  int accid2, double sum) throws LackOfFundsCentralBankServiceException {
        for (var acc1 : accounts) {
            if (acc1.get_id() == accid1) {
                checkSuspicion(acc1, sum);
                acc1.withdraw(sum);
                break;
            }
        }
        for (var acc2 : accounts) {
            if (acc2.get_id() == accid2) {
                acc2.deposit(sum);
                break;
            }
        }
    }

    public Map<Integer, Double> getOperationsHistory(int accid)
    {
        Map<Integer, Double> map = new HashMap<>();
        for (var acc : accounts) {
            if (acc.get_id() == accid) {
                map = acc.get_operationsHistory();
                break;
            }
        }
        return map;
    }

    public void annulOperation(int accid, int id)
    {
        for (var acc : accounts) {
            if (acc.get_id() == accid) {
                acc.annulTransaction(id);
                break;
            }
        }
    }

    public void rewindTime(int months)
    {
        for (int i = 0; i < months; i++)
        {
            for (DebitAccountImpl debitAccount : debitAccounts)
            {
                debitAccount.set_funds(debitAccount.get_funds() + (debitAccount.get_funds() * debitAccount.get_percent()));
            }

            for (DepositAccountImpl depositAccount : depositAccounts)
            {
                depositAccount.set_funds(depositAccount.get_funds() + (depositAccount.get_funds() * depositAccount.get_percent()));
                depositAccount.set_endMonths(depositAccount.get_endMonths() - 1);
            }
        }
    }

    public void checkSuspicion(BankAccount acc, double sum)
    {
        if (acc.get_client().checkSuspicion())
        {
            if (acc.get_bank().get_suspiciousLimit() < sum)
            {
                try {
                    throw new SuspiciousClientCentralBankServiceException("Аккаунт подозрительный и сумма вывода выше лимита!");
                } catch (SuspiciousClientCentralBankServiceException e) {
                    e.getMessage();
                }
            }
        }
    }

    public ArrayList<String> getNotifications(Client client)
    {
        return client.get_notifications();
    }

    public Client findClient(String phone)
    {
        Client client = new Client();
        for (Client _client : clients) {
            if (phone.equals(_client.get_phone())) {
                client = _client;
            }
        }
        if (client.get_name() != null)
        {
            return client;
        }
        else
        {
            try {
                throw new ClientNotFoundCentralBankServiceException("Клиент не найден!");
            } catch (ClientNotFoundCentralBankServiceException e) {
                e.getMessage();
                return null;
            }
        }
    }

    public Bank findBank(String name) {
        Bank bank = new Bank();
        for (Bank _bank : banks) {
            if (name.equals(_bank.get_name())) {
                bank = _bank;
            }
        }
        if (bank.get_name() != null) {
            return bank;
        } else {
            try {
                throw new BankNotFoundCentralBankServiceException("Банк не найден!");
            } catch (BankNotFoundCentralBankServiceException e) {
                e.getMessage();
                return null;
            }
        }
    }
}
