package br.com.eicon.teste.service;

import br.com.eicon.teste.domain.Pedido;
import br.com.eicon.teste.exception.PedidoExistsException;
import br.com.eicon.teste.exception.PedidoNotFoundException;
import br.com.eicon.teste.exception.PedidoNumeroControleDuplicatedException;
import br.com.eicon.teste.record.PedidoRecord;
import br.com.eicon.teste.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindPedidoById() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        Pedido result = pedidoService.findPedidoById(id);

        assertEquals(pedido, result);

        verify(pedidoRepository, times(1)).findById(id);
    }

    @Test
    public void testFindPedidoByIdNotFound() {
        Long id = 1L;

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () -> pedidoService.findPedidoById(id));

        verify(pedidoRepository, times(1)).findById(id);
    }

    @Test
    public void testFindPedidoByNumeroControle() {
        String numeroControle = "12345";
        Pedido pedido = new Pedido();
        pedido.setNumeroControle(numeroControle);

        when(pedidoRepository.findByNumeroControle(numeroControle)).thenReturn(pedido);

        Pedido result = pedidoService.findPedidoByNumeroControle(numeroControle);

        assertEquals(pedido, result);

        verify(pedidoRepository, times(1)).findByNumeroControle(numeroControle);
    }

    @Test
    public void testFindPedidoByNumeroControleNotFound() {
        String numeroControle = "12345";

        when(pedidoRepository.findByNumeroControle(numeroControle)).thenReturn(null);

        assertThrows(PedidoNotFoundException.class, () -> pedidoService.findPedidoByNumeroControle(numeroControle));

        verify(pedidoRepository, times(1)).findByNumeroControle(numeroControle);
    }

    @Test
    public void testFindPedidoByDataCadastro() {
        LocalDate dataCadastro = LocalDate.now();
        List<Pedido> pedidos = new ArrayList<>();

        when(pedidoRepository.findByDataCadastro(dataCadastro)).thenReturn(pedidos);

        List<Pedido> result = pedidoService.findPedidoByDataCadastro(dataCadastro);

        assertEquals(pedidos, result);

        verify(pedidoRepository, times(1)).findByDataCadastro(dataCadastro);
    }

    @Test
    public void testFindPedidoByDataCadastroNotFound() {
        LocalDate dataCadastro = LocalDate.now();

        when(pedidoRepository.findByDataCadastro(dataCadastro)).thenReturn(null);

        assertThrows(PedidoNotFoundException.class, () -> pedidoService.findPedidoByDataCadastro(dataCadastro));

        verify(pedidoRepository, times(1)).findByDataCadastro(dataCadastro);
    }

    @Test
    public void testFindAllPedidos() {
        List<Pedido> pedidos = new ArrayList<>();

        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<Pedido> result = pedidoService.findAllPedidos();

        assertEquals(pedidos, result);

        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    public void testSavePedido() {
        List<PedidoRecord> listaPedido = new ArrayList<>();
        PedidoRecord pedidoRecord = new PedidoRecord("12345",
                LocalDate.of(2023, 6, 1),
                "TESTE",
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(1),
                1234L);
        listaPedido.add(pedidoRecord);

        pedidoService.savePedido(listaPedido);

        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    public void testSavePedidoWithExistingPedido() {
        List<PedidoRecord> listaPedido = new ArrayList<>();
        PedidoRecord pedidoRecord = new PedidoRecord("12345",
                LocalDate.of(2023, 6, 1),
                "TESTE",
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(1),
                1234L);
        listaPedido.add(pedidoRecord);

        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setNumeroControle("12345");

        when(pedidoRepository.findByNumeroControle("12345")).thenReturn(pedidoExistente);

        assertThrows(PedidoExistsException.class, () -> pedidoService.savePedido(listaPedido));

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    public void testSavePedidoWithDuplicatedNumeroControle() {
        List<PedidoRecord> listaPedido = new ArrayList<>();
        PedidoRecord pedidoRecord1 = new PedidoRecord("12345",
                LocalDate.of(2023, 6, 1),
                "TESTE",
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(1),
                1234L);
        listaPedido.add(pedidoRecord1);

        PedidoRecord pedidoRecord2 = new PedidoRecord("12345",
                LocalDate.of(2023, 6, 1),
                "TESTE",
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(1),
                1234L);
        listaPedido.add(pedidoRecord2);

        when(pedidoRepository.findByNumeroControle("12345")).thenReturn(null);

        assertThrows(PedidoNumeroControleDuplicatedException.class, () -> pedidoService.savePedido(listaPedido));

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    public void testSavePedidoWithQuantidadeNull() {
        List<PedidoRecord> listaPedido = new ArrayList<>();
        PedidoRecord pedidoRecord = new PedidoRecord("12345",
                LocalDate.of(2023, 6, 1),
                "TESTE",
                BigDecimal.ZERO,
                BigDecimal.valueOf(1),
                1234L);
        listaPedido.add(pedidoRecord);

        when(pedidoRepository.findByNumeroControle("12345")).thenReturn(null);

        pedidoService.savePedido(listaPedido);

        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    public void testUpdatePedido() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setDescricao("TESTE");
        pedido.setNumeroControle("12345");
        pedido.setValor(BigDecimal.ONE);

        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(id);

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoExistente));
        when(pedidoRepository.save(pedidoExistente)).thenReturn(pedidoExistente);

        Pedido result = pedidoService.updatePedido(pedido, id);

        assertEquals(pedidoExistente, result);

        verify(pedidoRepository, times(1)).findById(id);
        verify(pedidoRepository, times(1)).save(pedidoExistente);
    }

    @Test
    public void testUpdatePedidoNotFound() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () -> pedidoService.updatePedido(pedido, id));

        verify(pedidoRepository, times(1)).findById(id);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    public void testDeletePedido() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        pedidoService.deletePedido(id);

        verify(pedidoRepository, times(1)).findById(id);
        verify(pedidoRepository, times(1)).delete(pedido);
    }

    @Test
    public void testDeletePedidoNotFound() {
        Long id = 1L;

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () -> pedidoService.deletePedido(id));

        verify(pedidoRepository, times(1)).findById(id);
        verify(pedidoRepository, never()).delete(any(Pedido.class));
    }
}
