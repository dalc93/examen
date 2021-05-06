package com.diegoleal.xmen.repository;


import com.diegoleal.xmen.entity.Mutante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MutanteRepository extends JpaRepository<Mutante, Long> {

    List<Mutante> findBySecuencia(String secuencia);

    Integer countByMutante(boolean mutante);
}
