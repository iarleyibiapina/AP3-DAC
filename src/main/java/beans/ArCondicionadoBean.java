package beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import daos.ArCondicionadoDAO;
import entidades.ArCondicionado;
import services.ArCondicionadoService;

@ManagedBean(name = "arCondicionadoBean")
@ViewScoped
public class ArCondicionadoBean {
	private Integer idParaEncontrar; // sera utilizado para a edicao
	private ArCondicionado arCondicionado = new ArCondicionado();
	private List<ArCondicionado> arCondicionados;
	
	public ArCondicionado getArCondicionado() {
		return arCondicionado;
	}
	public void setArCondicionado(ArCondicionado arCondicionado) {
		this.arCondicionado = arCondicionado;
	}
	
	public List<ArCondicionado> getArCondicionados() { 
		arCondicionados = ArCondicionadoDAO.listarTODOS();
		return arCondicionados;
	}
	public void setArCondicionados(List<ArCondicionado> arCondicionados) {
		this.arCondicionados = arCondicionados;
	}
	public Integer getIdParaEncontrar() {
		return idParaEncontrar;
	}
	public void setIdParaEncontrar(Integer idParaEncontrar) {
		this.idParaEncontrar = idParaEncontrar;
	}
	
	public void salvar()
	{
		try {			
			// aplicar o serviço as logica da temperatura
			ArCondicionadoDAO.salvar(
				ArCondicionadoService.
					defineTemperaturas(arCondicionado)
			);
			System.out.println("salvo");
			FacesMessage msg = new FacesMessage("Sucesso", "Ar condicionado criado.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (IllegalArgumentException e) {
			 System.err.println("Erro ao salvar ArCondicionado: " + e.getMessage());
			FacesMessage msg = new FacesMessage("Erro ao salvar: ", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
//	define o ar condicionado selecionado na pagina de edicao
	public void carregarArCondicionado() {
	    this.arCondicionado = ArCondicionadoDAO.encontrar(getIdParaEncontrar());
	}

	public void aumentarTemperatura()
	{
		try {			
			// pega temperatura do objeto selecionado, de acordo com a acao atualiza 
//			a temperatura
			ArCondicionadoDAO.definirTemperatura(arCondicionado, 1);
			FacesMessage msg = new FacesMessage("Sucesso", "Temperatura aumentada.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (IllegalArgumentException e) {
			System.err.println("Erro ao aumentar temperatura: " + e.getMessage());
			FacesMessage msg = new FacesMessage("Erro ao aumentar temperatura: ", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void diminuirTemperatura()
	{
		try {			
			// pega temperatura do objeto selecionado, de acordo com a acao atualiza 
//			a temperatura
			ArCondicionadoDAO.definirTemperatura(arCondicionado, 0);	
			FacesMessage msg = new FacesMessage("Sucesso", "Temperatura diminuida.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (IllegalArgumentException e) {
			System.err.println("Erro ao diminuir temperatura: " + e.getMessage());
			FacesMessage msg = new FacesMessage("Erro ao diminuir temperatura: ", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public String redirecionarParaMaiorManutencao() {
	    Integer id = ArCondicionadoDAO.buscarMaiorDataManutencao().getId();
	    return "maiorManutencao.xhtml?id=" + id + "&faces-redirect=true";
	}
	
	public void manutencao()
	{
		FacesMessage msg = new FacesMessage(
				"Mensagem", 
				ArCondicionadoService.diasRestantesManutencao(arCondicionado)
			);
			FacesContext.getCurrentInstance().addMessage(null, msg);  
	}
	
	public void atualizar() {
        ArCondicionadoDAO.atualizar(arCondicionado);
		FacesMessage msg = new FacesMessage("Atualizar", "ArCondicionado atualizado com sucesso.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
        arCondicionado = new ArCondicionado(); 
        getArCondicionados(); 
    }

	
    public void deletar()
    {
		FacesMessage msg = new FacesMessage("Deletar", arCondicionado.getDescricao() +  " deletado com sucesso.");
		ArCondicionadoDAO.deletar(arCondicionado);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		getArCondicionados();
    }
}
