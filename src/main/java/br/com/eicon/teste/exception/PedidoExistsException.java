package br.com.eicon.teste.exception;

public class PedidoExistsException extends RuntimeException {

    public PedidoExistsException(String numeroControle) {
        super(String.format(
                "Já existe um pedido cadastrado com o número de controle: %s", numeroControle));
    }
}
