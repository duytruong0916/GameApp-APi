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
import cs4347.gameJpaProject.entity.Player;
import cs4347.gameJpaProject.services.GamesOwnedService;
import cs4347.gameJpaProject.util.DAOException;

public class GamesOwnedServiceImpl implements GamesOwnedService
{
    @PersistenceContext 
    private EntityManager em; 
    
    public GamesOwnedServiceImpl(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void create(GamesOwned gamesOwned) throws DAOException, SQLException
    {
    	try {
            em.getTransaction().begin();
            em.persist(gamesOwned);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public GamesOwned retrieveByID(Long gamesOwnedID) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			GamesOwned  gameowned = em.find(GamesOwned.class, gamesOwnedID);
			em.getTransaction().commit();
			return gameowned;
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public GamesOwned retrievePlayerGameID(Long playerID, Long gameID) throws DAOException, SQLException
    {
    	em.getTransaction().begin();
    	Player player = em.find(Player.class,  playerID);
    	Game  game = em.find(Game.class, gameID);
    	GamesOwned gamesowned = (GamesOwned)em.createQuery("from GamesOwned as g where g.player= :player and g.game= :game")
    			.setParameter("player", player)
    			.setParameter("game", game)
    			.getSingleResult();
   
    		em.getTransaction().commit();
    		return gamesowned;
    		
    }

    @Override
    public List<GamesOwned> retrieveByGame(Long gameID) throws DAOException, SQLException
    {
    	em.getTransaction().begin();
    	Game  game = em.find(Game.class, gameID);
    	List<GamesOwned> GamesOwned = (List<GamesOwned>)em.createQuery("from GamesOwned as g where g.game= :game")
				.setParameter("game", game)
				.getResultList();
		em.getTransaction().commit();
		return GamesOwned;
    }

    @Override
    public List<GamesOwned> retrieveByPlayer(Long playerID) throws DAOException, SQLException
    {
    	em.getTransaction().begin();
    	Player player = em.find(Player.class,  playerID);
    	List<GamesOwned> GamesOwned = (List<GamesOwned>)em.createQuery("from GamesOwned as g where g.player= :player")
				.setParameter("player", player)
				.getResultList();
		em.getTransaction().commit();
		return GamesOwned;
    }

    @Override
    public void update(GamesOwned go1) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			GamesOwned p2 = em.find(GamesOwned.class,  go1.getId());
			p2.setPurchaseDate(go1.getPurchaseDate());
			p2.setPurchasePrice(go1.getPurchasePrice());
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public void delete(Long gamesOwnedID) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			GamesOwned GamesOwned = em.find(GamesOwned.class,gamesOwnedID); 
			em.remove(GamesOwned);
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
			Long count= (Long) em.createQuery("SELECT count(*) FROM GamesOwned").getSingleResult();  
			em.getTransaction().commit();
			return count;
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

}
