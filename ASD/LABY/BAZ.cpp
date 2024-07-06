// bazarek

#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

#define ll long long

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int n;
	cin >> n;

	vector<ll> nums(n);
	for (int i = 0; i < n; i++)
		cin >> nums[i];

	sort(nums.begin(), nums.end(), greater<ll>());

	vector<ll> prefi(n + 1);
	vector<pair<ll, ll> > prev(n + 1, make_pair(-1, -1));
	vector<pair<ll, ll> > next(n + 1, make_pair(-1, -1));
	prefi[0] = 0;

	for (int i = 0; i < n; i++) {
		prefi[i + 1] = prefi[i] + nums[i];
		prev[i + 1] = prev[i];
		if (nums[i]&1)
			prev[i + 1].second = nums[i];
		else 
			prev[i + 1].first = nums[i];
	}

	for (int i = n - 1; i >= 0; i--) {
		next[i] = next[i + 1];
		if (nums[i]&1)
			next[i].second = nums[i];
		else
			next[i].first = nums[i];
	}
	
	int m;
	cin >> m;

	while (m--) {
		int q;
		cin >> q;

		if (prefi[q]&1)
			cout << prefi[q] << "\n";
		else {
			ll ans = -1;

			if (prev[q].first != -1 && next[q].second != -1)
				ans = prefi[q] - prev[q].first + next[q].second;

			if (prev[q].second != -1 && next[q].first != -1)
				ans = max(ans, prefi[q] - prev[q].second + next[q].first);

			cout << ans << "\n";
		}
	}
}