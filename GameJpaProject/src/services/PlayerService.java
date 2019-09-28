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
import java.util.Date;
import java.util.List;

import cs4347.gameJpaProject.entity.Player;
import cs4347.gameJpaProject.util.DAOException;

public interface PlayerService
{
    void create(Player player) throws DAOException, SQLException;

    Player retrieve(Long playerID) throws DAOException, SQLException;

    void update(Player player) throws DAOException, SQLException;

    void delete(Long playerID) throws DAOException, SQLException;

    int countCreditCardsForPlayer(Long playerID) throws DAOException, SQLException;

    List<Player> retrieveByJoinDate(Date start, Date end) throws DAOException, SQLException;

    /**
     * Provided for testing and debugging.
     */
    Long count() throws DAOException, SQLException;
}
