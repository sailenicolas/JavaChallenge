package com.empresa.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.services.CrudExtraService;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Collections;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PosCostController.class)
class PosCostControllerTest {

    public static final String POS = "/PosCost";
    @Autowired
    private MockMvc webTestClient;
    @MockitoBean
    private CrudExtraService<PosCostHash, PosCostRequest> service;

    @BeforeEach
    void setUp() {
    }

    /**
     * Class under test: {@link PosCostController#get(Long, Long)}
     */
    @Test
    void get() throws Exception {
        when(service.getById(any(), any())).thenReturn(Mono.justOrEmpty(new PosCostHash("1", "1", "1", new BigDecimal("1"))).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(POS + "/byId/{idA}/{idB}", 1, 2)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(Matchers.equalTo("1")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(service).getById(anyString(), anyString());
    }

    /**
     * Class under test: {@link PosCostController#getAll()}
     */
    @Test
    void getAll() throws Exception {
        when(this.service.getAll()).thenReturn(Mono.justOrEmpty(Collections.singletonList(new PosCostHash("1", "1", "1", new BigDecimal("1")))).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(POS)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(Matchers.equalTo("1")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(service).getAll();
    }

    /**
     * Class under test: {@link PosCostController#create(PosCostRequest)}
     */
    @Test
    void create() throws Exception {
        when(service.createCache(any())).thenReturn(Mono.justOrEmpty(new PosCostHash("1", "1", "1", new BigDecimal("1"))).map(ApiResponse::new));
        String content = new ObjectMapper().writeValueAsString(new PosCostRequest("1", "1", new BigDecimal("1")));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(POS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(Matchers.equalTo("1")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(service).createCache(any());
    }

    /**
     * Class under test: {@link PosCostController#put(PosCostPutRequest, String)}
     */
    @Test
    void put() throws Exception {
        when(service.putCache(any(), any())).thenReturn(Mono.justOrEmpty(new PosCostHash("1", "1", "1", new BigDecimal("1"))).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(POS)
                .param("id", "COST:1:2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new PosCostRequest("1", "1", new BigDecimal("1"))));
        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(Matchers.equalTo("1")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(service).putCache(any(), any());
    }

    /**
     * Class under test: {@link PosCostController#delete(String)}
     */
    @Test
    void delete() throws Exception {
        when(this.service.delete(any())).thenReturn(Mono.justOrEmpty(new PosCostHash()).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(POS)
                .param("id", "COST:1:2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new PosCostRequest("1", "1", new BigDecimal("1"))));
        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(Matchers.nullValue()))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(service).delete(any());
    }

}