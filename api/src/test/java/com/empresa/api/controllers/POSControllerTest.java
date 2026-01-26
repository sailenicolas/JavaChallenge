package com.empresa.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.api.services.impl.CacheServiceImpl;
import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
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

@WebMvcTest(controllers = POSController.class)
@ExtendWith(SpringExtension.class)
class POSControllerTest {

    private static final String POS = "/Pos";
    @Autowired
    private MockMvc webTestClient;
    @MockitoBean
    private CacheServiceImpl service;

    @BeforeEach
    void setUp() {
    }

    /**
     * Class under test: {@link POSController#get(String)}
     */
    @Test
    void get() throws Exception {
        when(service.getById(any())).thenReturn(Mono.justOrEmpty(new PosHash("1", "1")).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(POS + "/byId/1")
                .queryParam("id", "1")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.idProceso").doesNotExist())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(service).getById(anyString());
    }

    /**
     * Class under test: {@link POSController#getAll()}
     */
    @Test
    void getAll() throws Exception {
        when(this.service.getAll()).thenReturn(Mono.justOrEmpty(Collections.singletonList(new PosHash("1", "1"))).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(POS )
                .queryParam("id", "1")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.idProceso").doesNotExist())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(service).getAll();
    }

    /**
     * Class under test: {@link POSController#create(PosHashRequest)} 
     */
    @Test
    void create() throws Exception {
        when(service.createCache(any())).thenReturn(Mono.justOrEmpty(new PosHash("1", "1")).map(ApiResponse::new));
        when(this.service.getAll()).thenReturn(Mono.justOrEmpty(Collections.singletonList(new PosHash("1", "1"))).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(POS )
                .queryParam("id", "1")
                .content(new ObjectMapper().writeValueAsBytes(new PosHashRequest("1")))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.idProceso").doesNotExist())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(service).createCache(any());
    }

    /**
     * Class under test: {@link POSController#put(PostHashPutRequest, String)}
     */
    @Test
    void put() throws Exception {
        when(service.putCache(any(), any())).thenReturn(Mono.justOrEmpty(new PosHash("1", "1")).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(POS )
                .queryParam("id", "1")
                .content(new ObjectMapper().writeValueAsBytes(new PostHashPutRequest("1")))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.idProceso").doesNotExist())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(service).putCache(any(), any());
    }

    /**
     * Class under test: {@link POSController#delete(String)}
     */
    @Test
    void delete() throws Exception {
        when(this.service.delete(any())).thenReturn(Mono.justOrEmpty(new PosHash()).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(POS)
                .queryParam("id", "1")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = webTestClient.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();
        webTestClient.perform(asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.idProceso").doesNotExist())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successful Call"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        verify(service).delete(any());
    }

}