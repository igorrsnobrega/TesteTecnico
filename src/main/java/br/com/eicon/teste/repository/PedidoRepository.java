package br.com.eicon.teste.repository;

import br.com.eicon.teste.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Pedido findByNumeroControle(String numeroControle);

    List<Pedido> findByDataCadastro(LocalDate dataCadastro);

    List<Pedido> findByCodigoCliente(Long codigoCliente);

}
