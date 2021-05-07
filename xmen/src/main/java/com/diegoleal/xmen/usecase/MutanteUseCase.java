package com.diegoleal.xmen.usecase;

import com.diegoleal.xmen.entity.Mutante;
import com.diegoleal.xmen.model.SecuenciaADN;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MutanteUseCase {

    public static final int CANTIDAD_SECUENCIA = 4;

    public String CADENA_A = "AAAA";
    public String CADENA_T = "TTTT";
    public String CADENA_G = "GGGG";
    public String CADENA_C = "CCCC";

    public static final String BASE_NITROGENADA = "ATCG";

    /**
     * Método encargado de validar si una persona es mutante
     * @param secuenciaADN Secuencia de ADN a validar.
     * @return True si es mutante False si no es mutante.
     */
    public boolean isMutante(SecuenciaADN secuenciaADN) {
        return extraerDatosAndValidar(secuenciaADN.getDna());
    }

    /**
     * Método encargado de sacar las horizontales, verticales y oblicuas de la data y luego validar las secuencias.
     * @param secuencia Lista de secuencias a validar.
     * @return True si es mutante, False si no es mutante.
     */
    public boolean extraerDatosAndValidar(List<String> secuencia) {
        List<String> vertical = new ArrayList<>();
        List<String> oblicuoSuperior = new ArrayList<>();
        List<String> oblicuoInferior = new ArrayList<>();
        int indexRow = 0;
        for (String data : secuencia) {
            int indexColum = 0;
            int indexSuperior = 0;
            int indexInferior = oblicuoInferior.size();
            validarTamanoCadena(data, secuencia);
            for (char c : data.toCharArray()) {

                validarBaseNitrogenadaSecuencia(c);
                if (vertical.size() > indexColum) {
                    vertical.set(indexColum, vertical.get(indexColum).concat(String.valueOf(c)));
                } else {
                    vertical.add(String.valueOf(c));
                }

                if (oblicuoSuperior.size() > indexColum) {
                    if ((indexRow - 1) >= 0 && (indexColum - 1) >= 0 && indexColum >= indexRow) {
                        oblicuoSuperior.set(indexSuperior, oblicuoSuperior.get(indexSuperior).concat(String.valueOf(c)));
                        indexSuperior++;
                    }
                } else {
                    oblicuoSuperior.add(String.valueOf(c));
                }

                if (indexColum < indexRow) {
                    if ((indexRow - 1) >= 0 && (indexColum - 1) >= 0) {
                        oblicuoInferior.set(indexInferior - indexColum, oblicuoInferior.get(indexInferior - indexColum).concat(String.valueOf(c)));
                    } else {
                        oblicuoInferior.add(String.valueOf(c));
                    }
                }
                indexColum++;
            }
            indexRow++;
        }
        return this.validarSecuencia(secuencia,vertical,oblicuoSuperior, oblicuoInferior);
    }

    /**
     * Método encargado de validar que una letra pertenezca a la base nitrogenada
     * @param c Letra de la cadena a validar.
     */
    private void validarBaseNitrogenadaSecuencia(char c) {
        if (!BASE_NITROGENADA.contains(String.valueOf(c))) {
            throw new RuntimeException("Base nitrogenada no permitda.");
        }
    }

    /**
     * Método encargado de validar que cada cadena concuerde con el tamaño de la secuencia ADN.
     * @param data Cadena a validar.
     * @param secuencia Secuencia a validar.
     */
    private void validarTamanoCadena(String data, List<String> secuencia) {
        if (data.length() != secuencia.size()) {
            throw new RuntimeException("No es una matriz NxN");
        }
    }

    /**
     * Método encargado de recorrer las horizontales, verticales y oblicuas para encontrar si existe una secuencia.
     * @param secuencia Secuencia a validar, horizontales.
     * @param vertical secuencias verticales.
     * @param oblicuoSuperior Secuencias oblicuos superior.
     * @param oblicuoInferior Secuencias oblicuos inferior.
     * @return True si la persona es mutante o False si no es mutante.
     */
    private boolean validarSecuencia(List<String> secuencia, List<String> vertical, List<String> oblicuoSuperior, List<String> oblicuoInferior) {
        int secuenciasHorizontales = 0;
        int secuenciasOblicuo = 0;
        int secuenciasVerticales = 0;
        for (int i = 0; i < secuencia.size(); i++) {

            if (validarSecuenciaEnCadena(secuencia.get(i))) {
                secuenciasHorizontales++;
            }

            if (vertical.size() > i && validarSecuenciaEnCadena(vertical.get(i))) {
                secuenciasVerticales++;
            }
            if (oblicuoSuperior.size() > i && validarSecuenciaEnCadena(oblicuoSuperior.get(i))) {
                secuenciasOblicuo++;
            }
            if (oblicuoInferior.size() > i && validarSecuenciaEnCadena(oblicuoInferior.get(i))) {
                secuenciasOblicuo++;
            }
        }
        return (( (secuenciasVerticales > 1 || secuenciasHorizontales > 1 || secuenciasOblicuo > 1) ||
                ( secuenciasVerticales == 1 && secuenciasHorizontales == 1 && secuenciasOblicuo == 1) ));
    }

    /**
     * Método encargado de validar una cadena en las bases nitrogenadas permitidas.
     * @param cadena Cadena de secuencia a validar.
     * @return True si tiene una secuencia de base nitrogenada False si no tiene.
     */
    public boolean validarSecuenciaEnCadena(String cadena) {
        return cadena.contains(CADENA_A) || cadena.contains(CADENA_C) || cadena.contains(CADENA_G)
                || cadena.contains(CADENA_T);
    }
}

