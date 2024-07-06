package pl.edu.mimuw;

import java.util.List;
import java.util.stream.Stream;

public class Samochod {
    final private String marka;
    final private String model;
    final private int liczbaDrzwi;
    final private int cena;

    final private List<Wpis> dodatki;

    public Samochod(String marka, String model, int liczbaDrzwi, int cena, List<Wpis> dodatki) {
        this.marka = marka;
        this.model = model;
        this.liczbaDrzwi = liczbaDrzwi;
        this.cena = cena;
        this.dodatki = dodatki;
    }

    public Samochod(String marka, String model, int liczbaDrzwi, int cena) {
        this(marka, model, liczbaDrzwi, cena, List.of());
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public int getLiczbaDrzwi() {
        return liczbaDrzwi;
    }

    public int getCena() {
        return cena;
    }

    public List<Wpis> getDodatki() {
        return dodatki;
    }

    @Override
    public String toString() {
        return marka + ' ' + model + ", drzwi: " + liczbaDrzwi + ", cena: " + cena;
    }

}
