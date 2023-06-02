package br.com.eicon.teste.controller;

import br.com.eicon.teste.domain.Pedido;
import br.com.eicon.teste.dto.PedidoDTO;
import br.com.eicon.teste.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAllPedidos() {
        return new ResponseEntity<>(pedidoService.findAllPedidos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findPedidoByNumeroControle(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.findPedidoById(id), HttpStatus.OK);
    }

    @GetMapping("/{numeroControle}")
    public ResponseEntity<Pedido> findPedidoByNumeroControle(@PathVariable String numeroControle) {
        return new ResponseEntity<>(pedidoService.findPedidoByNumeroControle(numeroControle), HttpStatus.OK);
    }

    @GetMapping("/{dataCadastro}")
    public ResponseEntity<List<Pedido>> findPedidoByDataCadastro(@PathVariable LocalDate dataCadastro) {
        return new ResponseEntity<>(pedidoService.findPedidoByDataCadastro(dataCadastro), HttpStatus.OK);
    }

    @PostMapping
    public void savePedido(@RequestBody List<PedidoDTO> dto) {
        pedidoService.savePedido(dto);
    }

    @PutMapping
    public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido) {
        return new ResponseEntity<>(pedidoService.updatePedido(pedido), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public void deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
    }

}
