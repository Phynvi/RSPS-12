package ethos.model.players.packets.commands.admin;

import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Transform a given player into an npc.
 * 
 * @author Emiel
 *
 */
public class Pnpc extends Command {

	@Override
	public void execute(Player c, String input) {
		int npc = Integer.parseInt(input);
		
		if (npc > 8729) {
			c.sendMessage("Max npc id is: 8729");
			return;
		}
		
		c.npcId2 = npc;
		c.isNpc = true;
		c.updateRequired = true;
		c.appearanceUpdateRequired = true;
	}
}
