package pl.edu.mimuw;

public enum Card {
    
    DWA(2), 
    TRZY(3), 
    CZTERY(4),
    PIEC(5),
    SZESC(6),
    SIEDEM(7),
    OSIEM(8),
    DZIEWIEC(9),
    DZIESIEC(10),
    WALET(10),
    DAMA(10),
    KROL(10),
    AS(11);

    final public int value;
    
    private Card (int value) {
        this.value = value;
    }
}
