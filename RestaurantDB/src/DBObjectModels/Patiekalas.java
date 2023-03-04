package DBObjectModels;

public class Patiekalas {
    private int patiekaloID;
    private int meniuID;
    private String pavadinimas;
    private Double kaina;

    public Patiekalas(int patiekaloID, int meniuID, String pavadinimas, Double kaina) {
        this.patiekaloID = patiekaloID;
        this.meniuID = meniuID;
        this.pavadinimas = pavadinimas;
        this.kaina = kaina;
    }

    public int getPatiekaloID() {
        return patiekaloID;
    }

    public void setPatiekaloID(int patiekaloID) {
        this.patiekaloID = patiekaloID;
    }

    public int getMeniuID() {
        return meniuID;
    }

    public void setMeniuID(int meniuID) {
        this.meniuID = meniuID;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public Double getKaina() {
        return kaina;
    }

    public void setKaina(Double kaina) {
        this.kaina = kaina;
    }
}
