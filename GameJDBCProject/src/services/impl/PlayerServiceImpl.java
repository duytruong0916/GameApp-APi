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
package cs4347.jdbcGame.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcGame.dao.CreditCardDAO;
import cs4347.jdbcGame.dao.PlayerDAO;
import cs4347.jdbcGame.dao.impl.CreditCardDAOImpl;
import cs4347.jdbcGame.dao.impl.PlayerDAOImpl;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.entity.Player;
import cs4347.jdbcGame.services.PlayerService;
import cs4347.jdbcGame.util.DAOException;

public class PlayerServiceImpl implements PlayerService
{
    private DataSource dataSource;

    public PlayerServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @Override
    public Player create(Player player) throws DAOException, SQLException
    {
        if (player.getCreditCards() == null || player.getCreditCards().size() == 0) 
        {
            throw new DAOException("Player must have at lease one CreditCard");
        }

        PlayerDAO playerDAO = new PlayerDAOImpl();
        CreditCardDAO ccDAO = new CreditCardDAOImpl();

        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            Player p1 = playerDAO.create(connection, player);
            Long playerID = p1.getId();
            for (CreditCard creditCard : player.getCreditCards()) {
                creditCard.setPlayerID(playerID);
                ccDAO.create(connection, creditCard, playerID);
            }
            connection.commit();
            return p1;
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        }
        finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public Player retrieve(Long playerID) throws DAOException, SQLException
    {
    	 PlayerDAO playerDAO = new PlayerDAOImpl();
    	 CreditCardDAO ccDAO = new CreditCardDAOImpl();
         Connection connection = dataSource.getConnection();
         try {
             connection.setAutoCommit(false);
             Player p1 = playerDAO.retrieve(connection, playerID);
             if(p1==null)
             {
            	 return null;
             }
             else 
             {
            	 p1.setCreditCards(ccDAO.retrieveCreditCardsForPlayer(connection,playerID));
            	 connection.commit();
            	 return p1;
             }
         }
          catch (Exception ex) {
             connection.rollback();
             throw ex;
         }
         finally {
             if (connection != null) {
                 connection.setAutoCommit(true);
             }
             if (connection != null && !connection.isClosed()) {
                 connection.close();
             }
         }
         
}

    @Override
    public int update(Player player) throws DAOException, SQLException
    {
    	PlayerDAO playerDAO = new PlayerDAOImpl();
    	CreditCardDAO ccDAO = new CreditCardDAOImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            playerDAO.update(connection, player);
            
    
            Player p1 = playerDAO.retrieve(connection, player.getId());
            p1.setCreditCards(ccDAO.retrieveCreditCardsForPlayer(connection, player.getId()));
            List<CreditCard> resultlist = new ArrayList<CreditCard>();
            Player p =retrieve(player.getId());
            resultlist = player.getCreditCards();
            p.setCreditCards(resultlist);
           
           	
           	connection.commit();
           	 return 1;
            
        }
         catch (Exception ex) {
            connection.rollback();
            throw ex;
        }
        finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        
    }

    @Override
    public int delete(Long playerID) throws DAOException, SQLException
    {
    	PlayerDAO playerDAO = new PlayerDAOImpl();
   	 	CreditCardDAO ccDAO = new CreditCardDAOImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            Player p1 = playerDAO.retrieve(connection, playerID);
            if(p1==null)
            {
            	System.out.println("Could not find the player in the system!");
            	return 0;
            }
            else 
            {
             playerDAO.delete(connection, playerID);
           	 ccDAO.deleteForPlayer(connection,playerID);
           	 connection.commit();
           	 return 1;
            }
        }
         catch (Exception ex) {
            connection.rollback();
            throw ex;
        }
        finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        
    }

    @Override
    public int count() throws DAOException, SQLException
    {
    	PlayerDAO playerDAO = new PlayerDAOImpl();
    	Connection connection = dataSource.getConnection();
         try {
        	 connection.setAutoCommit(false);
        	 int count;
             count = playerDAO.count(connection);
             
             return count;
         } catch (Exception ex) {
             connection.rollback();
             throw ex;
         }
         finally {
             if (connection != null) {
                 connection.setAutoCommit(true);
             }
             if (connection != null && !connection.isClosed()) {
                 connection.close();
             }
         }
    }

    @Override
    public List<Player> retrieveByJoinDate(Date start, Date end) throws DAOException, SQLException
    {
    	 PlayerDAO playerDAO = new PlayerDAOImpl();
    	 //CreditCardDAO ccDAO = new CreditCardDAOImpl();
         Connection connection = dataSource.getConnection();
         try {
             connection.setAutoCommit(false);
             List<Player> result = new ArrayList<Player>();
             result = playerDAO.retrieveByJoinDate(connection, start, end);
             if(result==null)
             {	
            	 System.out.println("No player were found!");
            	 return null;
             }
             else 
             {
            	 connection.commit();
            	 return result;
             }
         }
          catch (Exception ex) {
             connection.rollback();
             throw ex;
         }
         finally {
             if (connection != null) {
                 connection.setAutoCommit(true);
             }
             if (connection != null && !connection.isClosed()) {
                 connection.close();
             }
         }
    }

    /**
     * Used for debugging and testing purposes.
     */
    @Override
    public int countCreditCardsForPlayer(Long playerID) throws DAOException, SQLException
    {
    	Connection connection = dataSource.getConnection();
         try {
        	 connection.setAutoCommit(false);
        	 Player p1 = retrieve(playerID);
        	 int count;
        	 if(p1==null)
        	 {
        		 System.out.println("Player does not exist");
        		 return count=0;
        	 }
        	 else
        	 {
        		 count = p1.getCreditCards().size();
        		 return count;
        	 }
         	} catch (Exception ex) {
             connection.rollback();
             throw ex;
         }
         finally {
             if (connection != null) {
                 connection.setAutoCommit(true);
             }
             if (connection != null && !connection.isClosed()) {
                 connection.close();
             }
         }
    }

}
