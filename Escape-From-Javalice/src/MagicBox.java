public class MagicBox {
    public boolean status;
    public int coin;
    public boolean invisibleCloak;
    public boolean policeAlarm;
    public boolean coal;

    public MagicBox() {
        this.status = Util.getRandom(1,100)%2==0;
        this.coin = 0;
        this.invisibleCloak = false;
        this.policeAlarm = false;
        this.coal = false;

        if(status) {
            getPrize();
        }
    }
    public MagicBox(boolean status) {
        this.status = status;
        this.coin = 0;
        this.invisibleCloak = false;
        this.policeAlarm = false;
        this.coal = false;

        if(status) {
            getPrize();
        }
    }



    private void getPrize() {
        int rand = Util.getRandom(1, 10);

        if(rand<=3){
            // 30% possibility
            coin();
        }else if(rand <=6){
            // 30% possibility
            coal();
        }else{
            // 40% possibility

            rand = Util.getRandom(1, 40);
            if(rand <=15){
                // 15% possibility
                invisibilityCloak();
            }else{
                // 25% possibility
                policeAlarm();
            }
        }
    }

    public void coin(){
        int min = 10, max = 35;
        this.coin = Util.getRandom(min, max);
    }

    public void policeAlarm(){
        this.policeAlarm = true;
    }

    public void invisibilityCloak(){
        this.invisibleCloak = true;
    }

    public void coal(){
        this.coal = true;
    }

    @Override
    public String toString() {
        return "MagicBox contains : {" +
//                "status=" + status +
                (coin>0?  coin + " coins"  : "") +
                (invisibleCloak?  "1 Invisible Cloak" : "") +
                (policeAlarm?  "Police Alarm": "") +
                (coal? "Coal" : "") +
                '}';
    }
}
