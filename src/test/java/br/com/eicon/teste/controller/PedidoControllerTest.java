package br.com.eicon.teste.controller;

import br.com.eicon.teste.domain.Pedido;
import br.com.eicon.teste.record.PedidoRecord;
import br.com.eicon.teste.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @Test
    void testFindAllPedidos() throws Exception {
        List<Pedido> pedidos = new ArrayList<>();
        when(pedidoService.findAllPedidos()).thenReturn(pedidos);

        ResultActions resultActions = mockMvc.perform(get("/pedidos"));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(pedidoService, times(1)).findAllPedidos();
        verifyNoMoreInteractions(pedidoService);
    }

    @Test
    void testFindPedidoByNumeroControle() throws Exception {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        when(pedidoService.findPedidoById(id)).thenReturn(pedido);

        ResultActions resultActions = mockMvc.perform(get("/pedidos/{id}", id));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));

        verify(pedidoService, times(1)).findPedidoById(id);
        verifyNoMoreInteractions(pedidoService);
    }

    @Test
    void testFindPedidoByDataCadastro() throws Exception {
        LocalDate dataCadastro = LocalDate.now();
        List<Pedido> pedidos = new ArrayList<>();
        when(pedidoService.findPedidoByDataCadastro(dataCadastro)).thenReturn(pedidos);

        ResultActions resultActions = mockMvc.perform(get("/pedidos/dataCadastro/{dataCadastro}", dataCadastro));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(pedidoService, times(1)).findPedidoByDataCadastro(dataCadastro);
        verifyNoMoreInteractions(pedidoService);
    }

    @Test
    void testSavePedido() throws Exception {
        List<PedidoRecord> pedidoRecordList = new ArrayList<>();

        RequestBuilder requestBuilder = post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pedidoRecordList));

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions.andExpect(status().isOk());

        verify(pedidoService, times(1)).savePedido(pedidoRecordList);
        verifyNoMoreInteractions(pedidoService);
    }

    @Test
    void testUpdatePedido() throws Exception {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setDataCadastro(LocalDate.of(2023, 6, 1));
        when(pedidoService.updatePedido(pedido, id)).thenReturn(pedido);

        mockMvc.perform(put("/pedidos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"dataCadastro\":\"2023-06-01\"}"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));

        verify(pedidoService, times(1)).updatePedido(pedido, id);
        verifyNoMoreInteractions(pedidoService);
    }

    @Test
    void testDeletePedido() throws Exception {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);

        ResultActions resultActions = mockMvc.perform(delete("/pedidos/{id}", id));

        resultActions.andExpect(status().isOk());

        verify(pedidoService, times(1)).deletePedido(id);
        verifyNoMoreInteractions(pedidoService);
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
