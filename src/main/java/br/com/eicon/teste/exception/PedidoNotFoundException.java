package br.com.eicon.teste.exception;

public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException() {
        super("O pedido solicitado não foi encontrado!");
    }

}
