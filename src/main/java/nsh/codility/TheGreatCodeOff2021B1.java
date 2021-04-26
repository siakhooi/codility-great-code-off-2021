package nsh.codility;

public class TheGreatCodeOff2021B1 implements TheGreatCodeOff2021Interface {
	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		int[] s_layer = new int[N];
		int[] s_from = new int[N];
		int[] s_to = new int[N];
		int[] s_status = new int[N]; // 0=blank, 1=good, -1=bad

		int skindex = 0;
		s_layer[skindex] = 0;
		s_status[skindex] = 0;
		s_from[skindex] = 1;
		s_to[skindex] = N;

		for (int i = 0; i < A.length; i++) {
			int a = A[i];
			int b = B[i];
			int c = C[i];

			int newindex = skindex;

			for (int j = 0; j <= skindex; j++) {

				if (b < s_from[j])
					continue;
				else if (a > s_to[j])
					continue;
				else if (s_status[j] == -1)
					continue;
				else if (a <= s_from[j] && b >= s_to[j]) {
					// whole segments
					if (s_layer[j] + 1 == c) {
						s_layer[j] = c;
						s_status[j] = 1;

					} else {
						s_status[j] = -1;
					}
				} else if (a > s_from[j] && b < s_to[j]) {
					// split 3

					newindex++;
					s_layer[newindex] = s_layer[j];
					s_status[newindex] = s_status[j];
					s_from[newindex] = s_from[j];
					s_to[newindex] = a - 1;

					newindex++;
					s_layer[newindex] = s_layer[j];
					s_status[newindex] = s_status[j];
					s_from[newindex] = b + 1;
					s_to[newindex] = s_to[j];

					s_from[j] = a;
					s_to[j] = b;
					if (s_layer[j] + 1 == c) {
						s_layer[j] = c;
						s_status[j] = 1;

					} else {
						s_status[j] = -1;
					}

				} else if (a <= s_from[j] && b < s_to[j]) {
					// case 4-a
					newindex++;
					s_layer[newindex] = s_layer[j];
					s_status[newindex] = s_status[j];
					s_from[newindex] = b + 1;
					s_to[newindex] = s_to[j];

					s_to[j] = b;
					if (s_layer[j] + 1 == c) {
						s_layer[j] = c;
						s_status[j] = 1;

					} else {
						s_status[j] = -1;
					}

				} else if (a > s_from[j] && b >= s_to[j]) {
					// case 4-b
					newindex++;
					s_layer[newindex] = s_layer[j];
					s_status[newindex] = s_status[j];
					s_from[newindex] = s_from[j];
					s_to[newindex] = a - 1;

					s_from[j] = a;
					if (s_layer[j] + 1 == c) {
						s_layer[j] = c;
						s_status[j] = 1;

					} else {
						s_status[j] = -1;
					}
				}
			}
			skindex = newindex;
		}
		int r = 0;
		for (int i = 0; i <= skindex; i++) {
			if (s_status[i] == 1 && s_layer[i] == K) {
				r += (s_to[i] - s_from[i] + 1);
			}
		}

		return r;
	}
}
