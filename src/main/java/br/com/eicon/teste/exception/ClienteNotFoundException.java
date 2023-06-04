package br.com.eicon.teste.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException() {
        super("O cliente solicitado não foi encontrado!");
    }

}
