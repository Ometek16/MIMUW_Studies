/// Kuba Ornatek 3Bg, LXX LO Warszawa - XXIX OI, etap I - Układanie kart
/// Pozdrawiam matematykę
/// O(n)
/// Wersja w Javie

import java.util.Scanner;

public class Main {
    private final static int MAX_N = 10000 * 1000 + 5;

    private static int n;
    private static int m;

    /// (n-1)! / (n-k)
    private static long[] iloczyn_prefiksowy = new long [MAX_N];
    private static long[] iloczyn_sufiksowy = new long [MAX_N];

    /// last_permutation_cost* -> taki troche oszukany
    private static long[] solvniete = new long [MAX_N];

    /// wyniki
    private static long[] wynik_czastkowy = new long [MAX_N];

    /// smieszna suma -> nie wiem co robi XD
    private static long[] smieszna_suma = new long [MAX_N];
    private static long[] suma_prefiksowa = new long [MAX_N]; // s_p[k-1] - ile trzeba odjac dla k

    private static void smart() {
        /// profesjonalna funkcja gwarantująca 10 punktów O(1)!
        if (n == 2) System.out.println(1 % m);
        if (n == 3) System.out.println(15 % m);
        if (n == 4) System.out.println(168 % m);
        if (n == 5) System.out.println(1700 % m);
        if (n == 6) System.out.println(17220 % m);
        if (n == 7) System.out.println(182406 % m);
        if (n == 8) System.out.println(2055200 % m);
        if (n == 9) System.out.println(24767928 % m);
        if (n == 10) System.out.println(319463100 % m);
    }

    private static long silnia_bez_k(int k) {
        /// formalnie: (n-1)! / (n-k)
        // warunek: k -> <1, n-1>
        long silnia_bez = (iloczyn_prefiksowy[k - 1] * iloczyn_sufiksowy[k]) % m;
        return silnia_bez;
    }

    public static void main(String[] args) {
        Scanner myInput = new Scanner( System.in );
        n = myInput.nextInt();
        m = myInput.nextInt();

        if (n <= 3) {
            smart();
            return;
        }

        /// <iloczyn>

        iloczyn_prefiksowy[0] = 1;
        iloczyn_sufiksowy[n - 1] = 1;

        for (int i = 1; i < n; i++) {
            iloczyn_prefiksowy[i] = i * iloczyn_prefiksowy[i - 1];
            iloczyn_prefiksowy[i] %= m;

            iloczyn_sufiksowy[n - i - 1] = (n - i) * iloczyn_sufiksowy[n - i];
            iloczyn_sufiksowy[n - i - 1] %= m;
        }

        /// <last>
        solvniete[1] = (n * (n + 1) / 2 - 1) % m;
        solvniete[n] = (n * (n - 1) / 2) % m;
        solvniete[n - 1] = (n * (n - 1) / 2 - 1) % m;
        solvniete[2] = (solvniete[n - 1] + 2 * (n - 1)) % m;

        for (int i = 3; i < n - 1; i++)
            solvniete[i] = (solvniete[i - 1] + (n - i)) % m;

        /// <smieszna_suma>

        for (int i = 1; i < n; i++) {
            long mnoznik = silnia_bez_k(n - i);
            long sumnik = n * (n - 1) / 2;
            long odjemnik = (i) * (i - 1) / 2;

            smieszna_suma[i] = mnoznik * ((sumnik - odjemnik) % m) % m;
            suma_prefiksowa[i] = suma_prefiksowa[i - 1] + smieszna_suma[i];
        }

        /// <wyniki><easy>

        wynik_czastkowy[1] = (((((n * (n - 1) + solvniete[1]) % m) * silnia_bez_k(2)) % m - n * (n - 1)) % m + m) % m;
        wynik_czastkowy[n - 1] = ((((n - 2) * (n - 2) + solvniete[n - 1]) % m) * silnia_bez_k(2)) % m;
        wynik_czastkowy[n] = ((((n - 1) * (n - 1) + solvniete[n]) % m) * silnia_bez_k(2)) % m;

        /// <hard>

        for (int i = 2; i < n - 1; i++) {
            wynik_czastkowy[i] = (((n * (n - 1) + solvniete[i]) % m) * silnia_bez_k(2)) % m;
            wynik_czastkowy[i] += suma_prefiksowa[i - 1] - (((n * (n - 1)) % m) * iloczyn_sufiksowy[n - i]) % m;
            wynik_czastkowy[i] = ((wynik_czastkowy[i] % m) + m) % m;
        }

        /// <end>

        long wynik = 0;

        for (int i = 1; i <= n; i++)
            wynik = (wynik + wynik_czastkowy[i]) % m;


        System.out.println(wynik);
    }
}
