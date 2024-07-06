package pl.edu.mimuw;

import java.util.Arrays;
import java.util.List;

public class TestujSamochody {

    public static List<Samochod> zrobSamochody() {
        return Arrays.asList(
            new Samochod("Toyota", "Yaris", 5, 76_900,
                    List.of(new Wpis("radio", 5000), new Wpis("szyberdach", 10_000))),
            new Samochod("Renault", "Clio", 5, 71_900,
                    List.of(new Wpis("alufelgi", 3500))),
            new Samochod("Porsche", "Cayenne", 5, 428_000,
                    List.of(new Wpis("skóra", 25_000))),
            new Samochod("Tesla", "Model Y", 5, 229_990,
                    List.of(new Wpis("FSD", 50_000), new Wpis("120kWh", 60_000))),
            new Samochod("Renault", "Traffic", 6, 210_150,
                    List.of(new Wpis("radio", 3500), new Wpis("szyberdach", 8_000))),
            new Samochod("Tesla", "Model 3", 5, 219_990,
                    List.of(new Wpis("FSD", 50_000))),
            new Samochod("Tesla", "Model X", 5, 592_990,
                    List.of(new Wpis("FSD", 50_000))),
            new Samochod("Renault", "Kangoo", 5, 100_737,
                    List.of(new Wpis("radio", 5500), new Wpis("bagażnik", 5_000), new Wpis("alufelgi", 6000))),
            new Samochod("Porsche", "Taycan", 5, 448_000,
                    List.of(new Wpis("skóra", 25_000))),
            new Samochod("Tesla", "Model S", 5, 553_990,
                    List.of(new Wpis("FSD", 50_000))),
            new Samochod("Toyota", "Aygo", 3, 63_900,
                    List.of(new Wpis("alufelgi", 3500), new Wpis("radio", 2800), new Wpis("spoiler", 1500))),
            new Samochod("Toyota", "Corolla", 4, 116_900),
            new Samochod("Hyundai", "Tucson", 5, 127_400),
            new Samochod("Porsche", "911 Turbo", 5, 1_084_000),
            new Samochod("Hyundai", "I10", 5, 52_000)
        );
    }

}

