/// Kuba Ornatek 3Bg, LXX LO Warszawa - XXIX OI, etap I - Układanie kart
/// Pozdrawiam matematykę
/// O(n)

#include <iostream>
#include <vector>
using namespace std;

#define ll long long
#define f first
#define s second

const ll MAX_N = 10000 * 1000 + 5;

ll n, m;

/// (n-1)! / (n-k)
ll iloczyn_prefiksowy[MAX_N];
ll iloczyn_sufiksowy[MAX_N];

/// last_permutation_cost* -> taki troche oszukany
ll solvniete[MAX_N];

/// wyniki
ll wynik_czastkowy[MAX_N];

/// smieszna suma -> nie wiem co robi XD
ll smieszna_suma[MAX_N];
ll suma_prefiksowa[MAX_N]; // s_p[k-1] - ile trzeba odjac dla k

void smart() {
    /// profesjonalna funkcja gwarantująca 10 punktów O(1)!
    if (n == 2) cout << 1 % m;
    if (n == 3) cout << 15 % m;
    if (n == 4) cout << 168 % m;
    if (n == 5) cout << 1700 % m;
    if (n == 6) cout << 17220 % m;
    if (n == 7) cout << 182406 % m;
    if (n == 8) cout << 2055200 % m;
    if (n == 9) cout << 24767928 % m;
    if (n == 10) cout << 319463100 % m;
}

ll silnia_bez_k(ll k) {
    /// formalnie: (n-1)! / (n-k)
    // warunek: k -> <1, n-1>
    ll silnia_bez = (iloczyn_prefiksowy[k - 1] * iloczyn_sufiksowy[k]) % m;
    return silnia_bez;
}

int main() {
    std::ios_base::sync_with_stdio(false);

    cin >> n >> m;

    if (n <= 3) {
        smart();
        return 0;
    }

    /// <iloczyn>

    iloczyn_prefiksowy[0] = 1;
    iloczyn_sufiksowy[n - 1] = 1;

    for (ll i = 1; i < n; i++) {
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

    for (ll i = 3; i < n - 1; i++)
        solvniete[i] = (solvniete[i - 1] + (n - i)) % m;

    /// <smieszna_suma>

    for (ll i = 1; i < n; i++) {
        ll mnoznik = silnia_bez_k(n - i);
        ll sumnik = n * (n - 1) / 2;
        ll odjemnik = (i) * (i - 1) / 2;

        smieszna_suma[i] = mnoznik * ((sumnik - odjemnik) % m) % m;
        suma_prefiksowa[i] = suma_prefiksowa[i - 1] + smieszna_suma[i];
    }

    /// <wyniki><easy>

    wynik_czastkowy[1] = (((((n * (n - 1) + solvniete[1]) % m) * silnia_bez_k(2)) % m - n * (n - 1)) % m + m) % m;
    wynik_czastkowy[n - 1] = ((((n - 2) * (n - 2) + solvniete[n - 1]) % m) * silnia_bez_k(2)) % m;
    wynik_czastkowy[n] = ((((n - 1) * (n - 1) + solvniete[n]) % m) * silnia_bez_k(2)) % m;

    /// <hard>

    for (ll i = 2; i < n - 1; i++) {
        wynik_czastkowy[i] = (((n * (n - 1) + solvniete[i]) % m) * silnia_bez_k(2)) % m;
        wynik_czastkowy[i] += suma_prefiksowa[i - 1] - (((n * (n - 1)) % m) * iloczyn_sufiksowy[n - i]) % m;
        wynik_czastkowy[i] = ((wynik_czastkowy[i] % m) + m) % m;
    }

    /// <end>

    ll wynik = 0;

    for (ll i = 1; i <= n; i++)
        wynik = (wynik + wynik_czastkowy[i]) % m;

    cout << wynik << endl;
}