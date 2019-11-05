package ethos.model.players.packets.commands.admin;

import ethos.model.players.Player;
import ethos.util.Misc;

public class MeteorTest {
	public void execute(Player c, String input) {
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
}
