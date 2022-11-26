package client.resources;

public class Rates {
    private float volumeMin, volumeMax, priceMin, priceMax, fromCost, bid;

    public float getVolumeMin() {
        return volumeMin;
    }

    public float getVolumeMax() {
        return volumeMax;
    }

    public float getPriceMin() {
        return priceMin;
    }

    public float getPriceMax() {
        return priceMax;
    }

    public float getFromCost() {
        return fromCost;
    }

    public float getBid() {
        return bid;
    }

    public Rates(float volumeMin, float volumeMax, float priceMin, float priceMax, float fromCost, float bid) {
        this.volumeMin = volumeMin;
        this.volumeMax = volumeMax;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.fromCost = fromCost;
        this.bid = bid;
    }
}
