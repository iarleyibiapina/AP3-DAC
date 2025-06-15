package beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import daos.UsuarioDAO;
import entidades.Usuario;

@ManagedBean(name = "usuarioBean")
@RequestScoped
public class UsuarioBean {
    private Usuario usuario = new Usuario();
    
    public String salvar() {
    	UsuarioDAO.salvar(usuario);
        usuario = new Usuario(); 
        return null; 
    }
    
    public void prepararEdicao(Usuario usuarioSelecionado) {
        this.usuario = usuarioSelecionado;
    }

    public void editar() {
        UsuarioDAO.atualizar(usuario);
        this.usuario = new Usuario();
//        this.usuarios = UsuarioDAO.listar();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage("Sucesso", "Usuario editado."));
    }

    public void excluir(Usuario usuario) {
    	UsuarioDAO.deletar(usuario);
//        this.usuarios = UsuarioDAO.listarTODOS();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage("Sucesso", "Usuario excluído."));
    }
    
    public List<Usuario> getUsuarios() {
        return UsuarioDAO.listarTODOS();
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
