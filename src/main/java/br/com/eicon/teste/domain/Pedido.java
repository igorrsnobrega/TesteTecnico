package br.com.eicon.teste.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor(force = true)
@Data
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(name = "numero_controle")
    private String numeroControle;
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro = LocalDate.now();
    @NonNull
    @Column(name = "descricao")
    private String descricao;
    @NonNull
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;
    @Column(name = "quantidade")
    private BigDecimal quantidade = BigDecimal.ONE;
    @Column(name = "codigo_cliente")
    private Long codigoCliente;

}
