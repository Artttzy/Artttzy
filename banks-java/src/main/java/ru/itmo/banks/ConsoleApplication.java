package ru.itmo.banks;

import ru.itmo.banks.Entities.Bank;
import ru.itmo.banks.Exceptions.LackOfFundsCentralBankServiceException;
import ru.itmo.banks.Interfaces.BankAccount;
import ru.itmo.banks.Interfaces.CentralBankService;
import ru.itmo.banks.Service.CentralBankServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApplication {
    private static CentralBankService _centralBank;
    private static Scanner scanner = new Scanner(System.in);

    public static void registrateBank() {
        System.out.println("Введите название банка.");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Введите адрес банка.");
        String address = scanner.nextLine();
        _centralBank.registrateBank(name, address);
    }

    public static void setBankConditions() {
        System.out.println("Введите название банка, в котором вы бы хотели изменить условия.");
        scanner.nextLine();
        String bankName = scanner.nextLine();
        var bank = _centralBank.findBank(bankName);
        System.out.println("Вы хотите изменить дебетовые условия?");
        String ans = scanner.next();
        if (ans.equals("Да")) {
            System.out.println("Введите процент на остаток.");
            double percent = scanner.nextDouble() / 100;
            _centralBank.setDebitConditions(bank, percent);
        }

        System.out.println("Вы хотите изменить депозитные условия?");
        ans = scanner.next();
        if (ans.equals("Да")) {
            System.out.println("Вводите начальные суммы и процент, начисляемый на счет для суммы. Чтобы закончить, введите два 0.");
            double sum;
            double percent;
            Map<Double, Double> depositConditions = new HashMap<>();
            sum = scanner.nextDouble();
            percent = scanner.nextDouble();
            while (percent != 0 && sum != 0) {
                depositConditions.put(sum, percent / 100);
                sum = scanner.nextDouble();
                percent = scanner.nextDouble();
            }

            _centralBank.setDepositConditions(bank, depositConditions);
        }

        System.out.println("Вы хотите изменить кредитные условия?");
        ans = scanner.next();
        if (ans.equals("Да")) {
            System.out.println("Введите кредитную комиссию.");
            double comission = scanner.nextDouble() / 100;
            _centralBank.setDebitConditions(bank, comission);
        }
    }

    public static void registrateClient() {
        System.out.println("Введите имя клиента.");
        String name = scanner.next();
        System.out.println("Введите фамилию клиента.");
        String surname = scanner.next();
        System.out.println("Введите номер телефона.");
        String phone = scanner.next();
        _centralBank.registrateClient(name, surname, phone);
    }

    public static void addClientInformation() {
        System.out.println("Введите номер телефона.");
        String phone = scanner.next();
        var client = _centralBank.findClient(phone);
        if (client == null) throw new RuntimeException("Пользователь не найден!");
        System.out.println("Хотите добавить адрес клиента?");
        String ans = scanner.next();
        if (ans.equals("Да")) {
            System.out.println("Введите адрес клиента.");
            scanner.nextLine();
            String address = scanner.nextLine();
            _centralBank.addClientAdress(client, address);
        }

        System.out.println("Хотите добавить паспорт клиента?");
        ans = scanner.next();
        if (ans.equals("Да")) {
            System.out.println("Введите паспорт клиента.");
            String passport = scanner.next();
            _centralBank.addClientPassport(client, passport);
        }
    }

    public static void getBanksList() {
        for (Bank bank : _centralBank.get_banks()) {
            System.out.println(bank.get_id() + " " + bank.get_name() + " " + bank.get_address());
        }
    }

    public static void getClientsAccounts() {
        System.out.println("Введите номер телефона.");
        String phone = scanner.next();
        var client = _centralBank.findClient(phone);
        if (client == null) throw new RuntimeException("Пользователь не найден!");
        for (BankAccount acc : client.get_accounts()) {
            System.out.println("Id счета: " + acc.get_id() + " Тип счета: " + acc.get_type() + " Банк счета: " + acc.get_bank().get_name() +
                    " Состояние счета: " + acc.get_funds());
        }
    }

    public static void openDebitAccount() {
        System.out.println("Введите номер телефона.");
        String phone = scanner.next();
        var client = _centralBank.findClient(phone);
        System.out.println("Введите название банка, в котором вы бы хотели открыть счет.");
        String bankName = scanner.next();
        var bank = _centralBank.findBank(bankName);
        _centralBank.openDebitAccount(bank, client);
    }

    public static void openDepositAccount() {
        System.out.println("Введите номер телефона.");
        String phone = scanner.next();
        var client = _centralBank.findClient(phone);
        System.out.println("Введите название банка, в котором вы бы хотели открыть счет.");
        String bankName = scanner.next();
        var bank = _centralBank.findBank(bankName);
        System.out.println("Укажите начальную сумму, с которой вы бы хотели открыть счет.");
        double sum = scanner.nextDouble();
        System.out.println("Укажите срок, на который вы хотите открыть счет, в месяцах.");
        int months = scanner.nextInt();
        _centralBank.openDepositAccount(bank, client, sum, months);
    }

    public static void openCreditAccount() {
        System.out.println("Введите номер телефона.");
        String phone = scanner.next();
        var client = _centralBank.findClient(phone);
        System.out.println("Введите название банка, в котором вы бы хотели открыть счет.");
        String bankName = scanner.next();
        var bank = _centralBank.findBank(bankName);
        System.out.println("Укажите лимит, с которым вы бы хотели открыть счет.");
        double limit = scanner.nextDouble();
        _centralBank.openCreditAccount(bank, client, limit);
    }

    public static void withdrawFunds() throws LackOfFundsCentralBankServiceException {
        System.out.println("Введите id счета, с которого вы бы хотели вывести средства.");
        int id = scanner.nextInt();
        System.out.println("Введите сумму, которую вы бы хотели вывести.");
        double sum = scanner.nextDouble();
        _centralBank.withdraw(id, sum);
    }

    public static void depositFunds() {
        System.out.println("Введите id счета, на который вы бы хотели внести средства.");
        int id = scanner.nextInt();
        System.out.println("Введите сумму, которую вы бы хотели внести.");
        double sum = scanner.nextDouble();
        _centralBank.deposit(id, sum);
    }

    public static void transferFunds() throws LackOfFundsCentralBankServiceException {
        System.out.println("Введите id счета, с которого вы бы хотели перевести средства.");
        int id1 = scanner.nextInt();
        System.out.println("Введите id счета, на который вы бы хотели перевести средства.");
        int id2 = scanner.nextInt();
        System.out.println("Введите сумму, которую вы бы хотели перевести.");
        double sum = scanner.nextDouble();
        _centralBank.transfer(id1, id2, sum);
    }

    public static void getOperationsHistory() {
        System.out.println("Введите id счета, историю операций которого вы бы хотели узнать.");
        int id = scanner.nextInt();
        var operationsHistory = _centralBank.getOperationsHistory(id);
        for (int opId : operationsHistory.keySet()) {
            String type;
            if (opId % 2 == 0) {
                type = "Пополнение";
            } else {
                type = "Снятие";
            }

            System.out.println("Id операции: " + opId + " Вид операции: " + type + " Сумма: " + operationsHistory.get(opId));
        }
    }

    public static void annulOperation() {
        System.out.println("Введите id счета, на котором нужно отменить операцию.");
        int id = scanner.nextInt();
        System.out.println("Введите id операции, которую вы хотите отменить.");
        int opId = scanner.nextInt();
        _centralBank.annulOperation(id, opId);
    }

    public static void rewindTime() {
        System.out.println("Введите количество месяцев.");
        int months = scanner.nextInt();
        _centralBank.rewindTime(months);
    }

    public static void getNotifications() {
        System.out.println("Введите номер телефона.");
        String phone = scanner.next();
        var client = _centralBank.findClient(phone);
        ArrayList<String> notifications = _centralBank.getNotifications(client);
        for (int i = 1; i <= notifications.size(); i++) {
            System.out.println(i + " " + notifications.get(i - 1));
        }
    }

    public static void main(String[] args) throws LackOfFundsCentralBankServiceException {
        _centralBank = new CentralBankServiceImpl();
        int select = 0;
        while (select != 17) {
            System.out.println(
                    "Выберите опцию: 1 - Зарегистрировать банк; 2 - Установить условия счетов в банке; 3 - Зарегистрировать клиента; 4 - Добавить информацию по клиенту; \n" +
                            "5 - Посмотреть список банков; 6 - Посмотреть список счетов клиента; 7 - Открыть дебетовый счет; \n" +
                            "8 - Открыть депозитный счет; 9 - Открыть кредитный счет; 10 - Вывести средства; 11 - Внести средства; \n" +
                            "12 - Перевести средства; 13 - Получить историю операций по счету; 14 - Отменить операцию; \n" +
                            "15 - Перемотать время; 16 - Посмотреть уведомления пользователя; 17 - Выход");
            select = (int) scanner.nextInt();
            switch (select) {
                case 1:
                    registrateBank();
                    break;
                case 2:
                    setBankConditions();
                    break;
                case 3:
                    registrateClient();
                    break;
                case 4:
                    addClientInformation();
                    break;
                case 5:
                    getBanksList();
                    break;
                case 6:
                    getClientsAccounts();
                    break;
                case 7:
                    openDebitAccount();
                    break;
                case 8:
                    openDepositAccount();
                    break;
                case 9:
                    openCreditAccount();
                    break;
                case 10:
                    withdrawFunds();
                    break;
                case 11:
                    depositFunds();
                    break;
                case 12:
                    transferFunds();
                    break;
                case 13:
                    getOperationsHistory();
                    break;
                case 14:
                    annulOperation();
                    break;
                case 15:
                    rewindTime();
                    break;
                case 16:
                    getNotifications();
                    break;
                case 17:
                    break;
            }
        }
    }
}
