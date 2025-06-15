package beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import daos.CampeonatoDAO;
import entidades.Campeonato;

@ManagedBean(name = "campeonatoBean")
@ViewScoped
public class CampeonatoBean {
	private Campeonato campeonato = new Campeonato();
	private List<Campeonato> campeonatos;
	
	public String salvar()
	{
		FacesContext context = FacesContext.getCurrentInstance();
    	CampeonatoDAO.salvar(campeonato);
        campeonato = new Campeonato(); 
	    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
		        "Sucesso", "Campeonato criado com sucesso!"));
        return null; 
	}
	
    public void prepararEdicao(Campeonato campSelecionado) {
        this.campeonato = campSelecionado;
    }
    
    public void editar() {
        CampeonatoDAO.editar(campeonato);
        this.campeonato = new Campeonato();
        this.campeonatos = CampeonatoDAO.listar();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage("Sucesso", "Campeonato editado."));
    }

    public void excluir(Campeonato campParaExcluir) {
        CampeonatoDAO.excluir(campParaExcluir);
        this.campeonatos = CampeonatoDAO.listar();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage("Sucesso", "Campeonato excluído."));
    }
	
	public Campeonato getCampeonato() {
		return campeonato;
	}
	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}
	public List<Campeonato> getCampeonatos() {
		if(campeonatos == null) {
			campeonatos = CampeonatoDAO.listar();
		}
		return campeonatos;
	}
	public void setCampeonatos(List<Campeonato> campeonatos) {
		this.campeonatos = campeonatos;
	}
}
