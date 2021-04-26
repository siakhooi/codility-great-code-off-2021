package nsh.codility;

public class TheGreatCodeOff2021K4 implements TheGreatCodeOff2021Interface {
	void process(int index, int from, int to, int a, int b, int c) {
		int dividePoint = (from + to) >> 1;
		int layer = LAYER[index];

		if (layer == -1)
			return;
		int l = index << 1;
		int r = index << 1 | 1;

		boolean isParent = ISPARENT[index];

		if (!isParent) {
			if (from >= a && to <= b) {
				if (layer + 1 == c) { // good
					LAYER[index]++;
				} else { // bad
					LAYER[index] = -1;
					return;
				}
			} else {
				ISPARENT[index] = true;
				isParent = true;
				LAYER[l] = layer;
				LAYER[r] = layer;
			}
		}
		if (isParent) {
			if (a <= dividePoint)
				process(l, from, dividePoint, a, b, c);
			if (b > dividePoint)
				process(r, dividePoint + 1, to, a, b, c);

			if (LAYER[l] == -1 && LAYER[r] == -1)
				LAYER[index] = -1;
		}
	}

	public int getCount(int index, int from, int to) {
		int dividePoint = (from + to) >> 1;
		int l = LAYER[index];
		if (l == -1)
			return 0;
		boolean isParent = ISPARENT[index];
		if (!isParent && l == K)
			return to - from + 1;
		if (isParent)
			return getCount(index << 1, from, dividePoint) + getCount(index << 1 | 1, dividePoint + 1, to);
		return 0;
	}

	int K;
	int[] LAYER;
	boolean[] ISPARENT;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;
		int n = 1;
		while (n < N)
			n <<= 1;
		n <<= 1;
		LAYER = new int[n];
		ISPARENT = new boolean[n];

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];
			process(1, 1, N, a, b, c);
		}
		return getCount(1, 1, N);
	}
}