package nsh.codility;

public class TheGreatCodeOff2021A2 implements TheGreatCodeOff2021Interface {

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		int[] L = new int[N + 1];
		boolean[] S = new boolean[N + 1];

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];

			for (int j = a; j <= b; j++) {
				L[j]++;
				S[j] = S[j] || (c != L[j]);
			}
		}

		int r = 0;
		for (int i = 1; i <= N; i++)
			if (L[i] == K && !S[i])
				r++;
		return r;
	}
}
