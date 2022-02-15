package ru.itmo.banks.Exceptions;

public class SuspiciousClientCentralBankServiceException extends CentralBankServiceException{
    public SuspiciousClientCentralBankServiceException(String message) {super(message);}
}
