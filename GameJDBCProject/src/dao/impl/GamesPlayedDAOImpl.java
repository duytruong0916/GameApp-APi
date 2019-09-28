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
package cs4347.jdbcGame.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcGame.dao.GamesPlayedDAO;
//import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.util.DAOException;

public class GamesPlayedDAOImpl implements GamesPlayedDAO
{
	 private static final String insertSQL = "INSERT INTO GamesPlayed (PlayerID, GameID, timeFinished, score) VALUES (?, ?, ?, ?);";
    @Override
    public GamesPlayed create(Connection connection, GamesPlayed gamesPlayed) throws SQLException, DAOException
    {
    	if (gamesPlayed.getId() != null) {
            throw new DAOException("Trying to insert Game with NON-NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, gamesPlayed.getPlayerID());
            ps.setLong(2,  gamesPlayed.getGameID());
            ps.setDate(3, new java.sql.Date( gamesPlayed.getTimeFinished().getTime()));
            ps.setInt(4,  gamesPlayed.getScore());
            ps.executeUpdate();

            // Copy the assigned ID to the game instance.
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            gamesPlayed.setId((long) lastKey);
            return gamesPlayed;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
   final static String selectIdSQL = "select id, PlayerID, GameID,timeFinished,score from GamesPlayed where id=?;";
    @Override
    public GamesPlayed retrieveID(Connection connection, Long gamePlayedID) throws SQLException, DAOException
    {
    	if (gamePlayedID == null) {
            throw new DAOException("Trying to retrieve Game with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectIdSQL);
            ps.setLong(1, gamePlayedID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

          GamesPlayed gamesOwned = extractFromRS(rs);
            return gamesOwned;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }

    }
    final static String retrieveplayergameID = "select id, PlayerID, GameID,timeFinished,score from GamesPlayed where PlayerID=? and GameID=?;";

    @Override
    public List<GamesPlayed> retrieveByPlayerGameID(Connection connection, Long playerID, Long gameID)
            throws SQLException, DAOException
    {
    	List<GamesPlayed> result = new ArrayList<GamesPlayed>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(retrieveplayergameID);
            ps.setLong(1, playerID);
            ps.setLong(2, gameID); 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
           	 GamesPlayed gamesPlayed = extractFromRS(rs);
                result.add(gamesPlayed);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    final static String retrieveplayerID = "select id, PlayerID, GameID,timeFinished,score from GamesPlayed where PlayerID=?;";

    @Override
    public List<GamesPlayed> retrieveByPlayer(Connection connection, Long playerID) throws SQLException, DAOException
    {
    	 List<GamesPlayed> result = new ArrayList<GamesPlayed>();
         PreparedStatement ps = null;
         try {
             ps = connection.prepareStatement(retrieveplayerID);
             ps.setLong(1, playerID);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
            	 GamesPlayed gamesPlayed = extractFromRS(rs);
                 result.add(gamesPlayed);
             }
             return result;
         }
         finally {
             if (ps != null && !ps.isClosed()) {
                 ps.close();
             }
         }
    }
    final static String retrievegameID = "select id, PlayerID, GameID, timeFinished,score from GamesPlayed where GameID= ?;";
    @Override
    public List<GamesPlayed> retrieveByGame(Connection connection, Long gameID) throws SQLException, DAOException
    {
    	List<GamesPlayed> result = new ArrayList<GamesPlayed>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(retrievegameID);
            ps.setLong(1, gameID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            GamesPlayed gamesplayed = extractFromRS(rs);
                result.add(gamesplayed);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    final static String updateSQL = "UPDATE GamesPlayed SET PlayerID=?, GameID=?, timeFinished=?, score=? WHERE id = ?;";

    @Override
    public int update(Connection connection, GamesPlayed gamesPlayed) throws SQLException, DAOException
    {
    	Long id = gamesPlayed.getId();
        if (id == null) 
        {
            throw new DAOException("Trying to update Game with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(updateSQL);
            ps.setLong(1,  gamesPlayed.getPlayerID());
            ps.setLong(2,  gamesPlayed.getGameID());
            ps.setDate(3, new java.sql.Date(gamesPlayed.getTimeFinished().getTime()));
            ps.setInt(4,  gamesPlayed.getScore());
            ps.setLong(5, id);

            int rows = ps.executeUpdate();
            return rows;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    final static String deleteSQL = "delete from GamesPlayed where ID= ?;";
    @Override
    public int delete(Connection connection, Long gamePlayedID) throws SQLException, DAOException
    {
    	if (gamePlayedID== null) {
            throw new DAOException("Trying to delete Game with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(deleteSQL);
            ps.setLong(1, gamePlayedID);

            int rows = ps.executeUpdate();
            return rows;
        }
        finally {
            if (ps != null && !ps.isClosed()) 
            {
                ps.close();
            }
        }
    }
    final static String countSQL = "select count(*) from game";
    @Override
    public int count(Connection connection) throws SQLException, DAOException
    {

    	PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(countSQL);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new DAOException("No Count Returned");
            }
            int count = rs.getInt(1);
            return count;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    private GamesPlayed extractFromRS(ResultSet rs) throws SQLException
    {
        GamesPlayed n = new  GamesPlayed();
        n .setId(rs.getLong("id"));
        n .setPlayerID(rs.getLong("PlayerID"));
        n .setGameID(rs.getLong("GameID"));
        n .setTimeFinished(rs.getDate("timeFinished"));
        n . setScore(rs.getInt("score"));
        return  n ;
    }

}
