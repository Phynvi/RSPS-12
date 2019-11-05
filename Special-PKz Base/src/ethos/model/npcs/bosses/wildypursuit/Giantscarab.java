package ethos.model.npcs.bosses.wildypursuit;

import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.combat.Hitmark;
import ethos.util.Misc;

public class Giantscarab {
	
	public static int specialAmount = 0;
	
	public static void vespulaSpecial(Player player) {
		NPC GIANTSCARAB = NPCHandler.getNpc(1127);
		
		if (GIANTSCARAB.isDead) {
			return;
		}
		
		if (GIANTSCARAB.getHealth().getAmount() < 1400 && specialAmount == 0 ||
				GIANTSCARAB.getHealth().getAmount() < 1100 && specialAmount == 1 ||
						GIANTSCARAB.getHealth().getAmount() < 900 && specialAmount == 2 ||
								GIANTSCARAB.getHealth().getAmount() < 700 && specialAmount == 3 ||
										GIANTSCARAB.getHealth().getAmount() < 400 && specialAmount == 4 ||
												GIANTSCARAB.getHealth().getAmount() < 100 && specialAmount == 5) {
				NPCHandler.npcs[GIANTSCARAB.getIndex()].forceChat("Scarab ATTACK!");
				GIANTSCARAB.startAnimation(1312);
				GIANTSCARAB.underAttackBy = -1;
				GIANTSCARAB.underAttack = false;
				NPCHandler.glodAttack = "SPECIAL";
				specialAmount++;
				PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
				.forEach(p -> {
					p.appendDamage(Misc.random(25) + 13, Hitmark.HIT);
					p.sendMessage("Scarabs hit smashes through your body.");
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
