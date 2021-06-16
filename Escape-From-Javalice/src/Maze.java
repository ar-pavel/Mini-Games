import java.util.Scanner;

public class Maze {
    private Player player;
    private Cell currentCell;
    private Cell previousCell;
    private Direction lastMove;

    public Player getPlayer() {
        return player;
    }

    public Maze(Player player, Cell currentCell, Cell previousCell) {
        this.player = player;
        this.currentCell = currentCell;
        this.previousCell = previousCell;
    }

    public Maze(Player player, Cell currentCell) {
        this.player = player;
        this.currentCell = currentCell;
        previousCell = null;

        // play and control the match
        playGame();
    }


    // game controller
    private boolean playGame() {
        Scanner sc = new Scanner(System.in);

        while (true) {

            try {
                // print current cell info
                System.out.println(currentCell);

                System.out.print("Move to : {i.e: North, South, East, West} ");
                String move = sc.next();
                try {
                    // convert the input direction into ENUM
                    Direction mov = Direction.valueOf(move.toUpperCase());
                    // update previousCell
                    previousCell = currentCell;
                    Portal portal;

                    // make moves with necessary updates
                    switch (mov){
                        case EAST:
                            portal = currentCell.East;
                            currentCell.East = new Portal(currentCell.East, true);
                            lastMove = Direction.EAST;
                            currentCell = new Cell(currentCell, portal);
                            break;
                        case WEST:
                            portal = currentCell.West;
                            currentCell.West = new Portal(currentCell.West, true);
                            lastMove = Direction.WEST;
                            currentCell = new Cell(currentCell, portal);
                            break;
                        case NORTH:
                            portal = currentCell.North;
                            currentCell.North = new Portal(currentCell.North, true);
                            lastMove = Direction.NORTH;
                            currentCell = new Cell(currentCell, portal);
                            break;
                        case SOUTH:
                            portal = currentCell.South;
                            currentCell.South = new Portal(currentCell.South, true);
                            lastMove = Direction.SOUTH;
                            currentCell = new Cell(currentCell, portal);
                            break;
                    }

                    // if this cell is an exit cell
                    if(currentCell.exitCell){
                        handleExit(true);
                        return true;
                    }

                    // if this cell contains a magic box
                    if(currentCell.magicBox.status) {
                        handleMagicBox();
                        continue;
                    }

                    // if this cell is a jail
                    if(currentCell.jail){
                        boolean end = handleJail();
                        continue;
                    }

                    // if there exist any magic police in this cell
                    if(currentCell.police){
                        handlePolice();
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }

        }

//        return true;
    }

    private boolean handlePolice() {
        // TODO: bribe the police or use invisible cloak
        Scanner sc = new Scanner(System.in);
        if(player.inventory.size()>0){
            System.out.println("Using invisible cloak to skip police.Currently available cloak count : "+ player.inventory.size());
            player.inventory.remove(player.inventory.size()-1);
        }else{
            System.out.print("Want to bribe the police? (y/N)");

            // input user decision
            String decision = sc.next();

            if ("y".equals(decision)) {// bribing the police
                double coinNeed = (Util.getRandom(50, 150) * 1.0 / 10) * player.Coins;
                System.out.println("Coin requested : " + coinNeed + ", coin have : " + player.Coins);
                if (coinNeed > player.Coins) {
                    System.out.println("Transaction can't be made.\nSending to the jail");
                    return handleJail();
                } else {
                    player.Coins -= coinNeed;
                    System.out.println("Bribing Successful. Coin left : " + player.Coins);
                }
            } else {// send to jail
                return handleJail();
            }

        }
        return true;
    }

    private boolean handleJail() {
//        System.out.println("Jail");
        Scanner sc = new Scanner(System.in);
        player.jailCount++;

        if(player.jailCount>3){
            System.out.println("Jail escaping limit exceed");
            handleExit(false);
            return false;
        }else{
            System.out.print("Do you want to escape from jail? (y/N)  ");
            String decision = sc.next();
            if ("y".equals(decision)) {// jump to previous cell
                previousCell = currentCell;
                Portal portal;
                switch (lastMove) {
                    case WEST:
                        portal = currentCell.East;
                        currentCell.East = new Portal(currentCell.East, true);
                        lastMove = Direction.EAST;
                        currentCell = new Cell(currentCell, portal);

                        break;
                    case EAST:
                        portal = currentCell.West;
                        currentCell.West = new Portal(currentCell.West, true);
                        lastMove = Direction.WEST;
                        currentCell = new Cell(currentCell, portal);

                        break;
                    case SOUTH:
                        portal = currentCell.North;
                        currentCell.North = new Portal(currentCell.North, true);
                        lastMove = Direction.NORTH;
                        currentCell = new Cell(currentCell, portal);

                        break;
                    case NORTH:
                        portal = currentCell.South;
                        currentCell.South = new Portal(currentCell.South, true);
                        lastMove = Direction.SOUTH;
                        currentCell = new Cell(currentCell, portal);
                        break;
                }
                if (currentCell.exitCell) {
                    handleExit(true);
                    return false;
                }

                if (currentCell.magicBox.status) {
                    handleMagicBox();
                    return true;
                }

                if (currentCell.jail) {
                    return handleJail();

                }
                if (currentCell.police) {
                    return handlePolice();
                }
            } else {
                handleExit(false);
                return false;
            }
        }
        return true;
    }

    private void handleExit(boolean status) {
        System.out.println(status?"win":"lose");
        player.gameStatus = true;
    }

    private void handleMagicBox(){
        Scanner sc = new Scanner(System.in);
        // handle magic box
        System.out.print("A magic box is found!\n" +
                "Do you want to open the magic box? (y/N) ");
            String response = sc.next();
            switch (response){
                case "y":
                    System.out.println(currentCell.magicBox);

                    if(currentCell.magicBox.policeAlarm){
                        currentCell.North = new Portal(currentCell.North, 1);
                        currentCell.South = new Portal(currentCell.South, 1);
                        currentCell.East = new Portal(currentCell.East, 1);
                        currentCell.West = new Portal(currentCell.West, 1);
                    }else if(currentCell.magicBox.coal){
                        //do nothing
                    }else if(currentCell.magicBox.coin!=0){
                        player.Coins += currentCell.magicBox.coin;
                        System.out.println("User coins updated with " + currentCell.magicBox.coin + ". Updated coin count : " + player.Coins);
                    }else if(currentCell.magicBox.invisibleCloak && player.inventory.size()<3){
                        System.out.print("Do you want to add it to inventory? (y/N) ");
                        String res = sc.next();
                        switch (res){
                            case "y":
                                player.inventory.add(currentCell.magicBox);
                                break;
                            default:
                                System.out.println("Skipping this item...");
                        }
                    }else{
                        System.out.println("Can't add this item. Your inventory is already full.");
                    }
                    break;
                default:

            }

//        sc.close();
    }
}
