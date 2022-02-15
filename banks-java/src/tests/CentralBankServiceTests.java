import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.itmo.banks.Exceptions.CentralBankServiceException;
import ru.itmo.banks.Exceptions.LackOfFundsCentralBankServiceException;
import ru.itmo.banks.Exceptions.SuspiciousClientCentralBankServiceException;
import ru.itmo.banks.Interfaces.CentralBankService;
import ru.itmo.banks.Service.CentralBankServiceImpl;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CentralBankServiceTests {
    private CentralBankService centralBankService;

    @Before
    public void Setup() {
        centralBankService = new CentralBankServiceImpl();
        centralBankService.registrateBank("Тинькофф", "Гетто 66");
        centralBankService.setDebitConditions(centralBankService.findBank("Тинькофф"), 0.05);
        centralBankService.setCreditConditions(centralBankService.findBank("Тинькофф"), 0.1);
        var depositConditions = new HashMap<Double, Double>();
        depositConditions.put(50000.0, 0.03);
        depositConditions.put(100000.0, 0.05);
        depositConditions.put(200000.0, 0.07);
        centralBankService.setDepositConditions(centralBankService.findBank("Тинькофф"), depositConditions);
        centralBankService.setSuspiciousConditions(centralBankService.findBank("Тинькофф"), 100000);
        centralBankService.registrateClient("Артем", "Васильев", "88005553535");
        centralBankService.openDebitAccount(centralBankService.findBank("Тинькофф"), centralBankService.findClient("88005553535"));
        centralBankService.openDepositAccount(centralBankService.findBank("Тинькофф"), centralBankService.findClient("88005553535"), 70000, 6);
        centralBankService.openCreditAccount(centralBankService.findBank("Тинькофф"), centralBankService.findClient("88005553535"), 50000);
    }

    @Test (expected = LackOfFundsCentralBankServiceException.class)
    public void WithdrawFundsWithLackOfFundsAndWithDepositTermNotOver_CatchExceptions() throws LackOfFundsCentralBankServiceException
    {
        centralBankService.deposit(centralBankService.findClient("88005553535").get_accounts().get(0).get_id(), 50000);
        centralBankService.withdraw(centralBankService.findClient("88005553535").get_accounts().get(0).get_id(), 80000);
    }

    @Test
    public void SetBankConditions_GetNotification()
    {
        centralBankService.setDebitConditions(centralBankService.findBank("Тинькофф"), 0.07);
        assertEquals(centralBankService.getNotifications(centralBankService
                .findClient("88005553535")).get(centralBankService.getNotifications(centralBankService
                .findClient("88005553535")).size() - 1), "Debit conditions has updated!");
    }

    @Test (expected = CentralBankServiceException.class)
    public void WithdrawOnSuspiciousAccMoreThanLimitAllows_CatchException() throws CentralBankServiceException {
        centralBankService.deposit(centralBankService.findClient("88005553535").get_accounts().get(0).get_id(), 200000);
        centralBankService.withdraw(centralBankService.findClient("88005553535").get_accounts().get(0).get_id(), 150000);
        centralBankService.addClientAdress(centralBankService.findClient("88005553535"), "Гетто 5");
        centralBankService.addClientPassport(centralBankService.findClient("88005553535"), "4444444444");
        centralBankService.withdraw(centralBankService.findClient("88005553535").get_accounts().get(0).get_id(), 150000);
        assertEquals(centralBankService.findClient("88005553535").get_accounts().get(0).get_funds(), 50000);
    }

    @Test
    public void RewindTime_DepositAndDebitFundsWillRaiseAndDepositAccAllowsToWithdraw() throws LackOfFundsCentralBankServiceException {
        centralBankService.deposit(centralBankService.findClient("88005553535").get_accounts().get(0).get_id(), 50000);
        centralBankService.rewindTime(10);
        assertEquals((int)centralBankService.findClient("88005553535").get_accounts().get(0).get_funds(), 81444);
        centralBankService.withdraw(centralBankService.findClient("88005553535").get_accounts().get(1).get_id(), 50000);
    }
}
