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
import cs4347.jdbcGame.dao.GameDAO;
import cs4347.jdbcGame.dao.impl.CreditCardDAOImpl;
import cs4347.jdbcGame.dao.impl.GameDAOImpl;
import cs4347.jdbcGame.dao.PlayerDAO;
import cs4347.jdbcGame.dao.impl.PlayerDAOImpl;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.entity.Game;
import cs4347.jdbcGame.entity.Player;
import cs4347.jdbcGame.services.GameService;
import cs4347.jdbcGame.util.DAOException;

public class GameServiceImpl implements GameService
{

    private DataSource dataSource;

    public GameServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @Override
    public Game create(Game game) throws DAOException, SQLException
    {
    	Connection connection = dataSource.getConnection();
    	GameDAO gameDAO = new GameDAOImpl();
        try {
            connection.setAutoCommit(false);
            Game p1 = gameDAO.create(connection, game);
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
    public Game retrieve(long gameID) throws DAOException, SQLException
    {

    	GameDAO gameDAO = new GameDAOImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            Game p1 =gameDAO.retrieve(connection, gameID);
            if(p1==null)
            {
           	 return null;
            }
            else 
            {
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
    public int update(Game game) throws DAOException, SQLException
    {
    	GameDAO gameDAO = new GameDAOImpl();
    	Connection connection = dataSource.getConnection();
         try {
             connection.setAutoCommit(false);
             gameDAO.update(connection,game);
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
    public int delete(long gameID) throws DAOException, SQLException
    {
    	GameDAO gameDAO = new GameDAOImpl();
    	 Connection connection = dataSource.getConnection();
         try {
             connection.setAutoCommit(false);
             Game p1 = gameDAO.retrieve(connection, gameID);
             if(p1==null)
             {
             	System.out.println("Could not find the player in the system!");
             	return 0;
             }
             else 
             {
            	 gameDAO.delete(connection, gameID);
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
    	GameDAO gameDAO = new GameDAOImpl();
    	Connection connection = dataSource.getConnection();
        try {
       	 connection.setAutoCommit(false);
       	 int count;
            count = gameDAO.count(connection);
            
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
    public List<Game> retrieveByTitle(String titlePattern) throws DAOException, SQLException
    {
    	GameDAO gameDAO = new GameDAOImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            List<Game> result = new ArrayList<Game>();
            result = gameDAO.retrieveByTitle(connection, titlePattern);
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

    @Override
    public List<Game> retrieveByReleaseDate(Date start, Date end) throws DAOException, SQLException
    {
    		GameDAO gameDAO = new GameDAOImpl();
         Connection connection = dataSource.getConnection();
         try {
             connection.setAutoCommit(false);
             List<Game> result = new ArrayList<Game>();
             result = gameDAO.retrieveByReleaseDate(connection, start, end);
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
}
