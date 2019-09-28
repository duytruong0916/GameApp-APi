/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of all team members for academic dishonesty. 
 */
package cs4347.gameJpaProject.services.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import cs4347.gameJpaProject.entity.Game;
import cs4347.gameJpaProject.services.GameService;
import cs4347.gameJpaProject.util.DAOException;

public class GameServiceImpl implements GameService
{
    @PersistenceContext 
    private EntityManager em; 
    
    public GameServiceImpl(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void create(Game game) throws DAOException, SQLException
    {
        try {
            em.getTransaction().begin();
            em.persist(game);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public Game retrieve(Long gameID) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			Game game = em.find(Game.class, gameID);
			em.getTransaction().commit();
			return game;
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public void update(Game g1) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			Game e2 = em.find(Game.class, g1.getId());
			e2.setTitle(g1.getTitle());
			e2.setDescription(g1.getDescription());
			e2.setReleaseDate(g1.getReleaseDate());
			e2.setVersion(g1.getVersion());
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public void delete(Long gameID) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			Game game = em.find(Game.class, gameID);
			em.remove(game);
			em.getTransaction().commit();
	
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public Long count() throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			Long count= (Long) em.createQuery("SELECT count(*) FROM Game").getSingleResult();  
			em.getTransaction().commit();
			return count;
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    
    		      
    }

    @Override
    public List<Game> retrieveByTitle(String titlePattern) throws DAOException, SQLException
    {
    	em.getTransaction().begin();
    	List<Game> game = (List<Game>)em.createQuery("from Game as g where g.title = :title")
				.setParameter("title", titlePattern)
				.getResultList();
		em.getTransaction().commit();
		return game;
    }
    @Override
    public List<Game> retrieveByReleaseDate(Date start, Date end) throws DAOException, SQLException
    {
    	em.getTransaction().begin();
    	List<Game> game = (List<Game>)em.createQuery("from Game as g where g.releaseDate between :start and :end")
				.setParameter("start", start)
				.setParameter("end", end)
				.getResultList();
		em.getTransaction().commit();
		return game;
    }


}
