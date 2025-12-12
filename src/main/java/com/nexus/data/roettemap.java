// Este arquivo deve estar em: src/main/java/com/nexus/data/RouletteMap.java
package com.nexus.data;

import java.util.*;

public class RouletteMap {

    public static final Map<Integer, int[]> VIZINHOS_RACE = new HashMap<>();
    public static final Map<Integer, Integer> ESPELHOS_RACE = new HashMap<>();
    public static final Map<Integer, Set<Integer>> SUBSTITUTOS_ORION = new HashMap<>();

    static {
        // --- VIZINHOS (Race Track) ---
        VIZINHOS_RACE.put(0, new int[]{26, 32}); 
        VIZINHOS_RACE.put(9, new int[]{31, 22});
        VIZINHOS_RACE.put(10, new int[]{5, 23});
        // TODO: Adicionar todos os 37 números aqui.

        // --- ESPELHOS ---
        ESPELHOS_RACE.put(1, 10);
        ESPELHOS_RACE.put(10, 1);
        
        // --- SUBSTITUIÇÃO ORION (Sua Tabela Comportamental) ---
        SUBSTITUTOS_ORION.put(0, Set.of(1, 10, 11, 22, 33, 9, 0));
        SUBSTITUTOS_ORION.put(1, Set.of(1, 10, 2, 20, 22, 11, 33, 19, 9, 0));
        SUBSTITUTOS_ORION.put(9, Set.of(6, 18, 36, 27, 8, 10, 1, 11, 0));
        // TODO: Adicionar todos os 37 mapeamentos completos aqui.
    }

    public static List<Integer> getVizinhosDe1(int numero) {
        List<Integer> alvos = new ArrayList<>();
        alvos.add(numero); 
        int[] viz = VIZINHOS_RACE.getOrDefault(numero, new int[]{});
        if (viz.length == 2) {
            alvos.add(viz[0]);
            alvos.add(viz[1]);
        }
        return alvos;
    }
}
