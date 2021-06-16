import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {

        // read exits file
        Cell root = readExistFile();
        //  System.out.println("Root cell info : \n" + root);

        // read player info
        String userName = readName();

        // create a new player with provided input
        Player player = new Player(userName);
        System.out.println("Player info : " + player);

        // Maze class instance will control the game
        // takes two parameters: Player and starting cell
        Maze game = new Maze(player, root);

        // update the player info after the end of the game
        player = game.getPlayer();

        // write log to the file
        writeToFile(player);
    }

    private static void writeToFile(Player player) {
        try {
            File file = new File("src/Outcome.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(player.name + (player.gameStatus?" win":" lose" + " the game.\n"));
            System.out.print(player.name + (player.gameStatus?" win":" lose" + " the game.\n"));
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    static String readName(){
        Scanner scanner = new Scanner(System.in);
        String name;
        System.out.print("User name : ");
        while(true){
            name = scanner.nextLine();
            if(name.length()>=3 && name.length()<=12)
                break;
            System.out.println("Name length should be within 3 to 12 character.");
        }

        return name;
    }

    // Read root cell portal info
     static Cell  readExistFile(){
        File file = new File("src/exits.txt");
        Scanner scanner = null;
//         System.out.println(file.exists());
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Exist file reading error: " + e.getMessage());
        }

         ArrayList<Portal> portals=new ArrayList<>();
         while (true) {
             assert scanner != null;
             if (!scanner.hasNextLine()) break;
//            String data = myReader.nextLine();
            String[] tokens = scanner.nextLine().split(",");
            Portal portal = new Portal(Direction.valueOf(tokens[0].toUpperCase()),
                    Integer.parseInt(tokens[1]),Integer.parseInt( tokens[2]), Integer.parseInt(tokens[3]));
          //  System.out.println(portal);
            portals.add(portal);
        }
        scanner.close();

         // if the input file contains all four directions info, create an instance and of Cell class and return
        if(portals.size()==4)
            return new Cell(portals.get(0), portals.get(1), portals.get(2), portals.get(3));
        else
            return null;
    }

}