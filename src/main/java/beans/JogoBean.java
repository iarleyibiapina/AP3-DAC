package beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import DTOs.ResumoTimeDTO;
import daos.JogoDAO;
import entidades.Campeonato;
import entidades.Jogo;

@ManagedBean(name = "jogoBean")
@ViewScoped
public class JogoBean {
	//  entidade principal
	private Jogo jogo;
	private List<Jogo> jogos;
	private Map<String, String> timesDisponiveis;
	
    @PostConstruct
    public void init() {
        this.jogo = new Jogo();
        // Garante que a "corrente" jogo -> campeonato não quebre
        this.jogo.setCampeonato(new Campeonato()); 

        // Popula a lista de times
        timesDisponiveis = new LinkedHashMap<>();
        timesDisponiveis.put("Time A", "A");
        timesDisponiveis.put("Time B", "B");
        timesDisponiveis.put("Time C", "C");
    }
	
	//	item (j)
	private List<ResumoTimeDTO> resumoTimeDTO;

	//	pagina de filtro (item k)
	private String timeFiltro;
	private List<Jogo> jogosFiltrados;
	
	public List<ResumoTimeDTO> getResumoTimeDTO() {
		return resumoTimeDTO;
	}

	public void setResumoTimeDTO(List<ResumoTimeDTO> resumoTimeDTO) {
		this.resumoTimeDTO = resumoTimeDTO;
	}
	
	public String getTimeFiltro() {
		return timeFiltro;
	}

	public void setTimeFiltro(String timeFiltro) {
		this.timeFiltro = timeFiltro;
	}

	public List<Jogo> getJogosFiltrados() {
		return jogosFiltrados;
	}

	public void setJogosFiltrados(List<Jogo> jogosFiltrados) {
		this.jogosFiltrados = jogosFiltrados;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	
	public List<Jogo> getJogos() {
		if(jogos == null) {
			jogos = JogoDAO.listar();
		}
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}

	// Dentro da classe JogoBean
	public String salvar() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    // Validação de times iguais
	    jogo.setDataCadastro(new Date());
	    if (jogo.getTime1().equals(jogo.getTime2())) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
	            "Atenção", "Time 1 não pode ser igual ao Time 2."));
	        return null;
	    }

	    // Preenchimento automático da data de cadastro
	    jogo.setDataCadastro(new Date());
	    
	    // Lógica para carregar o campeonato selecionado
	    // (A ser implementada no bean)
	    
	    JogoDAO.salvar(jogo);
	    novoJogo(); // Limpa o objeto para um novo cadastro
	    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
	        "Sucesso", "Jogo salvo com sucesso!"));
	    
	    return null;
	}

	public void novoJogo() {
	    this.jogo = new Jogo();
	    this.jogo.setCampeonato(new Campeonato());
	}

    public void prepararEdicao(Jogo jogoSelecionado) {
        this.jogo = jogoSelecionado;
    }
    
    public void editar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            // Validação para garantir que os times não sejam iguais na edição
            if (jogo.getTime1().equals(jogo.getTime2())) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                    "Atenção", "Time 1 não pode ser igual ao Time 2."));
                return; 
            }
            JogoDAO.editar(this.jogo); 
            novoJogo(); 
            this.jogos = JogoDAO.listar(); 
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo editado com sucesso!"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível editar o jogo."));
        }
    }
    
    public void excluir(Jogo jogoParaExcluir) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
        	JogoDAO.excluir(jogoParaExcluir);
            this.jogos = JogoDAO.listar();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo excluído com sucesso!"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir o jogo."));
        }
    }
	
	public void calcularResumo() {
	    Map<String, ResumoTimeDTO> stats = new HashMap();
	    String[] times = {"A", "B", "C"}; // Ou busque dinamicamente
	    for (String nomeTime : times) {
	        stats.put(nomeTime, new ResumoTimeDTO(nomeTime, 0, 0, 0, 0, 0, 0, 0));
	    }

	    for (Jogo jogo : JogoDAO.listar()) {
	        ResumoTimeDTO time1 = stats.get(jogo.getTime1());
	        ResumoTimeDTO time2 = stats.get(jogo.getTime2());

	        // Processa gols
	        time1.setGolsMarcados(time1.getGolsMarcados() + jogo.getGolsTime1());
	        time1.setGolsSofridos(time1.getGolsSofridos() + jogo.getGolsTime2());
	        time2.setGolsMarcados(time2.getGolsMarcados() + jogo.getGolsTime2());
	        time2.setGolsSofridos(time2.getGolsSofridos() + jogo.getGolsTime1());

	        // Processa resultado
	        if (jogo.getGolsTime1() > jogo.getGolsTime2()) { // Vitória Time 1
	            time1.setVitorias(time1.getVitorias() + 1);
	            time2.setDerrotas(time2.getDerrotas() + 1);
	        } else if (jogo.getGolsTime2() > jogo.getGolsTime1()) { // Vitória Time 2
	            time2.setVitorias(time2.getVitorias() + 1);
	            time1.setDerrotas(time1.getDerrotas() + 1);
	        } else { // Empate
	            time1.setEmpates(time1.getEmpates() + 1);
	            time2.setEmpates(time2.getEmpates() + 1);
	        }
	    }
	    
	    // Calcula pontos e saldo de gols
	    for(ResumoTimeDTO resumo : stats.values()) {
	        resumo.setPontuacao((resumo.getVitorias() * 3) + resumo.getEmpates());
	        resumo.setSaldoGols(resumo.getGolsMarcados() - resumo.getGolsSofridos());
	    }

	    this.resumoTimeDTO = new ArrayList<>(stats.values());
	    // Ordena por pontuação
	    this.resumoTimeDTO.sort(Comparator.comparingInt(ResumoTimeDTO::getPontuacao).reversed());
	}
	
	public void filtrarPorTime() {
	    if (timeFiltro != null && !timeFiltro.isEmpty()) {
	        this.jogosFiltrados = JogoDAO.findByTime(timeFiltro);
	    }
	}
}
