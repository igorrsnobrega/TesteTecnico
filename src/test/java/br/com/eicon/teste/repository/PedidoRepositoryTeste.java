package br.com.eicon.teste.repository;

import br.com.eicon.teste.domain.Cliente;
import br.com.eicon.teste.domain.Pedido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PedidoRepositoryTeste {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testFindByNumeroControle() {
        String numeroControle = "12345";
        Pedido pedido = new Pedido();
        pedido.setNumeroControle(numeroControle);
        pedidoRepository.save(pedido);

        Pedido result = pedidoRepository.findByNumeroControle(numeroControle);

        assertNotNull(result);
        assertEquals(numeroControle, result.getNumeroControle());
    }

    @Test
    public void testFindByNumeroControleNotFound() {
        String numeroControle = "12345";

        Pedido result = pedidoRepository.findByNumeroControle(numeroControle);

        assertNull(result);
    }

    @Test
    public void testFindByDataCadastro() {
        LocalDate dataCadastro = LocalDate.now();
        Pedido pedido = new Pedido();
        pedido.setDataCadastro(dataCadastro);
        pedidoRepository.save(pedido);

        List<Pedido> result = pedidoRepository.findByDataCadastro(dataCadastro);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(dataCadastro, result.get(0).getDataCadastro());
    }

    @Test
    public void testFindByDataCadastroNotFound() {
        LocalDate dataCadastro = LocalDate.now();

        List<Pedido> result = pedidoRepository.findByDataCadastro(dataCadastro);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByCodigoCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L); // Usando o ID correto do cliente
        cliente.setNome("CLIENTE TESTE");
        clienteRepository.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedidoRepository.save(pedido);

        List<Pedido> result = pedidoRepository.findByClienteId(1L); // Pesquisando com o ID correto

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getCliente().getId());
    }


    @Test
    public void testFindByCodigoClienteNotFound() {
        Long clienteId = 1L;

        List<Pedido> result = pedidoRepository.findByClienteId(clienteId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
