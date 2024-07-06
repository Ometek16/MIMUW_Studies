// Matryca

#include <iostream>
using namespace std;

int main() {
	ios_base::sync_with_stdio(fasle);
	cin.tie(0);
	cout.tie(0);

	string s;
	cin >> s;
	int n = s.size();

	char litera = '?';
	int licznik = 0;
	int idx = 0;

	int zla_ramka = n + 1;

	for (int i = 0; i < n; i++) {
		if (s[i] == '*')
			continue;
		
		if (s[i] == litera) {
			licznik++;
			continue;
		}

		zla_ramka = min(zla_ramka, i - idx + 1);

		while (licznik)
			licznik -= (s[idx++] == litera);

		idx = i;
		litera = s[idx];
		licznik = 1;
	}

	
	return zla_ramka - 1;
}
