package nsh.codility;

public class TheGreatCodeOff2021Z1 implements TheGreatCodeOff2021Interface {
	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		int[] form = new int[N];
		for (int i = 0; i < A.length; i++) {
			int a = A[i];
			int b = B[i];
			int c = C[i];

			for (int j = a - 1; j < b; j++)
				form[j] = (form[j] == c - 1) ? c : -1;
		}
		int r = 0;
		for (int i = 0; i < N; i++)
			if (form[i] == K)
				r++;
		return r;
	}
}
