package br.com.eicon.teste.service;

import br.com.eicon.teste.domain.Cliente;
import br.com.eicon.teste.exception.ClienteNotFoundException;
import br.com.eicon.teste.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente findClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(ClienteNotFoundException::new);
    }
}
