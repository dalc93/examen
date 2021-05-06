package com.diegoleal.xmen.usecase;

import com.diegoleal.xmen.model.SecuenciaADN;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class MutanteUseCaseTest {

    @Test
    public void isMutanteTest() {
        SecuenciaADN data = new SecuenciaADN();
        List<String> list = new ArrayList<>();
        list.add("ATGCGA");
        list.add("CAGTGC");
        list.add("TTATGT");
        list.add("AGAAGG");
        list.add("CCCCTA");
        list.add("TCACTG");
        data.setDna(list);

        MutanteUseCase useCase = new MutanteUseCase();
        org.junit.Assert.assertTrue(useCase.isMutante(data));
    }

    @Test
    public void isNotMutanteTest() {
        SecuenciaADN data = new SecuenciaADN();
        List<String> list = new ArrayList<>();
        list.add("ATGCGA");
        list.add("CAGTGC");
        list.add("TTGTGT");
        list.add("AGAAGG");
        list.add("CCCCTA");
        list.add("TCACTG");
        data.setDna(list);

        MutanteUseCase useCase = new MutanteUseCase();
        org.junit.Assert.assertFalse(useCase.isMutante(data));
    }

    @Test
    public void isMutanteTestErrorBaseNitrogenada() {
        SecuenciaADN data = new SecuenciaADN();
        List<String> list = new ArrayList<>();
        list.add("ATGCGA");
        list.add("CAGTGC");
        list.add("TTATGT");
        list.add("AGAAHG");
        list.add("CCCCTA");
        list.add("TCACTG");
        data.setDna(list);

        MutanteUseCase useCase = new MutanteUseCase();
        try {
            useCase.isMutante(data);
            org.junit.Assert.fail();
        } catch (RuntimeException e){
            org.junit.Assert.assertEquals("Base nitrogenada no permitda.", e.getMessage().toString());
        }

    }

    @Test
    public void isMutanteTestErrorTamano() {
        SecuenciaADN data = new SecuenciaADN();
        List<String> list = new ArrayList<>();
        list.add("ATGCGA");
        list.add("CAGTGC");
        list.add("TTATGT");
        list.add("AGAAAAG");
        list.add("CCCCTA");
        list.add("TCACTG");
        data.setDna(list);

        MutanteUseCase useCase = new MutanteUseCase();
        try {
            useCase.isMutante(data);
            org.junit.Assert.fail();
        } catch (RuntimeException e){
            org.junit.Assert.assertEquals("No es una matriz NxN", e.getMessage().toString());
        }
    }



}
