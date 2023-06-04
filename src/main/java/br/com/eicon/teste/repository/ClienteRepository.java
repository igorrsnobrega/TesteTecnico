package br.com.eicon.teste.repository;

import br.com.eicon.teste.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
