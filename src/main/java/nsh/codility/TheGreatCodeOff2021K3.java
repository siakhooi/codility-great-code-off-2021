package nsh.codility;

public class TheGreatCodeOff2021K3 implements TheGreatCodeOff2021Interface {
	void explode(int index, int from, int to) {
		FROM[index] = from;
		TO[index] = to;
		LAYER[index] = 0;
		DIVIDEPOINT[index] = (from + to) >> 1;
		if (from == to)
			return;
		explode(index << 1, from, DIVIDEPOINT[index]);
		explode(index << 1 | 1, DIVIDEPOINT[index] + 1, to);
	}

	void process(int index, int a, int b, int c) {
		if (ISNOTVALID[index])
			return;
		int l = index << 1;
		int r = index << 1 | 1;
		if (!ISPARENT[index]) {
			if (FROM[index] >= a && TO[index] <= b) {
				if (LAYER[index] + 1 == c) { // good
					LAYER[index]++;
				} else { // bad
					ISNOTVALID[index] = true;
					return;
				}
			} else {
				ISPARENT[index] = true;
				LAYER[l] = LAYER[index];
				LAYER[r] = LAYER[index];
			}
		}
		if (ISPARENT[index]) {
			if (a <= DIVIDEPOINT[index])
				process(l, a, b, c);
			if (b > DIVIDEPOINT[index])
				process(r, a, b, c);

			if (ISNOTVALID[l] && ISNOTVALID[r])
				ISNOTVALID[index] = true;
		}
	}

	public int getCount(int index) {
		if (ISNOTVALID[index])
			return 0;
		if (!ISPARENT[index] && LAYER[index] == K)
			return TO[index] - FROM[index] + 1;
		if (ISPARENT[index])
			return getCount(index << 1) + getCount(index << 1 | 1);
		return 0;
	}

	int K;
	int[] FROM, TO, LAYER, DIVIDEPOINT;
	boolean[] ISPARENT, ISNOTVALID;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;
		int n = 1;
		while (n < N)
			n <<= 1;
		n <<= 1;
		FROM = new int[n];
		TO = new int[n];
		LAYER = new int[n];
		DIVIDEPOINT = new int[n];
		ISPARENT = new boolean[n];
		ISNOTVALID = new boolean[n];
		explode(1, 1, N);

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];
			process(1, a, b, c);
		}
		return getCount(1);
	}
}