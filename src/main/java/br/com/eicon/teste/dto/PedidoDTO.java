package br.com.eicon.teste.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private String numeroControle;
    private LocalDate dataCadastro;
    private String descricao;
    private BigDecimal valor;
    private BigDecimal quantidade;
    private Long codigoCliente;

}
