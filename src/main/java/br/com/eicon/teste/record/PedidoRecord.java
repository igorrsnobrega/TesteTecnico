package br.com.eicon.teste.record;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoRecord(String numeroControle,
                           LocalDate dataCadastro,
                           String descricao,
                           BigDecimal valor,
                           BigDecimal quantidade,
                           Long codigoCliente) {

}
