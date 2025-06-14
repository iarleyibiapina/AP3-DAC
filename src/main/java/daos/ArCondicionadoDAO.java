package daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.ArCondicionado;
import util.JPAUtil;

public class ArCondicionadoDAO {
    public static void salvar(ArCondicionado ArCondicionado) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ArCondicionado);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar ArCondicionado: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    public static void atualizar(ArCondicionado ArCondicionado) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(ArCondicionado);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar ArCondicionado: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public static void deletar(ArCondicionado ArCondicionado) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
            ArCondicionado ArCondicionadoEncontrada = em.find(ArCondicionado.class, ArCondicionado.getId());
            em.remove(ArCondicionadoEncontrada);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao excluir ArCondicionado: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
//    Feito
    public static void definirTemperatura(ArCondicionado arCondicionado, Integer acao)
    {
//    	acao pode ser "aumentar" (1) ou "diminuir" (0)
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
//            implementacao 1
//            	ArCondicionado ac = em.find(ArCondicionado.class, arCondicionado.getId());
//            	ac.setTemperaturaAtual(ac.getTemperaturaAtual() + (acao == 1 ? 1 : -1));
//                em.merge(ac);
//				implementacao 2
            Integer novaTemperatura = arCondicionado.getTemperaturaAtual() + (acao == 1 ? 1 : -1);
            em.createNamedQuery("ArCondicionado.definirTemperaturaAtual")
                .setParameter("temperatura", novaTemperatura)
                .setParameter("id", arCondicionado.getId())
                .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao definir temperatura: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
	public static List<ArCondicionado> listarTODOS() {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
        	Query query = em.createNamedQuery("ArCondicionado.listarTODOS");
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar ArCondicionados: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public static ArCondicionado encontrar(Integer id) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            return em.find(ArCondicionado.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar ArCondicionado por ID: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public static ArCondicionado buscarMaiorDataManutencao()
    {   
        EntityManager em = JPAUtil.criarEntityManager();
        try {
        	List<ArCondicionado> resultados = em.createNamedQuery("ArCondicionado.buscarMaioresDataManutencao", ArCondicionado.class)
                     .setMaxResults(1)
                     .getResultList(); 
            return resultados.get(0); // pegando o primeiro elemento, sendo o unico
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar um ar condicionado: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
