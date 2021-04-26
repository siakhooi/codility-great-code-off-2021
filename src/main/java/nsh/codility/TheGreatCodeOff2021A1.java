package nsh.codility;

public class TheGreatCodeOff2021A1 implements TheGreatCodeOff2021Interface {
	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		int[] CAKE = new int[N + 1];
		int r = N;
		for (int i = 0; i < A.length; i++) {
			int a = A[i];
			int b = B[i];
			int c = C[i];

			for (int j = a; j <= b; j++) {
				int cake = CAKE[j];
				if (cake + 1 == c) {
					CAKE[j]++;
				} else if (cake == -1) {
					continue;
				} else {
					r--;
					CAKE[j] = -1;
				}
			}
		}
		for (int i = 1; i <= N; i++) {
			if (CAKE[i] != K && CAKE[i] != -1)
				r--;
		}
		return r;
	}
}
