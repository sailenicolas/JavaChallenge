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
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.api.services.CrudExtraService;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.math.BigDecimal;
import java.util.List;
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

class CachePosCostControllerTest {
    public static final String POS = "/PosCost";
    @Autowired
    private MockMvc webTestClient;
    @MockitoBean
    private CrudExtraService<PosCostHash, PosCostRequest> service;

    @BeforeEach
    void setUp() {
    }

    /**
     * Class under test: {@link PosCostController#getPointMin(String, String)}
     */
    @Test
    void getPointMin() throws Exception {
        PosCostBHash posCostHash = new PosCostBHash("", "", "", "", "", new BigDecimal("1"));
        List<PosCostBHash> points = List.of(posCostHash);
        when(this.service.getPointMin(any(), anyString())).thenReturn(Mono.just(new PosCostMinHash("1", points, new BigDecimal("1"))).map(ApiResponse::new));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(POS + "/pointMin")
                .queryParam("idA", "1")
                .queryParam("idB", "2")
                .accept(MediaType.APPLICATION_JSON);

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

        verify(service).getPointMin(anyString(), anyString());
    }
    /**
     * Class under test: {@link PosCostController#getPointC(String)} 
     */
    @Test
    void getPointA() throws Exception {
        when(this.service.getPointB(any())).thenReturn(Mono.just(new ApiResponse<>(List.of(new PosCostBHash("1","1","1","1","1",new BigDecimal("1"))))));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(POS + "/pointA")
                .queryParam("idA", "1")
                .queryParam("idB", "2")
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

        verify(service).getPointB(anyString());
    }
}