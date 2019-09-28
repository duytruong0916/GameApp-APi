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
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cs4347.gameJpaProject.entity.Game;
import cs4347.gameJpaProject.entity.GamesOwned;
import cs4347.gameJpaProject.entity.GamesPlayed;
import cs4347.gameJpaProject.entity.Player;
import cs4347.gameJpaProject.services.GamesPlayedService;
import cs4347.gameJpaProject.util.DAOException;

public class GamesPlayedServiceImpl implements GamesPlayedService
{
    @PersistenceContext 
    private EntityManager em; 
    
    public GamesPlayedServiceImpl(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void create(GamesPlayed gamesPlayed) throws DAOException, SQLException
    {
    	try {
            em.getTransaction().begin();
            em.persist(gamesPlayed);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public GamesPlayed retrieveByID(Long gamePlayedID) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			GamesPlayed  GamesPlayed = em.find(GamesPlayed.class, gamePlayedID);
			em.getTransaction().commit();
			return GamesPlayed;
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public List<GamesPlayed> retrieveByPlayerGameID(Long playerID, Long gameID) throws DAOException, SQLException
    {
    	em.getTransaction().begin();
    	Player player = em.find(Player.class,  playerID);
    	Game  game = em.find(Game.class, gameID);
    	List<GamesPlayed> GamesPlayed = (List<GamesPlayed>)em.createQuery("from GamesPlayed as g where g.player= :player and g.game= :game")
    			.setParameter("player", player)
    			.setParameter("game", game)
    			.getResultList();
    		em.getTransaction().commit();
    		return GamesPlayed;
    }

    @Override
    public List<GamesPlayed> retrieveByGame(Long gameID) throws DAOException, SQLException
    {
    	em.getTransaction().begin();
    	Game  game = em.find(Game.class, gameID);
    	List<GamesPlayed> GamesPlayed = (List<GamesPlayed>)em.createQuery("from GamesPlayed as g where g.game= :game")
				.setParameter("game", game)
				.getResultList();
		em.getTransaction().commit();
		return GamesPlayed;
    }

    @Override
    public List<GamesPlayed> retrieveByPlayer(Long playerID) throws DAOException, SQLException
    {
    	em.getTransaction().begin();
    	Player player = em.find(Player.class,  playerID);
    	List<GamesPlayed> GamesPlayed = (List<GamesPlayed>)em.createQuery("from GamesPlayed as g where g.player= :player")
				.setParameter("player", player)
				.getResultList();
		em.getTransaction().commit();
		return GamesPlayed;
    }

    @Override
    public void update(GamesPlayed gp1) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			GamesPlayed p2 = em.find(GamesPlayed.class,  gp1.getId());
			p2.setTimeFinished(gp1.getTimeFinished());
			p2.setScore(gp1.getScore());
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public void delete(Long gamePlayedID) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			GamesPlayed GamesPlayed = em.find(GamesPlayed.class,gamePlayedID); 
			em.remove(GamesPlayed);
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
			Long count= (Long) em.createQuery("SELECT count(*) FROM  GamesPlayed").getSingleResult();  
			em.getTransaction().commit();
			return count;
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

}
