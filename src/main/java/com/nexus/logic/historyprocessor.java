// Este arquivo deve estar em: src/main/java/com/nexus/logic/HistoryProcessor.java
package com.nexus.logic;

import java.util.*;

public class HistoryProcessor {

    public static List<int[]> getOcorrencias(int gatilho, List<Integer> history) {
        // TODO: Implementar a lógica exata da Nera Engine (3 antes / 3 depois).
        return new ArrayList<>(); 
    }

    public static int calculateTempoEspera(int gatilho, List<Integer> history) {
        // Lógica simplificada de média de intervalo:
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i) == gatilho) indices.add(i);
        }
        if (indices.size() < 2) return 0;
        
        int totalIntervalo = 0;
        for (int i = 1; i < indices.size(); i++) {
            totalIntervalo += (indices.get(i) - indices.get(i-1));
        }
        return Math.round(totalIntervalo / (float)indices.size());
    }
    
    public static boolean isRetornoAtivo(int numero, List<Integer> history) {
        // Simulação de Retorno Ativo: verifica se está nas últimas 10 rodadas.
        return history.subList(Math.max(0, history.size() - 10), history.size()).contains(numero);
    }
}
