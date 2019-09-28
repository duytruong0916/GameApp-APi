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
package cs4347.gameJpaProject.services;

import java.sql.SQLException;
import java.util.List;

import cs4347.gameJpaProject.entity.GamesOwned;
import cs4347.gameJpaProject.util.DAOException;

public interface GamesOwnedService
{
    void create(GamesOwned gamesOwned) throws DAOException, SQLException;

    GamesOwned retrieveByID(Long gamesOwnedID) throws DAOException, SQLException;

    GamesOwned retrievePlayerGameID(Long playerID, Long gameID) throws DAOException, SQLException;

    List<GamesOwned> retrieveByGame(Long gameID) throws DAOException, SQLException;

    List<GamesOwned> retrieveByPlayer(Long playerID) throws DAOException, SQLException;

    void update(GamesOwned gamesOwned) throws DAOException, SQLException;

    void delete(Long gamesOwnedID) throws DAOException, SQLException;

    Long count() throws DAOException, SQLException;

}
