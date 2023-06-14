package br.com.eicon.teste.service;

import br.com.eicon.teste.domain.Cliente;
import br.com.eicon.teste.domain.Pedido;
import br.com.eicon.teste.exception.PedidoExistsException;
import br.com.eicon.teste.exception.PedidoLimitException;
import br.com.eicon.teste.exception.PedidoNotFoundException;
import br.com.eicon.teste.exception.PedidoNumeroControleDuplicatedException;
import br.com.eicon.teste.record.PedidoRecord;
import br.com.eicon.teste.repository.PedidoRepository;
import org.springframework.beans.BeanUtils;
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

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private static final int MAX_PEDIDOS = 10;

    public PedidoService(PedidoRepository pedidoRepository, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
    }

    public Pedido findPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(PedidoNotFoundException::new);
    }

    public Pedido findPedidoByNumeroControle(String numeroControle) {
        return Optional.ofNullable(pedidoRepository.findByNumeroControle(numeroControle))
                .orElseThrow(PedidoNotFoundException::new);
    }

    public List<Pedido> findPedidoByDataCadastro(LocalDate dataCadastro) {
        return Optional.ofNullable(pedidoRepository.findByDataCadastro(dataCadastro))
                .orElseThrow(PedidoNotFoundException::new);
    }

    public List<Pedido> findAllPedidos() {
        return pedidoRepository.findAll();
    }

    public void validaListaPedidos(List<PedidoRecord> listaPedidos) {
        if (listaPedidos.size() > MAX_PEDIDOS) {
            throw new PedidoLimitException();
        }

        Set<String> listaNumerosControle = new HashSet<>();

        for (PedidoRecord record : listaPedidos) {
            String numeroControle = record.numeroControle();

            if (pedidoRepository.findByNumeroControle(numeroControle) != null) {
                throw new PedidoExistsException(numeroControle);
            }

            if (listaNumerosControle.contains(numeroControle)) {
                throw new PedidoNumeroControleDuplicatedException(numeroControle);
            } else {
                listaNumerosControle.add(numeroControle);
            }
        }
    }

    public void savePedido(List<PedidoRecord> listaPedido) {
        validaListaPedidos(listaPedido);

        listaPedido.forEach(pedidoRecord -> {
            Pedido novoPedido = createPedidoFromRecord(pedidoRecord);
            pedidoRepository.save(novoPedido);
        });
    }

    private Pedido createPedidoFromRecord(PedidoRecord pedidoRecord) {
        Pedido novoPedido = new Pedido();

        Cliente clienteEncontrado = clienteService.findClienteById(pedidoRecord.codigoCliente());
        novoPedido.setCliente(clienteEncontrado);
        novoPedido.setDataCadastro(pedidoRecord.dataCadastro());
        novoPedido.setValor(pedidoRecord.valor());
        novoPedido.setDescricao(pedidoRecord.descricao());
        novoPedido.setQuantidade(pedidoRecord.quantidade());
        novoPedido.setNumeroControle(pedidoRecord.numeroControle());

        BigDecimal valorDesconto = getValorDesconto(novoPedido);

        valorDesconto = valorDesconto.setScale(2, RoundingMode.HALF_UP);

        novoPedido.setValorDesconto(valorDesconto);

        return novoPedido;
    }

    private static BigDecimal getValorDesconto(Pedido novoPedido) {
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
        return valorDesconto;
    }

    public Pedido updatePedido(Pedido pedido, Long id) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(PedidoNotFoundException::new);

        BeanUtils.copyProperties(pedido, pedidoExistente, "id", "dataCadastro");

        Cliente cliente = pedido.getCliente();
        if (cliente != null && !cliente.equals(pedidoExistente.getCliente())) {
            Cliente clienteEncontrado = clienteService.findClienteById(cliente.getId());
            pedidoExistente.setCliente(clienteEncontrado);
        }

        return pedidoRepository.save(pedidoExistente);
    }

    public void deletePedido(Long id) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(PedidoNotFoundException::new);

        pedidoRepository.delete(pedidoExistente);
    }
}
