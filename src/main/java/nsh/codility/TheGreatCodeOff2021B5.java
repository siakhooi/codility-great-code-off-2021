package nsh.codility;

public class TheGreatCodeOff2021B5 implements TheGreatCodeOff2021Interface {
	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		int[] sl = new int[N];
		int[] sf = new int[N];
		int[] st = new int[N];
		int[] ss = new int[N]; // 0=blank, 1=good, -1=bad

		int si = 0;
		sl[si] = 0;
		ss[si] = 0;
		sf[si] = 1;
		st[si] = N;

		int total_good = 0;

		for (int i = 0; i < A.length; i++) {
			int a = A[i];
			int b = B[i];
			int c = C[i];

			int oldsi = si;

			for (int j = 0; j <= oldsi; j++) {

				if (b < sf[j])
					continue;
				else if (a > st[j])
					continue;
				else if (ss[j] == -1)
					continue;
				else if (a <= sf[j] && b >= st[j]) {
					// whole segments
					if (sl[j] + 1 == c) {
						sl[j] = c;
						ss[j] = 1;
						if (c == K) {
							total_good += (st[j] - sf[j] + 1);
						}
					} else {
						// bad
						ss[j] = -1;
						if (sl[j] == K) {
							total_good -= (st[j] - sf[j] + 1);
						}
					}
				} else if (a > sf[j] && b < st[j]) {
					// split 3

					si++;
					sl[si] = sl[j];
					ss[si] = ss[j];
					sf[si] = sf[j];
					st[si] = a - 1;

					if (sl[j] + 1 == c) {
						if (c == K) {
							total_good += (b - a + 1);
						}

						si++;
						sl[si] = sl[j];
						ss[si] = ss[j];
						sf[si] = b + 1;
						st[si] = st[j];

						sl[j] = c;
						ss[j] = 1;
						sf[j] = a;
						st[j] = b;

					} else {
						if (sl[j] == K) {
							total_good -= (b - a + 1);
						}

						sf[j] = b + 1;
					}

				} else if (a <= sf[j] && b < st[j]) {
					// split right
					if (sl[j] + 1 == c) {
						if (c == K) {
							total_good += (b - sf[j] + 1);
						}

						si++;
						sl[si] = sl[j];
						ss[si] = ss[j];
						sf[si] = b + 1;
						st[si] = st[j];

						sl[j] = c;
						ss[j] = 1;
						st[j] = b;
					} else {
						if (sl[j] == K) {
							total_good -= (b - sf[j] + 1);
						}
						sf[j] = b + 1;
					}

				} else if (a > sf[j] && b >= st[j]) {
					// split left
					if (sl[j] + 1 == c) {
						if (c == K) {
							total_good += (st[j] - a + 1);
						}

						si++;
						sl[si] = sl[j];
						ss[si] = ss[j];
						sf[si] = sf[j];
						st[si] = a - 1;

						sl[j] = c;
						ss[j] = 1;
						sf[j] = a;

					} else {
						if (sl[j] == K) {
							total_good -= (st[j] - a + 1);
						}
						st[j] = a - 1;
					}

				}
			}
		}

		return total_good;
	}

}
