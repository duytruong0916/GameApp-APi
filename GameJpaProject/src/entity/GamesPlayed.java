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
package cs4347.gameJpaProject.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "gamesplayed")
public class GamesPlayed
{
    private Long id;
    private Player player; 
    private Game game; 
    private Date timeFinished;
    private int score;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="player_id", unique=false) // FK column to be generated.
    public Player getPlayer()
    {
        return player;
    } 

    public void setPlayer(Player player)
    {
        this.player = player;
    }
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="game_id", unique=false) // FK column to be generated.
    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public Date getTimeFinished()
    {
        return timeFinished;
    }

    public void setTimeFinished(Date timeFinished)
    {
        this.timeFinished = timeFinished;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

}
