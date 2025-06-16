package beans;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import daos.UsuarioDAO;
import entidades.Usuario;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean { 

    private Usuario usuario  = new Usuario();
    private List<Usuario> usuarios;
    
    public String salvar() {
    	UsuarioDAO.salvar(usuario);
        usuario = new Usuario();
        return null; 
    }
    
    public void prepararEdicao(Usuario usuarioSelecionado) {
    	usuarioSelecionado.setSenha(null);
        this.usuario = usuarioSelecionado;
    }

    public void editar() {
        UsuarioDAO.atualizar(usuario);
        this.usuario = new Usuario();
        this.usuarios = UsuarioDAO.listarTODOS();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage("Sucesso", "Usuario editado."));
    }

    public void excluir(Usuario usuario) {
    	UsuarioDAO.deletar(usuario);
        this.usuarios = UsuarioDAO.listarTODOS();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage("Sucesso", "Usuario excluído."));
    }
    
    public List<Usuario> getUsuarios() {
    	if(usuarios == null) {
    		usuarios = UsuarioDAO.listarTODOS();
    	}
        return usuarios;
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
    public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
