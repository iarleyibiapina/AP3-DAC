package services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import entidades.ArCondicionado;

public class ArCondicionadoService {
	
	public static ArCondicionado defineTemperaturas(ArCondicionado arCondicionado)
	{
        if (arCondicionado == null) {
            throw new IllegalArgumentException("Objeto ArCondicionado não pode ser nulo.");
        }
//      Define a temperatura atual em 20
        arCondicionado.setTemperaturaAtual(20);
        
//      Validacao para temperatura minima
        Integer tempMin = arCondicionado.getTemperaturaMinima();
        if (tempMin == null || tempMin < 10 || tempMin > 16) {
            throw new IllegalArgumentException("Temperatura mínima deve estar entre 10 e 16.");
        }
		
// 	 	Validacao para temperatura maxima
        Integer tempMax = arCondicionado.getTemperaturaMaxima();
        if (tempMax == null || tempMax < 20 || tempMax > 25) {
            throw new IllegalArgumentException("Temperatura máxima deve estar entre 20 e 25.");
        }
       
//		Valida se a data é maior que data atual
        Date dataManutencao = arCondicionado.getDataManutencao();
        if (dataManutencao == null || !dataManutencao.after(new Date())) {
            throw new IllegalArgumentException("Data de manutenção deve ser posterior à data atual.");
        }

		return arCondicionado;
	}
	
    public static String diasRestantesManutencao(ArCondicionado arCondicionado)
    {
    	Date dataManutencao = arCondicionado.getDataManutencao();
        if (dataManutencao == null) {
            return "Data de manutenção não definida.";
        }
//        	converte o Date para LocalDate
        LocalDate localDataManutencao = dataManutencao.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
//        	define a data atual
        LocalDate dataAtual = LocalDate.now();
//			compara as datas
        long diasRestantes = ChronoUnit.DAYS.between(dataAtual, localDataManutencao);
        
        if (diasRestantes > 0) {
            return "Faltam " + diasRestantes + " dias para a manutenção.";
        } else if (diasRestantes == 0) {
            return "A manutenção é hoje.";
        } else {
            return "A manutenção está atrasada há " + Math.abs(diasRestantes) + " dias.";
        }
    }
}
