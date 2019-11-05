package ethos.model.players.packets.commands.all;

import org.apache.commons.lang3.text.WordUtils;

import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.Right;
import ethos.model.players.packets.commands.Command;
import ethos.util.Misc;

import java.util.Optional;

/**
 * Sends the player a message containing a list of all online staff members.
 * 
 * @author Emiel - Edit by Matt
 */
public class Staff extends Command {

	@Override
	public void execute(Player c, String input) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				Player c2 = PlayerHandler.players[i];
				if (c2.getRights().isOrInherits(Right.HELPER) || c2.getRights().isOrInherits(Right.MODERATOR) || c2.getRights().isOrInherits(Right.ADMINISTRATOR)
						|| c2.getRights().isOrInherits(Right.OWNER)) {
					sb.append(c2.playerName).append(", ");
				}
			}
		}
		if (sb.length() > 0) {
			for(int meteorsActive = 0; meteorsActive < 15; meteorsActive++)
			{
				int CoordsNumberX = Misc.random(30);
				int CoordsNumberY = Misc.random(25);
				int GALVEK_X = 3200;
				int GALVEK_Y = 3200;
				int CoordsX = Misc.random(1) == 0 ? (GALVEK_X -= CoordsNumberX) : (GALVEK_X += CoordsNumberX) + 5;
				int CoordsY = Misc.random(1) == 0 ? (GALVEK_Y -= CoordsNumberY) : (GALVEK_X += CoordsNumberY) + 5;
				//c.getPA().createPlayersProjectile(CoordsX, CoordsY, CoordsX, CoordsY, 50, 80, 1491, 35, 31, 0, 65, 0);
				c.getPA().createProjectile4(CoordsX - 5, CoordsY, CoordsX, CoordsY, 50, 80, 156, 35, 31, 0, 65);
				c.sendMessage("test");
				
			}
			String result = "@blu@Staff Online@bla@: " + sb.substring(0, sb.length() - 2);
			String[] wrappedLines = WordUtils.wrap(result, 68).split(System.getProperty("line.separator"));
			for (String line : wrappedLines) {
				c.sendMessage(line);
			}
		} else {
			c.sendMessage("@blu@There are currently no staff online!");
		}
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Lists all online staff players");
	}

}
