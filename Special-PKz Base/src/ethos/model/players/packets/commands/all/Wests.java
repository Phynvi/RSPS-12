package ethos.model.players.packets.commands.all;

import java.util.Optional;

import ethos.Server;
import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;
import ethos.util.Misc;

/**
 * Teleport the player to wests.
 * 
 * @author Emiel
 */
public class Wests extends Command {

	@Override
	public void execute(Player c, String input) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		if (c.inClanWars() || c.inClanWarsSafe()) {
			c.sendMessage("@cr10@You can not teleport from here, speak to the doomsayer to leave.");
			return;
		}
		if (c.inWild()) {
			return;
		}
		//c.getPA().spellTeleport(2979, 3597, 0, false);
		for(int meteorsActive = 0; meteorsActive < 15; meteorsActive++)
		{
			int CoordsNumberX = Misc.random(30);
			int CoordsNumberY = Misc.random(25);
			int GALVEK_X = 3200;
			int GALVEK_Y = 3200;
			int CoordsX = Misc.random(1) == 0 ? (GALVEK_X -= CoordsNumberX) : (GALVEK_X += CoordsNumberX) + 5;
			int CoordsY = Misc.random(1) == 0 ? (GALVEK_Y -= CoordsNumberY) : (GALVEK_X += CoordsNumberY) + 5;
			c.getPA().createPlayersProjectile(CoordsX, CoordsY, CoordsX, CoordsY, 50, 80, 1491, 35, 31, 0, 65, 0);
			
		}
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teleports you to this PK hotspot");
	}

}
