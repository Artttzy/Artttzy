package ru.itmo.banks.Exceptions;

public class LackOfFundsCentralBankServiceException extends CentralBankServiceException{
    public LackOfFundsCentralBankServiceException(String message) {super(message);}
}
