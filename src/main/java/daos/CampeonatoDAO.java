package daos;

import java.util.List;

import javax.persistence.EntityManager;

import entidades.Campeonato;
import util.JPAUtil;

public class CampeonatoDAO {
	public static void salvar(Campeonato campeonato) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(campeonato);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public static void editar(Campeonato campeonato) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(campeonato);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
    public static void atualizar(Campeonato campeonato) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(campeonato);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar o campeonato: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

	public static void excluir(Campeonato campeonato) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			Campeonato CampeonatoParaRemover = em.find(Campeonato.class, campeonato.getId());
			if (CampeonatoParaRemover != null) {
				em.remove(CampeonatoParaRemover);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public static List<Campeonato> listar() {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			return em.createQuery("SELECT c FROM Campeonato c", Campeonato.class).getResultList();
		} finally {
			em.close();
		}
	}
}
