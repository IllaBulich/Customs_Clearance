package client;

import java.time.LocalDate;

public class Сalculations {

    private int cost;
    private LocalDate date;
    private int volume;

    public Сalculations(int cost, LocalDate date, int volume) {
        this.cost = cost;
        this.date = date;
        this.volume = volume;
    }

    public int PhysicalCalc(){
        int cum=0;
        cum += this.cost*0.03+volume*0.5;
        return cum;
    }


}
