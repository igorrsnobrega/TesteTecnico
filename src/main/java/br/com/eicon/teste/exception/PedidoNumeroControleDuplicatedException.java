package br.com.eicon.teste.exception;

public class PedidoNumeroControleDuplicatedException extends RuntimeException{

    public PedidoNumeroControleDuplicatedException(String numeroControle) {
        super(String.format(
                "O número de controle %s está duplicado", numeroControle));
    }
}
