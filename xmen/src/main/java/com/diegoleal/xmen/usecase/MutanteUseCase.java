package com.diegoleal.xmen.usecase;

import com.diegoleal.xmen.entity.Mutante;
import com.diegoleal.xmen.model.SecuenciaADN;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MutanteUseCase {

    public static final int CANTIDAD_SECUENCIA = 4;

    public static final String BASE_NITROGENADA = "ATCG";

    /**
     * Método encargado de validar si una persona es mutante
     * @param secuenciaADN Secuencia de ADN a validar.
     * @return True si es mutante False si no es mutante.
     */
    public boolean isMutante(SecuenciaADN secuenciaADN) {
        String[][] adn = this.obtenerMatriz(secuenciaADN.getDna());
        return validarSecuencias(adn);
    }

    /**
     * Método encargado de validar las secuencias en una matriz
     * @param adn Matriz a validar
     * @return True si existe más de una secuencia de cuatro letras iguales False si no existe.
     */
    private boolean validarSecuencias(String[][] adn) {

        int secuenciasVerticales = 0;
        int secuenciasHorizontales = 0;
        int secuenciasOblicuo = 0;
        int tamanoMatriz = adn.length;

        for (int i = 0; i < tamanoMatriz; i ++) {

            if (validarHorizontal(adn, i)){
                secuenciasHorizontales++;
            }
            if(validarVertical(adn, i)) {
                secuenciasVerticales++;
            }
            if (validarOblicua(adn, i)){
                secuenciasOblicuo++;
            }
        }

        return (( (secuenciasVerticales > 1 || secuenciasHorizontales > 1 || secuenciasOblicuo > 1) ||
                ( secuenciasVerticales == 1 && secuenciasHorizontales == 1 && secuenciasOblicuo == 1) ));
    }

    /**
     * Método encargado de validar las secuencias horizontales en una matriz.
     * @param matriz Matriz a validar.
     * @param row indice por el cual validar.
     * @return True si existe una secuencia de ADN False si no existe.
     */
    private static boolean validarHorizontal(String[][] matriz, int row) {
        String last = matriz[row][0];
        String actual = "";
        int count = 0;
        for (int column = 0; column < matriz[row].length; column++){
            actual = matriz[row][column];
            if (last.equals(actual)) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count=0;
                last = actual;
            }
        }
        return false;
    }

    /**
     * Método encargado de validar las secuencias verticales en una matriz.
     * @param matriz Matriz a validar.
     * @param indice Indice por el cual validar.
     * @return True si existe una secuencia de ADN False si no existe.
     */
    private static boolean validarVertical(String[][] matriz, int indice) {
        String last = matriz[0][indice];
        String actual = "";
        int count = 0;
        for (int column = 0; column < matriz[indice].length; column++){
            actual = matriz[column][indice];
            if (last.equals(actual)) {
                count++;
                if (count == CANTIDAD_SECUENCIA) {
                    return true;
                }
            } else {
                count=0;
                last = actual;
            }
        }
        return false;
    }

    /**
     * Método encargado de validar la vertical en la matriz.
     * @param matriz Matriz a validar.
     * @param row Fila por la cual empezar.
     * @return True si existe una secuancia de ADN False si no existe.
     */
    private static boolean validarOblicua(String[][] matriz, int row) {
        if ((row + CANTIDAD_SECUENCIA) <= matriz[row].length){
            String last = matriz[row][0];
            int count = 0;
            for (int i = 0; i < matriz[row].length; i++){
                if ((i+1) < matriz[row].length && (row + i) < matriz[row].length &&
                        last.equals(matriz[row + i][i])) {
                    count++;
                    if (count == CANTIDAD_SECUENCIA) {
                        return true;
                    }
                } else {
                    count = 0;
                    last = (row + i) < matriz[row].length?matriz[row + i][i]:matriz[row][i];
                }
            }
        }
        return false;
    }

    /**
     * Método encargado de transformar una lista de string en una matriz y validar el tamano de la matriz y las
     * bases nitrogenadas.
     * @param secuencia Lista de String
     * @return Matriz tipo string.
     */
    private String[][] obtenerMatriz(List<String> secuencia) {
        String[][] adn = new String[secuencia.size()][secuencia.size()];
        int size = secuencia.size();
        int indexRow = 0;

        for (String data : secuencia) {
            int indexColum = 0;
            for (char c : data.toCharArray()) {
                if (size == data.toCharArray().length) {
                    adn[indexRow][indexColum] = String.valueOf(c);
                    if (!BASE_NITROGENADA.contains(adn[indexRow][indexColum])) {
                        throw new RuntimeException("Base nitrogenada no permitda.");
                    }
                    indexColum++;
                } else {
                    throw new RuntimeException("No es una matriz NxN");
                }
            }
            indexRow++;
        }
        return adn;
    }
}
