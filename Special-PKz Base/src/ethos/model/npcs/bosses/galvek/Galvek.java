package ethos.model.npcs.bosses.galvek;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import ethos.Server;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.Position;
import ethos.model.players.combat.CombatType;
import ethos.model.players.combat.Damage;
import ethos.model.players.combat.Hitmark;
import ethos.util.Misc;
import ethos.world.objects.GlobalObject;

/**
 * @author Kal
 *
 */
public class Galvek {

	/**
	 * NPC IDS
	 */
	ArrayList<int[][]> tileCoords = new ArrayList<int[][]>();
	int[][] randomCoord, randomPoolCoordOne, randomPoolCoordTwo;
	int[] coord, poolCoordOne, poolCoordTwo;
	int x, y, x1, y1, x2, y2;
	NPC galvek = null;
	public static int GALVEK_STAGE_ONE = 8095;
	public static int GALVEK_STAGE_TWO = 8096;
	public static int GALVEK_STAGE_THREE = 8097;
	public static int GALVEK_STAGE_FOUR = 8098;
	public static int TSUNAMI = 8099;
	public static int METEOR_PROJECTILE = 1;
	public static int GALVEK_OFFSPRING = 127;
	private Stage currentStage;
	public boolean meteors = false;
	public int stage = 0;
	public int GALVEK_X = 3300;
	public int GALVEK_Y = 3300;
	public int meteorsActive = 0;
	public int HEALTH_POOL_OBJECT = 0;
	public int LIFE_SIPHON_OBJECT = 0;
	public boolean offspringsSummoned = true;
	private Player player;
	public int offspringCount = 0;
	public boolean attackable = true;
	Position poolPositionOne = null;
	Position poolPositionTwo = null;
	int totalPlayersDamaged;
	public final int MAX_METEORS = 15;
	int damage = 0;
	int amount = 0;
	ArrayList<Position> lifeSiphonCoords = new ArrayList<Position>();
	public ArrayList<Player> localPlayers = new ArrayList<Player>();
	private Stopwatch poolTimer = Stopwatch.createStarted();
	private Stopwatch siphonTimer = Stopwatch.createStarted();
	private Stopwatch meteorTimer = Stopwatch.createStarted();
	int minimumX = Boundary.GALVEK_LAIR.getMinimumX();
	int maximumX = Boundary.GALVEK_LAIR.getMaximumX();
	int minimumY = Boundary.GALVEK_LAIR.getMinimumY();
	int maximumY = Boundary.GALVEK_LAIR.getMaximumY();

	public void Start() {
		for(int i = minimumX; i <= maximumX; i++) {
			for(int j = minimumY; j <= maximumY; j++) {
				tileCoords.add(new int [][] { { i, j}});
		}
	}
		SetStage(Stage.ONE);
		galvek.attackType = CombatType.MAGE;
	}

	/**
	 * Spawns five random minions, one will be the "key"
	 */
	public void SpawnMinions() {
		NPCHandler.spawnNpc(GALVEK_OFFSPRING, 2984, (4377 + Misc.random(10)), 2, 0, 35, 13, 100, 120);
		NPCHandler.spawnNpc(GALVEK_OFFSPRING, 2982, (4377 - Misc.random(10)), 2, 0, 35, 13, 100, 120);
		NPCHandler.spawnNpc(GALVEK_OFFSPRING, 2982, (4350 + Misc.random(30)), 2, 0, 35, 13, 100, 120);
		NPCHandler.spawnNpc(GALVEK_OFFSPRING, 2982, (4390 - Misc.random(30)), 2, 0, 35, 13, 100, 120);
		NPCHandler.spawnNpc(GALVEK_OFFSPRING, 2982, (4377 + Misc.random(20)), 2, 0, 35, 13, 100, 120);
		offspringCount = 0;
		offspringsSummoned = true;
		attackable = false;
	}

	public enum Stage {
		ONE, TWO, THREE, FOUR
	}

	public void SetStage(Stage stage) {
		this.currentStage = stage;
		HandleNewStage(stage);
	}

	public Stage GetStage() {
		return this.currentStage;
	}

	private void HandleNewStage(Stage stage) {
		switch (stage) {
		case ONE:
			meteors = true;
			break;
		case TWO:
			break;
		case THREE:
			break;
		case FOUR:
			break;
		}
	}

	public void LaunchMeteor() {
		randomCoord = tileCoords.get(Misc.random(tileCoords.size() - 1));
		coord = randomCoord[Misc.random(randomCoord.length - 1)];
		x = coord[0];
		y = coord[1];
		//player.getPA().createPlayersStillGfx(135, x, y, player.heightLevel, 5);
		//meteorTimer.start();
		meteorsActive++;
		tileCoords.remove(randomCoord);
//		if (meteorTimer.elapsed(TimeUnit.SECONDS) >= 3) {
//			if (!meteors)
//				return;
//
//			PlayerHandler.nonNullStream()
//					.filter(player -> player.WithinDistance(player.absX, player.absY, GALVEK_X, GALVEK_Y, 100))
//					.forEach(player -> {
//						if (player.getPosition().getX() == x && player.getPosition().getY() == y) {
//							player.appendDamage(15 + Misc.random(15), Hitmark.HIT);
//							player.gfx100(1);
//						}
//						tileCoords.add(randomCoord);
//						meteorTimer.stop();
//						meteorTimer.reset();
//						meteorsActive--;
//					});
//		}
	}

	public void HandleGalvekAttack() {
		player.sendMessage("the attack type is: " + galvek.attackType);
		int random = Misc.random(10) + 1;
		switch (galvek.attackType) {
		case MAGE:
			if (random < 2)
				galvek.attackType = CombatType.DRAGON_FIRE;
			else if (random >= 6)
				galvek.attackType = CombatType.RANGE;
			else if (random == 10)
				galvek.attackType = CombatType.SPECIAL;
		case RANGE:
			if (random < 2)
				galvek.attackType = CombatType.DRAGON_FIRE;
			else if (random >= 6)
				galvek.attackType = CombatType.MAGE;
			else if (random == 10)
				galvek.attackType = CombatType.SPECIAL;
			break;
		case DRAGON_FIRE:
			if (random > 1)
				galvek.attackType = CombatType.MAGE;
			else if (random >= 6)
				galvek.attackType = CombatType.RANGE;
			else if (random == 10)
				galvek.attackType = CombatType.SPECIAL;
			break;
		case SPECIAL:
			switch (GetStage()) {
			case ONE:
				break;
			case TWO:
				if (random >= 5)
					HandleSmiteAttack();
				else if (random < 5)
					HandleLifeSiphon();
				break;
			case THREE:
				// TODO: Stage three special
				HandleSmiteAttack();
				break;
			case FOUR:
				break;
			}
			break;
		default:
			galvek.attackType = CombatType.MAGE;
			break;
		}
	}

	public void HandleSmiteAttack() {
		Player randomPlayer = localPlayers.get(Misc.random(localPlayers.size()));
		int drain = randomPlayer.playerLevel[5] *= 0.6f;
		randomPlayer.playerLevel[5] -= drain;
		randomPlayer.sendMessage("@red@ You lost " + drain + " prayer points!");
	}

	/**
	 * Spawns life siphon under the player, after 3 seconds if the player does not
	 * move the player is killed.
	 */
	public void HandleLifeSiphon() {
		PlayerHandler.nonNullStream()
				.filter(player -> player.WithinDistance(player.absX, player.absY, GALVEK_X, GALVEK_Y, 100))
				.forEach(player -> {
					Server.getGlobalObjects()
							.add(new GlobalObject(LIFE_SIPHON_OBJECT, player.getX(), player.getY(), 0, 0, 10, 5, -1));
					lifeSiphonCoords.add(player.getPosition());
					siphonTimer.start();
					if (siphonTimer.elapsed(TimeUnit.SECONDS) >= 3) {
						for (int i = 0; i < lifeSiphonCoords.size(); i++) {
							Position siphon = lifeSiphonCoords.get(i);
							if (siphon == player.getPosition()) {
								player.gfx0(10);
								player.appendDamage(player.getMaximumHealth(), Hitmark.HIT);
								lifeSiphonCoords.remove(i);
								siphonTimer.stop();
								siphonTimer.reset();
							}
						}
					}
				});
	}

	// TODO:
	public void HandleDecoyGalvek() {

	}

	// TODO:
	public void HandleTsunami() {

	}

	public void AttackGalvek(Player attacker, Damage damage) {
		if (!attackable) {
			damage.setAmount(0);
			attacker.sendMessage("Something is blocking your attack, perhaps there's something I need to do first.");
		}
	}

	public void SpawnRandomPool(int randomOne, int randomTwo) {
		randomPoolCoordOne = tileCoords.get(Misc.random(tileCoords.size() - 1));
		randomPoolCoordTwo = tileCoords.get(Misc.random(tileCoords.size() - 1));
		poolCoordOne = randomPoolCoordOne[Misc.random(randomPoolCoordOne.length - 1)];
		poolCoordTwo = randomPoolCoordTwo[Misc.random(randomPoolCoordTwo.length - 1)];
		x1 = poolCoordOne[0];
		y1 = poolCoordOne[1];
		x2 = poolCoordTwo[0];
		y2 = poolCoordTwo[1];
		poolPositionOne = new Position(x, y, 0);
		poolPositionTwo = new Position(x, y, 0);
		Server.getGlobalObjects().add(new GlobalObject(HEALTH_POOL_OBJECT, x1, y1, 0, 0, 10, 50, -1));
		Server.getGlobalObjects().add(new GlobalObject(HEALTH_POOL_OBJECT, x2, y2, 0, 0, 10, 50, -1));
		poolTimer.reset();
		poolTimer.start();
	}

	public void HandleHealthPools() {
		SpawnRandomPool(15, 15);
		int randomDamage = (Misc.random(20) + 1);
		PlayerHandler.nonNullStream().filter(player -> player.WithinDistance(player.absX, player.absY,
				poolPositionOne.getX(), poolPositionOne.getY(), 100)).forEach(player -> {
					if (poolTimer.elapsed(TimeUnit.SECONDS) >= 3) {
						if (player.getPosition() != poolPositionOne && player.getPosition() != poolPositionTwo)
							totalPlayersDamaged++;

						if (Boundary.isIn(player, Boundary.GALVEK_LAIR)) {
							if (totalPlayersDamaged <= 0) {
								return;
							}
							player.appendDamage(randomDamage, Hitmark.HIT);
							int totalPlayerDamage = totalPlayersDamaged * randomDamage;
							int healAmount = totalPlayerDamage * 5;
							int health = GetHealth();
							SetHealth((health + healAmount));
							poolTimer.stop();
						}
					}
				});
	}
	

	public int GetHealth() {
		int currentHealth = galvek.getHealth().getAmount();
		return currentHealth;
	}

	public void SetHealth(int health) {
		if (galvek == null) {
			return;
		}
		galvek.getHealth().setAmount(health);
	}

}
