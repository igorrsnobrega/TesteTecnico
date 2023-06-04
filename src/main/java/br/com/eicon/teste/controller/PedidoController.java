package br.com.eicon.teste.controller;

import br.com.eicon.teste.domain.Pedido;
import br.com.eicon.teste.record.PedidoRecord;
import br.com.eicon.teste.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> findAllPedidos() {
        return new ResponseEntity<>(pedidoService.findAllPedidos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findPedidoByNumeroControle(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.findPedidoById(id), HttpStatus.OK);
    }

    @GetMapping("/numeroControle/{numeroControle}")
    public ResponseEntity<Pedido> findPedidoByNumeroControle(@PathVariable String numeroControle) {
        return new ResponseEntity<>(pedidoService.findPedidoByNumeroControle(numeroControle), HttpStatus.OK);
    }

    @GetMapping("/dataCadastro/{dataCadastro}")
    public ResponseEntity<List<Pedido>> findPedidoByDataCadastro(@PathVariable LocalDate dataCadastro) {
        return new ResponseEntity<>(pedidoService.findPedidoByDataCadastro(dataCadastro), HttpStatus.OK);
    }

    @PostMapping
    public void savePedido(@RequestBody List<PedidoRecord> pedidoRecordList) {
        pedidoService.savePedido(pedidoRecordList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido, @PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.updatePedido(pedido, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
    }

}
