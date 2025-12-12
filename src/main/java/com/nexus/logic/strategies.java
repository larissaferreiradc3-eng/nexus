// Este arquivo deve estar em: src/main/java/com/nexus/logic/Strategies.java
package com.nexus.logic;

import com.nexus.data.RouletteMap;
import java.util.*;

// Estrutura interna para retorno da Orion
class OrionResult {
    final int alvoReal;
    final int numeroOculto; 

    public OrionResult(int real, int oculto) {
        this.alvoReal = real;
        this.numeroOculto = oculto;
    }
}

// ----------------------------------------------
// ESTRATÉGIA A: NERA ENGINE (Pilar Principal 1)
// ----------------------------------------------
public class StrategyA_NeraEngine {
    public List<Integer> run(int gatilho, List<Integer> history) {
        // TODO: Implementar a RaceValidator.
        if (history.size() > 5) {
            // Retorna o Gatilho e vizinhos (Simulação)
            return RouletteMap.getVizinhosDe1(gatilho);
        }
        return Collections.emptyList(); 
    }
}

// ----------------------------------------------
// ESTRATÉGIA B: COMPORTAMENTAL (Pilar Principal 2)
// ----------------------------------------------
class StrategyB_Comportamental {
    public List<Integer> run(List<Integer> history) {
        // Simulação: se o número 7 está Quente, sugere ele.
        if (HistoryProcessor.isRetornoAtivo(7, history)) {
            return RouletteMap.getVizinhosDe1(7);
        }
        return Collections.emptyList();
    }
}

// ----------------------------------------------
// ESTRATÉGIA C: ORION (Filtro Terciário / Número Oculto)
// ----------------------------------------------
class StrategyC_Orion {
    public OrionResult run(List<Integer> history) {
        if (history.size() < 5) return new OrionResult(-1, -1);
        
        // Simulação: A soma do Gatilho da Orion resultou no Alvo Real 9
        int alvoReal = 9; 
        
        Set<Integer> poolOculto = RouletteMap.SUBSTITUTOS_ORION.getOrDefault(alvoReal, Collections.emptySet());
        
        int numeroOculto = -1;
        // Filtro: Busca o substituto que está em Retorno Ativo
        for(int num : poolOculto) {
            if (HistoryProcessor.isRetornoAtivo(num, history)) {
                numeroOculto = num; 
                break; 
            }
        }
        
        return new OrionResult(alvoReal, numeroOculto);
    }
}
