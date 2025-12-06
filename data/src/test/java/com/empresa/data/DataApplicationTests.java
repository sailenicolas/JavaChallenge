package com.empresa.data;

import com.empresa.data.repositories.DataManagerRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class DataApplicationTests {

    @MockitoBean
    DataManagerRepository dataManagerRepository;
    @Test
    void contextLoads() {
    }

    @Test
    void contextLoads1() {
        DataApplication.main(new String[]{});
    }

}
