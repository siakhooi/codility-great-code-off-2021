package nsh.codility;

public class TheGreatCodeOff2021B2 implements TheGreatCodeOff2021Interface {
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

		for (int i = 0; i < A.length; i++) {
			int a = A[i];
			int b = B[i];
			int c = C[i];

			int newsi = si;

			for (int j = 0; j <= si; j++) {

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

					} else {
						ss[j] = -1;
					}
				} else if (a > sf[j] && b < st[j]) {
					// split 3

					newsi++;
					sl[newsi] = sl[j];
					ss[newsi] = ss[j];
					sf[newsi] = sf[j];
					st[newsi] = a - 1;

					if (sl[j] + 1 == c) {
						newsi++;
						sl[newsi] = sl[j];
						ss[newsi] = ss[j];
						sf[newsi] = b + 1;
						st[newsi] = st[j];

						sl[j] = c;
						ss[j] = 1;
						sf[j] = a;
						st[j] = b;

					} else {
						sf[j] = b + 1;
					}

				} else if (a <= sf[j] && b < st[j]) {
					// split right
					if (sl[j] + 1 == c) {

						newsi++;
						sl[newsi] = sl[j];
						ss[newsi] = ss[j];
						sf[newsi] = b + 1;
						st[newsi] = st[j];

						sl[j] = c;
						ss[j] = 1;
						st[j] = b;
					} else {
						sf[j] = b + 1;
					}

				} else if (a > sf[j] && b >= st[j]) {
					// split left
					if (sl[j] + 1 == c) {
						newsi++;
						sl[newsi] = sl[j];
						ss[newsi] = ss[j];
						sf[newsi] = sf[j];
						st[newsi] = a - 1;

						sl[j] = c;
						ss[j] = 1;
						sf[j] = a;

					} else {
						st[j] = a - 1;
					}

				}
			}
			si = newsi;
		}
		int r = 0;
		for (int i = 0; i <= si; i++) {
			if (ss[i] == 1 && sl[i] == K) {
				r += (st[i] - sf[i] + 1);
			}
		}

		return r;
	}

}
