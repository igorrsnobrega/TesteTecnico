package br.com.eicon.teste.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class InicialService {
    private final JdbcTemplate jdbcTemplate;

    private InicialService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadInitialData() throws Exception {
        ClassPathResource resource = new ClassPathResource("clientes.sql");

        String sql = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));

        jdbcTemplate.execute(sql);
    }

}
