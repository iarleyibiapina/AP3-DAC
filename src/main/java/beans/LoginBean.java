package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import daos.UsuarioDAO;
import entidades.Usuario;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
    private String login;
    private String senha;
    private Usuario usuarioLogado;

    public String entrar() {
        usuarioLogado = UsuarioDAO.findByLoginSenha(login, senha);
        if (usuarioLogado != null) {
            return "opcoes?faces-redirect=true"; 
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login ou senha inválidos."));
            return null; 
        }
    }
    
    public String logout()
    {
    	return "login?faces-redirect=true";
    }
    
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}