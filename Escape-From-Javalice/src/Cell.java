
public class Cell {
    public Portal  North;
    public Portal  South;
    public Portal  East;
    public Portal  West;
    public MagicBox magicBox;
    public boolean jail;
    public boolean police;
    public boolean exitCell;

    public Cell(Cell cell, Portal portal){
        this.North = cell.North;
        this.South = cell.South;
        this.East = cell.East;
        this.West = cell.West;
        this.magicBox = new MagicBox();

        this.police = Util.getPossibility(portal.policeEncounterPossibility);
        this.exitCell = Util.getPossibility(portal.exitFindingPossibility);
    }

    public Cell(Portal north, Portal south, Portal east, Portal west) {
        North = north;
        South = south;
        East = east;
        West = west;
        this.magicBox = new MagicBox(false);
        this.jail = false;
        this.police = false;
        this.exitCell= false;
    }


    @Override
    public String toString() {
        return "Cell info : {" +
                "\nNorth=" + North +
                ",\nSouth=" + South +
                ",\nEast=" + East +
                ",\nWest=" + West +
//                (magicBox.status? ",\nmagicBox=" + magicBox :"")+
//                (jail==true? ",\njail=" + jail: "") +
//                (police==true? ",\npolice=" + police : "")+
//                (exitCell==true? ",\nexitCell=" + exitCell : "") +
                '}';
    }
}
