package ethos.model.minigames.lighthouse;

import org.apache.commons.lang3.Range;

import ethos.Server;
import ethos.event.Event;
import ethos.model.content.instances.SingleInstancedArea;
import ethos.model.npcs.NPC;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.util.Misc;

/**
 * Lighthouse, Nylocas Vasilias minigame
 * 
 * @author Matt
 */
public final class NylocasVasilias extends SingleInstancedArea {

	private static final int START_X = 2507, START_Y = 3642;

	public static final Range<Integer> RANGE_OF_IDS = Range.between(8354, 8357);

	private static final int SPAWN_X = 3038, SPAWN_Y = 5343;

	private static final int HITPOINTS = 150, ATTACK = 400, DEFENCE = 700, MAXHIT = 60;

	/**
	 * The {@link NPC#npcType} value of the transformed non-playable character.
	 */
	private int npcId = RANGE_OF_IDS.getMinimum();

	public NylocasVasilias(Player player, Boundary boundary, int height) {
		super(player, boundary, height);
	}

	/**
	 * Constructs the content by creating an event that will execute after 5 cycles (3 seconds).
	 */
	public void init() {
		player.getPA().movePlayer(START_X, START_Y, height);
		player.sendMessage("Prepare to fight the Nylocas Vasilias..");

		Server.getEventHandler().submit(new Event<Player>("Nylocas_Vasilias", player, 5) {

			@Override
			public void execute() {
				if (player == null) {
					super.stop();
					return;
				}
				Server.npcHandler.spawnNpc(player, 8354, SPAWN_X, SPAWN_Y, height, 0, HITPOINTS, MAXHIT, ATTACK, DEFENCE, true, false);
				npcId = 8354;
				super.stop();
			}

		});
	}

	/**
	 * Attempts to randomly transformation the non-playable character into another by randomly generating a value from the range of values defined by {@link #RANGE_OF_IDS}.
	 * 
	 * If the randomly generated value is that of the current transformed npc id then the transformation is aborted.
	 * 
	 * @param npc the non-playable character we are transforming.
	 */
	public final void transformation(NPC npc) {
		int probability = Misc.random(100);

		if (probability < 80) {
			return;
		}
		int randomTransformationId = Misc.random(RANGE_OF_IDS);

		if (randomTransformationId == npcId) {
			return;
		}
		npc.requestTransform(randomTransformationId);
	}

	/**
	 * Disposes of the content by moving the player and finalizing and or removing any left over content.
	 * 
	 * @param dispose the type of dispose
	 */
	public final void end(DisposeType dispose) {
		if (player == null) {
			return;
		}
		player.getPA().movePlayer(2509, 3639, 0);
		if (dispose == DisposeType.COMPLETE) {
			player.sendMessage("You have slain the Nylocas Vasilias.");
			player.sendMessage("You have been rewarded a rusty casket.");
			player.getItems().addItemUnderAnyCircumstance(3849, 1);
		} else if (dispose == DisposeType.INCOMPLETE) {
			player.sendMessage("You were unable to slay the Nylocas Vasilias.");
		}
	}

	@Override
	public void onDispose() {
		end(DisposeType.INCOMPLETE);
	}

}