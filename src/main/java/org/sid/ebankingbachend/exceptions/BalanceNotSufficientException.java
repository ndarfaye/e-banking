package org.sid.ebankingbachend.exceptions;

public class BalanceNotSufficientException extends Exception{

    public BalanceNotSufficientException(String message){
        super(message);
    }
}
