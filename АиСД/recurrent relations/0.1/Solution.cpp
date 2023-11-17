#include <iostream>

using namespace std;

void findMaxPath(int* arr, int n) {
    pair<int, int>* maxPath = new pair<int, int>[n + 1];
    maxPath[0].first = arr[0];
    maxPath[0].second = -1;
    maxPath[1].first = -1;
    maxPath[1].second = -1;

    for (int i = 2; i < n; i++) {
        if (i >= 2) {
            if (maxPath[i - 2].first + arr[i] > maxPath[i].first) {
                maxPath[i].first = maxPath[i - 2].first + arr[i];
                maxPath[i].second = i - 2;
            }
        }
        if (i >= 3) {
            if (maxPath[i - 3].first + arr[i] > maxPath[i].first) {
                maxPath[i].first = maxPath[i - 3].first + arr[i];
                maxPath[i].second = i - 3;
            }
        }
    }

    if (maxPath[n - 1].first < 1) {
        cout << -1;
        return;
    }

    cout << maxPath[n - 1].first << "\n";

    int* res = new int[n];
    int l = 1;

    res[0] = n;
    for (int i = n - 1; maxPath[i].second != -1; l++) {
        res[l] = maxPath[i].second + 1;
        i = maxPath[i].second;
    }
    for (int i = l - 1; i >= 0; i--) {
        cout << res[i] << " ";
    }
    delete[] maxPath;
    delete[] res;

}

int main() {
    int n;
    cin >> n;

    int* a = new int[n];

    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }

    findMaxPath(a, n);

    delete[] a;
    return 0;
}
