package daos;

import java.util.List;
import javax.persistence.EntityManager;
import entidades.Usuario;
import util.JPAUtil;

public class UsuarioDAO {
    public static void salvar(Usuario Usuario) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(Usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar Usuario: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    public static void atualizar(Usuario Usuario) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(Usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar Usuario: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public static void deletar(Usuario Usuario) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
            Usuario UsuarioEncontrada = em.find(Usuario.class, Usuario.getId());
            em.remove(UsuarioEncontrada);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao excluir Usuario: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
  
	public static List<Usuario> listarTODOS() {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
        	return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar Usuarios: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public static Usuario encontrar(Integer id) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            return em.find(Usuario.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Usuario por ID: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public static Usuario findByLoginSenha(String login, String senha) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha", Usuario.class)
                     .setParameter("login", login)
                     .setParameter("senha", senha)
                     .getSingleResult();
        } catch (Exception e) {
            return null; // Retorna null se não encontrar
        } finally {
            em.close();
        }
    }
}
