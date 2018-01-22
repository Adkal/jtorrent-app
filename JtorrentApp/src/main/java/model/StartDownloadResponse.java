package model;

import java.io.Serializable;

public class StartDownloadResponse implements Serializable {

    Integer id;

    String name;

    float seeds;

    float peers;

    float speed;

    double size;

    public StartDownloadResponse(Integer id, String name, float seeds, float peers, float speed, double size) {
        this.id = id;
        this.name = name;
        this.seeds = seeds;
        this.peers = peers;
        this.speed = speed;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSeeds() {
        return seeds;
    }

    public void setSeeds(float seeds) {
        this.seeds = seeds;
    }

    public float getPeers() {
        return peers;
    }

    public void setPeers(float peers) {
        this.peers = peers;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StartDownloadResponse that = (StartDownloadResponse) o;

        if (Float.compare(that.seeds, seeds) != 0) return false;
        if (Float.compare(that.peers, peers) != 0) return false;
        if (Float.compare(that.speed, speed) != 0) return false;
        if (Double.compare(that.size, size) != 0) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (seeds != +0.0f ? Float.floatToIntBits(seeds) : 0);
        result = 31 * result + (peers != +0.0f ? Float.floatToIntBits(peers) : 0);
        result = 31 * result + (speed != +0.0f ? Float.floatToIntBits(speed) : 0);
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
