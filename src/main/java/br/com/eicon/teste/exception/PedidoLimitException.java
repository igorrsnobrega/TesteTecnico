package br.com.eicon.teste.exception;

public class PedidoLimitException extends RuntimeException{

    public PedidoLimitException() {
        super("Limite máximo de 10 pedidos excedido");
    }
}
