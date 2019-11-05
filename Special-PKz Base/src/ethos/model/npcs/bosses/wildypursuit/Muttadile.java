package ethos.model.npcs.bosses.wildypursuit;

import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.combat.Hitmark;
import ethos.util.Misc;

public class Muttadile {
	
	public static int specialAmount = 0;
	
	public static void vespulaSpecial(Player player) {
		NPC MUTTADILE = NPCHandler.getNpc(7562);
		
		if (MUTTADILE.isDead) {
			return;
		}
		
		if (MUTTADILE.getHealth().getAmount() < 1400 && specialAmount == 0 ||
				MUTTADILE.getHealth().getAmount() < 1100 && specialAmount == 1 ||
						MUTTADILE.getHealth().getAmount() < 900 && specialAmount == 2 ||
								MUTTADILE.getHealth().getAmount() < 700 && specialAmount == 3 ||
										MUTTADILE.getHealth().getAmount() < 400 && specialAmount == 4 ||
												MUTTADILE.getHealth().getAmount() < 100 && specialAmount == 5) {
				NPCHandler.npcs[MUTTADILE.getIndex()].forceChat("Scarab ATTACK!");
				MUTTADILE.startAnimation(7422);
				MUTTADILE.underAttackBy = -1;
				MUTTADILE.underAttack = false;
				NPCHandler.glodAttack = "SPECIAL";
				specialAmount++;
				PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
				.forEach(p -> {
					p.appendDamage(Misc.random(25) + 13, Hitmark.HIT);
					p.sendMessage("muttadiles hit smashes through your body.");
				});
			}
		}
	
	public static void rewardPlayers(Player player) {
		PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
		.forEach(p -> {
				if (p.getGlodDamageCounter() >= 1) {
					p.sendMessage("@blu@The boss in pursuit has been killed!");
					p.sendMessage("@blu@You receive a Pursuit Crate for damaging the boss!");
					p.getItems().addItemUnderAnyCircumstance(21307, 1);
				} else {
					p.sendMessage("@blu@You didn't do enough damage to the boss!");
				}
				p.setGlodDamageCounter(0);
		});
	}
}
