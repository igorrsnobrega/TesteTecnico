package br.com.eicon.teste.service;

import br.com.eicon.teste.domain.Pedido;
import br.com.eicon.teste.dto.PedidoDTO;
import br.com.eicon.teste.repository.PedidoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido findPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        "Não foi encontrado o pedido com o id: %s", id)));
    }

    public Pedido findPedidoByNumeroControle(String numeroControle) {
        return Optional.ofNullable(pedidoRepository.findByNumeroControle(numeroControle))
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        "Não foi encontrado o pedido com o número de controle: %s", numeroControle)));
    }

    public List<Pedido> findPedidoByDataCadastro(LocalDate dataCadastro) {
        return Optional.ofNullable(pedidoRepository.findByDataCadastro(dataCadastro))
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        "Não foram encontrados pedidos para a data: %s", dataCadastro)));
    }

    public List<Pedido> findAllPedidos() {
        return pedidoRepository.findAll();
    }

    public void validaListaPedidos(List<PedidoDTO> listaPedidos) {
        if (listaPedidos.size() > 10) {
            throw new IllegalArgumentException("Limite máximo de 10 pedidos excedido");
        }

        Set<String> listaNumerosControle = new HashSet<>();

        for (PedidoDTO dto : listaPedidos) {
            String numeroControle = dto.getNumeroControle();

            Pedido pedidoExistente = pedidoRepository.findByNumeroControle(numeroControle);
            if (pedidoExistente != null) {
                throw new IllegalArgumentException(String.format(
                        "Já existe um pedido cadastrado com o número de controle: %s", numeroControle));
            }

            if (listaNumerosControle.contains(numeroControle)) {
                throw new IllegalArgumentException(String.format(
                        "O número de controle %s está duplicado", numeroControle));
            } else {
                listaNumerosControle.add(numeroControle);
            }
        }
    }



    public void savePedido(List<PedidoDTO> listaPedido) {
        validaListaPedidos(listaPedido);

        listaPedido.stream()
                .map(pedidoDTO -> {
                    Pedido novoPedido = new Pedido();
                    BeanUtils.copyProperties(pedidoDTO, novoPedido);

                    BigDecimal quantidade = Optional.ofNullable(novoPedido.getQuantidade()).orElse(BigDecimal.ONE);
                    BigDecimal valorTotal = novoPedido.getValor().multiply(quantidade);
                    BigDecimal valorDesconto = BigDecimal.ZERO;

                    if (quantidade.compareTo(BigDecimal.valueOf(5)) > 0) {
                        if (quantidade.compareTo(BigDecimal.valueOf(10)) >= 0) {
                            valorDesconto = valorTotal.multiply(BigDecimal.valueOf(0.1));
                        } else {
                            valorDesconto = valorTotal.multiply(BigDecimal.valueOf(0.05));
                        }
                    }

                    valorDesconto = valorDesconto.setScale(2, RoundingMode.HALF_UP);

                    novoPedido.setValorDesconto(valorDesconto);

                    return novoPedido;
                })
                .forEach(pedidoRepository::save);
    }


    public Pedido updatePedido(Pedido pedido) {
        Long id = pedido.getId();
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        "Não foi encontrado o pedido com o id: %d", id)));

        BeanUtils.copyProperties(pedido, pedidoExistente);
        return pedidoRepository.save(pedidoExistente);
    }

    public void deletePedido(Long id) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        "Não foi encontrado o pedido com o id: %d", id)));
        pedidoRepository.delete(pedidoExistente);
    }
}
