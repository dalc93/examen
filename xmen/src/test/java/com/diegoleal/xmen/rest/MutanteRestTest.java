package com.diegoleal.xmen.rest;

import com.diegoleal.xmen.entity.Mutante;
import com.diegoleal.xmen.repository.MutanteRepository;
import com.diegoleal.xmen.usecase.MutanteUseCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class )
@SpringBootTest
public class MutanteRestTest {

    @InjectMocks
    private MutanteRest rest;

    @Mock
    private MutanteRepository mutanteRepository;

    @Mock
    private MutanteUseCase useCase;


    @Test
    public void testForbidden(){
        List<Mutante> list = new ArrayList<>();
        String json = " {\n" +
                "    \"dna\": [\n" +
                "        \"ATGCGA\",\n" +
                "        \"CAGTGC\",\n" +
                "        \"TTATGT\",\n" +
                "        \"AGAAGG\",\n" +
                "        \"CCCGTA\",\n" +
                "        \"TCACTG\"\n" +
                "    ]\n" +
                "}";
        ResponseEntity response = rest.adnMutant(json);
        List<Mutante> mutantes = new ArrayList<>();
        mutantes.add(new Mutante());
        Mockito.when(this.mutanteRepository.findBySecuencia("json")).thenReturn(mutantes);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testSecuenciaExiste(){
        List<Mutante> list = new ArrayList<>();
        String json = " {\n" +
                "    \"dna\": [\n" +
                "        \"ATGCGA\",\n" +
                "        \"CAGTGC\",\n" +
                "        \"TTATGT\",\n" +
                "        \"AGAAGG\",\n" +
                "        \"CCCGTA\",\n" +
                "        \"TCACTG\"\n" +
                "    ]\n" +
                "}";
        ResponseEntity response = rest.adnMutant(json);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testOK(){
        List<Mutante> list = new ArrayList<>();
        String json = " {\n" +
                "    \"dna\": [\n" +
                "        \"ATGCGA\",\n" +
                "        \"CAGTGC\",\n" +
                "        \"TTATGT\",\n" +
                "        \"AGAAGG\",\n" +
                "        \"CCCCTA\",\n" +
                "        \"TCACTG\"\n" +
                "    ]\n" +
                "}";
        ResponseEntity response = rest.adnMutant(json);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }


    @Test
    public void estadisticasTest() {
        Mockito.when(this.mutanteRepository.countByMutante(false)).thenReturn(2);

        String msg = rest.estadisticas();
        Assert.assertTrue(!msg.isEmpty());
    }
}
