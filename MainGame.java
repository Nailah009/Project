package oldpackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// Interface untuk aksi monster
interface MonsterActions {
    void basicAttack(Monster enemy);
    void specialAttack(Monster enemy) throws SpecialAttackException;
    void elementalAttack(Monster enemy);
}

abstract class Monster implements MonsterActions {
    protected String name;
    protected int level;
    protected int healthPoints;
    protected int experiencePoints;
    protected int maxHealthPoints;
    protected String element;
    protected boolean evolved;

    private static final Map<String, List<String>> evolutionChart = new HashMap<>() {{
        put("FIRE", Arrays.asList("WIND", "EARTH"));
        put("WIND", Arrays.asList("FIRE", "WATER"));
        put("WATER", Arrays.asList("WIND", "ICE"));
        put("ICE", Arrays.asList("WATER", "EARTH"));
        put("EARTH", Arrays.asList("FIRE", "ICE"));
    }};

    private static final Map<String, String> effectiveChart = new HashMap<>() {{
        put("FIRE", "WIND");
        put("WIND", "EARTH");
        put("EARTH", "WATER");
        put("WATER", "FIRE");
        put("ICE", "WIND");
    }};

    public Monster(String name, String element) {
        this.name = name;
        this.level = level;
        this.healthPoints = healthPoints;
        this.maxHealthPoints = healthPoints;
        this.experiencePoints = 0;
        this.element = element;
        this.evolved = false;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public String getElement() {
        return element;
    }

    public boolean isEvolved() {
        return evolved;
    }

    public void increaseExperience(int points) {
        this.experiencePoints += points;
        if (this.experiencePoints >= 100) {
            levelUp();
        }
    }

    public void heal(int amount) {
        this.healthPoints = Math.min(this.maxHealthPoints, this.healthPoints + amount);
    }

    public void evolve(String newElement) {
        if (!this.evolved && evolutionChart.get(this.element).contains(newElement)) {
            this.element = newElement;
            this.evolved = true;
            System.out.println(this.name + " has evolved into " + newElement + " element!");
        } else if (this.evolved) {
            System.out.println(this.name + " has already evolved.");
        } else {
            System.out.println("Invalid evolution path.");
        }
    }

    protected void levelUp() {
        this.level++;
        this.experiencePoints = 0;
        this.maxHealthPoints += 10;
        this.healthPoints = this.maxHealthPoints;
        this.evolved = false;
        System.out.println(this.name + " leveled up to level " + this.level + "!");
    }

    public void increaseMaxHealthPoints(int amount) {
        this.maxHealthPoints += amount;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void basicAttack(Monster enemy) {};

    public void specialAttack(Monster enemy) throws SpecialAttackException {};

    public void elementalAttack(Monster enemy) {};
}

class SpecialAttackException extends Exception {
    public SpecialAttackException(String message) {
        super(message);
    }
}

class FireMonster extends Monster {
    public FireMonster(String name, int level, int healthPoints) {
        super(name, "FIRE");
    }

    @Override
    public void basicAttack(Monster enemy) {
        System.out.println("Basic Attack mengurangi HP " + enemy.getName() + " sebesar 10 poin, menyisakan " + (enemy.healthPoints - 10) + " HP.");
        enemy.healthPoints -= 10;
    }

    @Override
    public void specialAttack(Monster enemy) throws SpecialAttackException {
        Random rand = new Random();
        if (rand.nextInt(100) < 10) { // 10% chance to miss
            throw new SpecialAttackException(this.name + " missed the Special Attack!");
        }
        System.out.println("Special Attack mengurangi HP " + enemy.getName() + " sebesar 15 poin, menyisakan " + (enemy.healthPoints - 15) + " HP.");
        System.out.println("Namun, " + this.name + " juga mengorbankan 4 HP, menyisakan " + (this.healthPoints - 4) + " HP.");
        enemy.healthPoints -= 15;
        this.healthPoints -= 4; // Sacrifice HP
    }

    @Override
    public void elementalAttack(Monster enemy) {
        int damage = 15;
        if (enemy.getElement().equals("GRASS") || enemy.getElement().equals("ICE")) {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 30 poin, menyisakan " + (enemy.healthPoints - 30) + " HP.");
            damage = 30;
        } else {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 15 poin, menyisakan " + (enemy.healthPoints - 15) + " HP.");
        }
        enemy.healthPoints -= damage;
    }
}

class WaterMonster extends Monster {
    public WaterMonster(String name, int level, int healthPoints) {
        super(name, "WATER");
    }

    @Override
    public void basicAttack(Monster enemy) {
        System.out.println("Basic Attack mengurangi HP " + enemy.getName() + " sebesar 10 poin, menyisakan " + (enemy.healthPoints - 10) + " HP.");
        enemy.healthPoints -= 10;
    }

    @Override
    public void specialAttack(Monster enemy) throws SpecialAttackException {
        Random rand = new Random();
        if (rand.nextInt(100) < 10) { // 10% chance to miss
            throw new SpecialAttackException(this.name + " missed the Special Attack!");
        }
        System.out.println("Special Attack mengurangi HP " + enemy.getName() + " sebesar 20 poin, menyisakan " + (enemy.healthPoints - 20) + " HP.");
        System.out.println("Namun, " + this.name + " juga mengorbankan 5 HP, menyisakan " + (this.healthPoints - 5) + " HP.");
        enemy.healthPoints -= 20;
        this.healthPoints -= 5; // Sacrifice HP
    }

    @Override
    public void elementalAttack(Monster enemy) {
        int damage = 15;
        if (enemy.getElement().equals("FIRE") || enemy.getElement().equals("GROUND")) {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 30 poin, menyisakan " + (enemy.healthPoints - 30) + " HP.");
            damage = 30;
        } else {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 15 poin, menyisakan " + (enemy.healthPoints - 15) + " HP.");
        }
        enemy.healthPoints -= damage;
    }
}

class WindMonster extends Monster {
    public WindMonster(String name, int level, int healthPoints) {
        super(name, "WIND");
    }

    @Override
    public void basicAttack(Monster enemy) {
        System.out.println("Basic Attack mengurangi HP " + enemy.getName() + " sebesar 10 poin, menyisakan " + (enemy.healthPoints - 10) + " HP.");
        enemy.healthPoints -= 10;
    }

    @Override
    public void specialAttack(Monster enemy) throws SpecialAttackException {
        Random rand = new Random();
        if (rand.nextInt(100) < 10) { // 10% chance to miss
            throw new SpecialAttackException(this.name + " missed the Special Attack!");
        }
        System.out.println("Special Attack mengurangi HP " + enemy.getName() + " sebesar 15 poin, menyisakan " + (enemy.healthPoints - 15) + " HP.");
        System.out.println("Namun, " + this.name + " juga mengorbankan 4 HP, menyisakan " + (this.healthPoints - 4) + " HP.");
        enemy.healthPoints -= 15;
        this.healthPoints -= 4; // Sacrifice HP
    }

    @Override
    public void elementalAttack(Monster enemy) {
        int damage = 15;
        if (enemy.getElement().equals("EARTH") || enemy.getElement().equals("ICE")) {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 30 poin, menyisakan " + (enemy.healthPoints - 30) + " HP.");
            damage = 30;
        } else {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 15 poin, menyisakan " + (enemy.healthPoints - 15) + " HP.");
        }
        enemy.healthPoints -= damage;
    }
}

class IceMonster extends Monster {
    public IceMonster(String name, int level, int healthPoints) {
        super(name, "ICE");
    }

    @Override
    public void basicAttack(Monster enemy) {
        System.out.println("Basic Attack mengurangi HP " + enemy.getName() + " sebesar 10 poin, menyisakan " + (enemy.healthPoints - 10) + " HP.");
        enemy.healthPoints -= 10;
    }

    @Override
    public void specialAttack(Monster enemy) throws SpecialAttackException {
        Random rand = new Random();
        if (rand.nextInt(100) < 10) { // 10% chance to miss
            throw new SpecialAttackException(this.name + " missed the Special Attack!");
        }
        System.out.println("Special Attack mengurangi HP " + enemy.getName() + " sebesar 25 poin, menyisakan " + (enemy.healthPoints - 20) + " HP.");
        System.out.println("Namun, " + this.name + " juga mengorbankan 5 HP, menyisakan " + (this.healthPoints - 5) + " HP.");
        enemy.healthPoints -= 25;
        this.healthPoints -= 5; // Sacrifice HP
    }

    @Override
    public void elementalAttack(Monster enemy) {
        int damage = 15;
        if (enemy.getElement().equals("WATER") || enemy.getElement().equals("GROUND")) {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 30 poin, menyisakan " + (enemy.healthPoints - 30) + " HP.");
            damage = 30;
        } else {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 15 poin, menyisakan " + (enemy.healthPoints - 15) + " HP.");
        }
        enemy.healthPoints -= damage;
    }
}

class EarthMonster extends Monster {
    public EarthMonster(String name, int level, int healthPoints) {
        super(name, "Earth");
    }

    @Override
    public void basicAttack(Monster enemy) {
        System.out.println("Basic Attack mengurangi HP " + enemy.getName() + " sebesar 10 poin, menyisakan " + (enemy.healthPoints - 10) + " HP.");
        enemy.healthPoints -= 10;
    }

    @Override
    public void specialAttack(Monster enemy) throws SpecialAttackException {
        Random rand = new Random();
        if (rand.nextInt(100) < 10) { // 10% chance to miss
            throw new SpecialAttackException(this.name + " missed the Special Attack!");
        }
        System.out.println("Special Attack mengurangi HP " + enemy.getName() + " sebesar 30 poin, menyisakan " + (enemy.healthPoints - 20) + " HP.");
        System.out.println("Namun, " + this.name + " juga mengorbankan 7 HP, menyisakan " + (this.healthPoints - 5) + " HP.");
        enemy.healthPoints -= 30;
        this.healthPoints -= 7; // Sacrifice HP
    }

    @Override
    public void elementalAttack(Monster enemy) {
        int damage = 15;
        if (enemy.getElement().equals("WATER") || enemy.getElement().equals("GROUND")) {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 30 poin, menyisakan " + (enemy.healthPoints - 30) + " HP.");
            damage = 30;
        } else {
            System.out.println("Elemental Attack mengurangi HP " + enemy.getName() + " sebesar 15 poin, menyisakan " + (enemy.healthPoints - 15) + " HP.");
        }
        enemy.healthPoints -= damage;
    }
}

class Item {
    private String name;
    private String effect;
    private int value;

    public Item(String name, String effect, int value) {
        this.name = name;
        this.effect = effect;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public int getValue() {
        return value;
    }

    public void applyEffect(Monster monster) {
        switch (effect) {
            case "HEAL":
                monster.heal(value);
                System.out.println(monster.getName() + " healed by " + value + " points.");
                break;
            case "RARE":
                monster.heal(monster.getMaxHealthPoints() - monster.getHealthPoints());
                monster.increaseMaxHealthPoints(value);
                System.out.println(monster.getName() + " max health increased by " + value + " points and fully healed.");
                break;
            case "REVIVE":
                if (monster.getHealthPoints() <= 0) {
                    monster.setHealthPoints(value);
                    System.out.println(monster.getName() + " revived with " + value + " health points.");
                } else {
                    System.out.println(monster.getName() + " is not fainted.");
                }
                break;
            default:
                System.out.println("Item effect not recognized.");
        }
    }
}


class Dungeon {
    public Monster generateWildMonster() {
        Random rand = new Random();
        int monsterType = rand.nextInt(5) + 1; // Fixing the range
        int level = rand.nextInt(5) + 1;
        switch (monsterType) {
            case 1:
                return new FireMonster("WildFire", level, level * 20);
            case 2:
                return new WaterMonster("WildWater", level, level * 20);
            case 3:
                return new WindMonster("WildWind", level, level * 20);
            case 4:
                return new IceMonster("WildIce", level, level * 20);
            case 5:
                return new EarthMonster("WildEarth", level, level * 20);
            default:
                return null;
        }
    }
}

class BattleArena {
    private Monster playerMonster;
    private Monster wildMonster;
    private Scanner scanner;

    public BattleArena(Monster playerMonster, Monster wildMonster) {
        this.playerMonster = playerMonster;
        this.wildMonster = wildMonster;
        this.scanner = new Scanner(System.in);
    }

    public void startBattle() {
        System.out.println("Battle started between " + playerMonster.getName() + " and " + wildMonster.getName() + ".");

        while (playerMonster.getHealthPoints() > 0 && wildMonster.getHealthPoints() > 0) {
            playerTurn();
            if (wildMonster.getHealthPoints() > 0) {
                wildTurn();
            }
        }

        if (playerMonster.getHealthPoints() > 0) {
            System.out.println(playerMonster.getName() + " wins the battle!");
            playerMonster.increaseExperience(50);
        } else {
            System.out.println(wildMonster.getName() + " wins the battle!");
        }
    }

    private void playerTurn() {
        System.out.println(" ");
        System.out.println(playerMonster.getName() + "'s turn! Choose your action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Special Attack");
        System.out.println("3. Elemental Attack");
        System.out.println("4. Use Item");
        System.out.println("5. Flee");

        int choice = scanner.nextInt();
        try {
            switch (choice) {
                case 1:
                    playerMonster.basicAttack(wildMonster);
                    break;
                case 2:
                    playerMonster.specialAttack(wildMonster);
                    break;
                case 3:
                    playerMonster.elementalAttack(wildMonster);
                    break;
                case 4:
                    useItem();
                    break;
                case 5:
                    if (new Random().nextInt(100) < 50) { // 50% chance to flee successfully
                        System.out.println(playerMonster.getName() + " fled from battle!");
                        playerMonster.healthPoints = 0; // End battle
                    } else {
                        System.out.println(playerMonster.getName() + " failed to flee!");
                    }
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    playerTurn();
            }
        } catch (SpecialAttackException e) {
            System.out.println(e.getMessage());
        }
    }

    private void wildTurn() {
        System.out.println(" ");
        System.out.println(wildMonster.getName() + "'s turn!");
        int damage = 11; // Example damage for wild monster's attack
        System.out.println(wildMonster.getName() + " attacks and deals " + damage + " damage to " + playerMonster.getName() + ".");
        playerMonster.healthPoints -= damage;
    }

    private void useItem() {
        List<Item> inventory = HomeBase.getInventory();
        if (inventory.size() > 0) {
            System.out.println("Choose an item to use:");
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println((i + 1) + ". " + inventory.get(i).getName());
            }
            int choice = scanner.nextInt();
            if (choice > 0 && choice <= inventory.size()) {
                Item item = inventory.get(choice - 1);
                item.applyEffect(playerMonster);
                inventory.remove(choice - 1);
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("No items in inventory.");
        }
    }
}

class HomeBase {
    private Scanner scanner;
    private List<Monster> monsters;
    private static List<Item> inventory = new ArrayList<>();
    private static final List<Item> shopItems = new ArrayList<>();

    public HomeBase() {
        this.scanner = new Scanner(System.in);
        this.monsters = new ArrayList<>();
        shopItems.add(new Item("Potion", "HEAL", 20));
        shopItems.add(new Item("Rare Candy", "RARE", 50));
        shopItems.add(new Item("Revive", "REVIVE", 30));
    }

    public void addMonster(Monster monster) {
        if (monsters.size() < 3) {
            monsters.add(monster);
            System.out.println(" ");
            System.out.println(monster.getName() + " has been added to your team.");
        } else {
            System.out.println("You can only have up to 3 monsters in your team.");
        }
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public static List<Item> getInventory() {
        return inventory;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nHome Base Menu:");
            System.out.println("1. Level Up Monster");
            System.out.println("2. Evolve Monster");
            System.out.println("3. Heal Monster");
            System.out.println("4. Buy Item");
            System.out.println("5. Exit Home Base");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    levelUpMonster();
                    break;
                case 2:
                    evolveMonster();
                    break;
                case 3:
                    healMonster();
                    break;
                case 4:
                    buyItem();
                    break;
                case 5:
                    return; // Exit Home Base
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void levelUpMonster() {
        Monster monster = chooseMonster();
        if (monster != null) {
            monster.increaseExperience(100); // for testing purpose
        }
    }

    private void evolveMonster() {
        Monster monster = chooseMonster();
        if (monster != null) {
            System.out.print("Enter new element for evolution: ");
            String newElement = scanner.next();
            monster.evolve(newElement);
        }
    }

    private void healMonster() {
        Monster monster = chooseMonster();
        if (monster != null) {
            monster.heal(20);
            System.out.println(monster.getName() + " has been healed.");
        }
    }

    private void buyItem() {
        System.out.println("Available items to buy:");
        for (int i = 0; i < shopItems.size(); i++) {
            System.out.println((i + 1) + ". " + shopItems.get(i).getName() + " (" + shopItems.get(i).getEffect() + ") - Effect value: " + shopItems.get(i).getValue());
        }

        System.out.print("Choose an item to buy (1-" + shopItems.size() + "): ");
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= shopItems.size()) {
            Item selectedItem = shopItems.get(choice - 1);
            inventory.add(selectedItem);
            System.out.println(selectedItem.getName() + " has been added to your inventory.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private Monster chooseMonster() {
        System.out.println("Choose a monster:");
        for (int i = 0; i < monsters.size(); i++) {
            System.out.println((i + 1) + ". " + monsters.get(i).getName());
        }
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= monsters.size()) {
            return monsters.get(choice - 1);
        } else {
            System.out.println("Invalid choice.");
            return null;
        }
    }
}

class MonsterFactory {
    public static Monster createMonster(String name, int level, int healthPoints) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an element for " + name + ":");
        System.out.println("1. FIRE");
        System.out.println("2. WIND");
        System.out.println("3. WATER");
        System.out.println("4. ICE");
        System.out.println("5. EARTH");

        int choice = scanner.nextInt();
        String element = "";

        switch (choice) {
            case 1:
                element = "FIRE";
                return new FireMonster(name, level, healthPoints);
            case 2:
                element = "WIND";
                return new WindMonster(name, level, healthPoints);
            case 3:
                element = "WATER";
                return new WaterMonster(name, level, healthPoints);
            case 4:
                element = "ICE";
                return new IceMonster(name, level, healthPoints);
            case 5:
                element = "EARTH";
                return new EarthMonster(name, level, healthPoints);
            default:
                System.out.println("Invalid choice. Defaulting to FIRE.");
                return new FireMonster(name, level, healthPoints);
        }
    }
}


public class MainGame {
    public static void main(String[] args) throws IOException {
        HomeBase homeBase = new HomeBase();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Holla!");
        System.out.println("Welcome to Pokemon Mystery!");

        // Create 3 monsters and add them to home base
        for (int i = 1; i <= 3; i++) {
            System.out.print("\nEnter name for monster " + i + ": ");
            String name = scanner.nextLine();
            Monster monster = MonsterFactory.createMonster(name, 1, 100);
            homeBase.addMonster(monster);
        }

        while (true) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1. Enter Home Base");
            System.out.println("2. Enter Dungeon");
            System.out.println("3. Exit Game");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    homeBase.showMenu();
                    break;
                case 2:
                    List<Monster> playerMonsters = homeBase.getMonsters();
                    if (playerMonsters.size() > 0) {
                        System.out.println("\nChoose a monster to enter the dungeon with:");
                        for (int i = 0; i < playerMonsters.size(); i++) {
                            System.out.println((i + 1) + ". " + playerMonsters.get(i).getName());
                        }
                        int monsterChoice = scanner.nextInt();
                        Monster chosenMonster = playerMonsters.get(monsterChoice - 1);

                        displayMonsterDetails(chosenMonster);
                        
                        Dungeon dungeon = new Dungeon();
                        Monster wildMonster = dungeon.generateWildMonster();
                        System.out.println("\nA wild " + wildMonster.getName() + " appears!");
                        BattleArena arena = new BattleArena(chosenMonster, wildMonster);
                        arena.startBattle();
                    } else {
                        System.out.println("You don't have any monsters. Go to the Home Base to add one.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting Game...");
                    System.out.println("Thank You! and Back Again!!");
                    saveGameProgress(homeBase.getMonsters());
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    public static void displayMonsterDetails(Monster monster) {
        System.out.println("\nMonster Details:");
        System.out.println("Name: " + monster.getName());
        System.out.println("Level: " + monster.getLevel());
        System.out.println("Experience Points: " + monster.getExperiencePoints());
        System.out.println("Health Points: " + monster.getHealthPoints());
        System.out.println("Element: " + monster.getElement());
        System.out.println("Evolved: " + (monster.isEvolved() ? "Yes" : "No"));
        System.out.println();
    }

    public static void saveGameProgress(List<Monster> monsters) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("game_progress.txt"));
        for (Monster monster : monsters) {
            writer.write("Name: " + monster.getName() + "\n");
            writer.write("Level: " + monster.getLevel() + "\n");
            writer.write("Experience Points: " + monster.getExperiencePoints() + "\n");
            writer.write("Health Points: " + monster.getHealthPoints() + "\n");
            writer.write("Element: " + monster.getElement() + "\n");
            writer.write("Evolved: " + (monster.isEvolved() ? "Yes" : "No") + "\n");
            writer.write("\n");
        }
        writer.close();
    }
}