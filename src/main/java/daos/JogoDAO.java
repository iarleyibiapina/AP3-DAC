package daos;

import java.util.List;

import javax.persistence.EntityManager;

import entidades.Jogo;
import util.JPAUtil;

public class JogoDAO {
	public static void salvar(Jogo jogo) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(jogo);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public static void editar(Jogo jogo) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(jogo);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
    public static void atualizar(Jogo jogo) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(jogo);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar o jogo: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

	public static void excluir(Jogo jogo) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			Jogo jogoParaRemover = em.find(Jogo.class, jogo.getId());
			if (jogoParaRemover != null) {
				em.remove(jogoParaRemover);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public static List<Jogo> listar() {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			return em.createQuery("SELECT j FROM Jogo j", Jogo.class).getResultList();
		} finally {
			em.close();
		}
	}

	// Método para o item 'k'
	public static List<Jogo> findByTime(String time) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			return em.createNamedQuery("Jogo.findByTime", Jogo.class).setParameter("time", time).getResultList();
		} finally {
			em.close();
		}
	}
}
