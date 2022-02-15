package ru.itmo.banks.Exceptions;

public class DepositAccountNotClosedCentralBankServiceException extends CentralBankServiceException{
    public DepositAccountNotClosedCentralBankServiceException(String message) {super(message);}
}
