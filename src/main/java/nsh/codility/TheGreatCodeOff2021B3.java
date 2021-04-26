package nsh.codility;

public class TheGreatCodeOff2021B3 implements TheGreatCodeOff2021Interface {
	int[] sl;
	int[] sf;
	int[] st;
	int[] ss;
	int si = -1;

	int[] x;
	int xi = -1;

	void unwant(int j) {
		ss[j] = -1;
		x[++xi] = j;
	}

	void addLine(int l, int s, int f, int t) {
		if (xi > -1) {
			int v = x[xi];
			sl[v] = l;
			ss[v] = s;
			sf[v] = f;
			st[v] = t;
			xi--;
		} else {
			si++;
			sl[si] = l;
			ss[si] = s;
			sf[si] = f;
			st[si] = t;
		}
	}

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		sl = new int[N];
		sf = new int[N];
		st = new int[N];
		ss = new int[N]; // 0=blank, 1=good, -1=bad
		x = new int[N];// unwanted

		addLine(0, 0, 1, N);

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

					} else {
						unwant(j);
					}
				} else if (a > sf[j] && b < st[j]) {
					// split 3

					addLine(sl[j], ss[j], sf[j], a - 1);

					if (sl[j] + 1 == c) {
						addLine(sl[j], ss[j], b + 1, st[j]);

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
						addLine(sl[j], ss[j], b + 1, st[j]);

						sl[j] = c;
						ss[j] = 1;
						st[j] = b;
					} else {
						sf[j] = b + 1;
					}

				} else if (a > sf[j] && b >= st[j]) {
					// split left
					if (sl[j] + 1 == c) {
						addLine(sl[j], ss[j], sf[j], a - 1);

						sl[j] = c;
						ss[j] = 1;
						sf[j] = a;

					} else {
						st[j] = a - 1;
					}

				}
			}
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
