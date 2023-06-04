package br.com.eicon.teste.service;

import br.com.eicon.teste.domain.Cliente;
import br.com.eicon.teste.exception.ClienteNotFoundException;
import br.com.eicon.teste.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindClienteById() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("CLIENTE TESTE");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.findClienteById(1L);

        assertEquals(cliente, result);

        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindClienteByIdNotFound() {
        Long id = 1L;

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> clienteService.findClienteById(id));

        verify(clienteRepository, times(1)).findById(id);
    }
}

