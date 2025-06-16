package beans;

import java.util.Date;
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
import services.ResumoService;

@ManagedBean(name = "jogoBean")
@ViewScoped
public class JogoBean {
	//  entidade principal
	private Jogo jogo;
	private List<Jogo> jogos;
	private Map<String, String> timesDisponiveis;
	
	// Atributos para a logica de Resumo de partidas
    private ResumoService resumoService = new ResumoService();
    private List<ResumoTimeDTO> resumoDosTimes;
	
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
	
	public void filtrarPorTime() {
	    if (timeFiltro != null && !timeFiltro.isEmpty()) {
	        this.jogosFiltrados = JogoDAO.findByTime(timeFiltro);
	    }
	}
	
	// resumo de times
    public void calcularResumo() {
        this.resumoDosTimes = resumoService.gerarResumo(this.jogos);
    }
    
    public List<ResumoTimeDTO> getResumoDosTimes() {
        return resumoDosTimes;
    }
}
