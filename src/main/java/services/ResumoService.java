package services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import DTOs.ResumoTimeDTO;
import entidades.Jogo;

public class ResumoService {

    public List<ResumoTimeDTO> gerarResumo(List<Jogo> todosOsJogos) {
        
        // 1. Usamos um Map para armazenar os dados de cada time de forma fácil
        Map<String, ResumoTimeDTO> stats = new LinkedHashMap<>();
        
        // 2. Iniciar DTO com times conhecidos (fixado em A,B,C..)
        // se time nao jogou, aparece tudo zerado
        stats.put("A", new ResumoTimeDTO("Time A"));
        stats.put("B", new ResumoTimeDTO("Time B"));
        stats.put("C", new ResumoTimeDTO("Time C"));

        // 3. Iteramos sobre cada jogo para computar os resultados
        for (Jogo jogo : todosOsJogos) {
            ResumoTimeDTO dtoTime1 = stats.get(jogo.getTime1());
            ResumoTimeDTO dtoTime2 = stats.get(jogo.getTime2());

            // Gols Marcados e Sofridos
            dtoTime1.setGolsMarcados(dtoTime1.getGolsMarcados() + jogo.getGolsTime1());
            dtoTime1.setGolsSofridos(dtoTime1.getGolsSofridos() + jogo.getGolsTime2());
            // 
            dtoTime2.setGolsMarcados(dtoTime2.getGolsMarcados() + jogo.getGolsTime2());
            dtoTime2.setGolsSofridos(dtoTime2.getGolsSofridos() + jogo.getGolsTime1());

            if (jogo.getGolsTime1() > jogo.getGolsTime2()) { // Vitória do Time 1
                dtoTime1.setVitorias(dtoTime1.getVitorias() + 1);
                dtoTime2.setDerrotas(dtoTime2.getDerrotas() + 1);
            } else if (jogo.getGolsTime2() > jogo.getGolsTime1()) { // Vitória do Time 2
                dtoTime2.setVitorias(dtoTime2.getVitorias() + 1);
                dtoTime1.setDerrotas(dtoTime1.getDerrotas() + 1);
            } else { // Empate
                dtoTime1.setEmpates(dtoTime1.getEmpates() + 1);
                dtoTime2.setEmpates(dtoTime2.getEmpates() + 1);
            }
        }

        // 4. Definindo o saldo para cada time
        for (ResumoTimeDTO resumo : stats.values()) {
            int pontos = (resumo.getVitorias() * 3) + (resumo.getEmpates() * 1);
            int saldo = resumo.getGolsMarcados() - resumo.getGolsSofridos();
            resumo.setPontuacao(pontos);
            resumo.setSaldoGols(saldo);
        }

        List<ResumoTimeDTO> resultadoFinal = new ArrayList<>(stats.values()); // de map para arraylist
        resultadoFinal.sort(Comparator.comparingInt(ResumoTimeDTO::getPontuacao).reversed()); // ordena pontuacao decrescente

        return resultadoFinal;
    }
}