package ru.itmo.banks.Exceptions;

public class ClientNotFoundCentralBankServiceException extends CentralBankServiceException {
    public ClientNotFoundCentralBankServiceException(String message) {super(message);}
}
