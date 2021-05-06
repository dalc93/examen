package com.diegoleal.xmen.rest;

import com.diegoleal.xmen.entity.Mutante;
import com.diegoleal.xmen.model.SecuenciaADN;
import com.diegoleal.xmen.repository.MutanteRepository;
import com.diegoleal.xmen.usecase.MutanteUseCase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mutantes")
public class MutanteRest {

    @Autowired
    private MutanteUseCase mutanteUseCase;

    @Autowired
    private MutanteRepository mutanteRepository;

    @PostMapping("/mutant")
    public ResponseEntity adnMutant(@RequestBody String secuenciaADN) {

        secuenciaADN = secuenciaADN.replaceAll("\\s", "");

        ResponseEntity response = ResponseEntity.status(HttpStatus.FORBIDDEN).body("Is not a Mutant");
        List<Mutante> mutantes = this.mutanteRepository.findBySecuencia(secuenciaADN);
        if (mutantes.size() > 0) {
            if (mutantes.get(0).isMutante()) {
                response = ResponseEntity.ok("Is a Mutant");
            }
        } else {
            SecuenciaADN secuencia = new Gson().fromJson(secuenciaADN, SecuenciaADN.class);
            Mutante mutante = new Mutante();
            mutante.setSecuencia(secuenciaADN);
            mutante.setMutante(mutanteUseCase.isMutante(secuencia));
            this.mutanteRepository.save(mutante);

            if (mutante.isMutante()) {
                response = ResponseEntity.ok("Is a Mutant");
            }
        }
        return response;
    }

    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String estadisticas(){
        long personas = this.mutanteRepository.countByMutante(false);
        long cantMutantes = this.mutanteRepository.countByMutante(true);
        String msg = "{" +
                    "\"count_mutant_dna\":" + cantMutantes + "," +
                    "\"count_human_dna\":" + personas + "," +
                    "\"ratio\":" + (cantMutantes/personas) + "," +
                "}";
        return msg;
    }

}
