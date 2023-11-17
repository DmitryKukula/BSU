#include <iostream>

using namespace std;

const int MOD = 1000000007;
int a[1001][1001];

void triangle(int n) {
    a[0][0] = 1;
    for (int i = 1; i <= n; i++) {
        a[i][0] = 1;
        a[i][i] = 1;
        for (int j = 1; j < i; j++) {
            a[i][j] = (a[i - 1][j - 1] + a[i - 1][j]) % MOD;
        }
    }
}

int main()
{
    int n, k;
    scanf_s("%d %d", &n, &k);
    triangle(n);
    printf("%d", a[n][k]);
    return 0;
}
