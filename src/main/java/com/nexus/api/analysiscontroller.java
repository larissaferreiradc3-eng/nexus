// Este arquivo deve estar em: src/main/java/com/nexus/api/AnalysisController.java
package com.nexus.api;

import com.nexus.logic.*;
import com.nexus.data.RouletteMap;
import java.util.*;

// Objeto que representa o JSON vindo do Frontend
class AnalysisRequest {
    private int gatilho;
    private List<Integer> history;
    // Getters e Setters (Omitidos, mas necessários no projeto real)
    public int getGatilho() { return gatilho; }
    public List<Integer> getHistory() { return history; }
}

public class AnalysisController {
    
    private final StrategyA_NeraEngine neraEngine = new StrategyA_NeraEngine();
    private final StrategyB_Comportamental strategyB = new StrategyB_Comportamental();
    private final StrategyC_Orion strategyC = new StrategyC_Orion();

    // Este método seria mapeado para um endpoint POST no Spring Boot
    public Map<String, Object> runAnalysis(AnalysisRequest request) { 
        
        // 1. Executar as 3 Estratégias
        List<Integer> alvosNera = neraEngine.run(request.getGatilho(), request.getHistory());
        List<Integer> alvosB = strategyB.run(request.getHistory());
        OrionResult orionResult = strategyC.run(request.getHistory());
        
        // 2. Confluência
        Set<Integer> alvosDefinitivos = new HashSet<>();
        List<String> ativadas = new ArrayList<>();

        if (!alvosNera.isEmpty()) { alvosDefinitivos.addAll(alvosNera); ativadas.add("NeraEngine"); }
        if (!alvosB.isEmpty()) { alvosDefinitivos.addAll(alvosB); ativadas.add("Comportamental"); }
        
        // Regra de Confluência da Orion: Adiciona o Número Oculto APENAS se bater com A ou B.
        int numOculto = orionResult.numeroOculto;
        if (numOculto != -1 && (alvosNera.contains(numOculto) || alvosB.contains(numOculto))) {
            alvosDefinitivos.add(numOculto);
            ativadas.add("Orion (Oculto)");
        }
        
        // 3. Cobertura Final (Vizinhos, Espelhos, Proteções)
        Set<Integer> alvosFinaisComCobertura = new HashSet<>();
        for (int alvo : alvosDefinitivos) {
            alvosFinaisComCobertura.addAll(RouletteMap.getVizinhosDe1(alvo));
            Integer espelho = RouletteMap.ESPELHOS_RACE.get(alvo);
            if (espelho != null) alvosFinaisComCobertura.add(espelho);
        }
        alvosFinaisComCobertura.add(0); // Proteção Zero
        alvosFinaisComCobertura.add(21); // Proteção Fixa (21)

        // 4. Calcular Tempo de Espera
        int tempoEspera = HistoryProcessor.calculateTempoEspera(request.getGatilho(), request.getHistory());

        // 5. Montar Resposta Final (JSON)
        Map<String, Object> response = new HashMap<>();
        response.put("alvos_finais", new ArrayList<>(alvosFinaisComCobertura));
        response.put("tempo_espera", tempoEspera);
        response.put("estrategias_ativadas", ativadas);
        response.put("alvo_orion_oculto", numOculto);
        
        return response;
    }
}
