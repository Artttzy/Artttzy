package ru.itmo.banks.Exceptions;

public class BankNotFoundCentralBankServiceException extends CentralBankServiceException {
    public BankNotFoundCentralBankServiceException(String message) {super(message);}
}
