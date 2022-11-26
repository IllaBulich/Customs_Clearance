package client;

import java.time.LocalDate;

public class Сalculations {

    private int cost;
    private LocalDate date;
    private int volume;
    private String currency;

    public Сalculations(int cost, LocalDate date, int volume, String currency) {
        this.cost = cost;
        this.date = date;
        this.volume = volume;
        this.currency = currency;
    }

    public int PhysicalCalc(){
        int cum=0;
        cum += this.cost*0.03+volume*0.5;
        return cum;
    }


}
