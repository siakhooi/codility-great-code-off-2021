package nsh.codility;

public class TheGreatCodeOff2021K6 implements TheGreatCodeOff2021Interface {
	class Node {
		int layer;
		boolean isParent;
		boolean leftGone, rightGone;
	}

	boolean process(int index, int from, int to, int a, int b, int c, boolean initFromParent, int initLayer) {
		int dividePoint = (from + to) >> 1;
		Node n = AllNodes[index];
		if (initFromParent) {
			n.layer = initLayer;
			if (a > to || b < from)
				return false; // just init
		}

		if (n.layer == -1)
			return true;
		int l = index << 1;
		int r = index << 1 | 1;

		if (!n.isParent) {
			if (from >= a && to <= b) {
				if (n.layer + 1 == c) { // good
					n.layer++;
				} else { // bad
					n.layer = -1;
					return true;
				}
			} else {
				n.isParent = true;
				n.leftGone = process(l, from, dividePoint, a, b, c, true, n.layer);
				n.rightGone = process(r, dividePoint + 1, to, a, b, c, true, n.layer);
				if (n.leftGone && n.rightGone) {
					n.layer = -1;
					return true;
				}
			}
		} else if (n.isParent) {
			if (a <= dividePoint)
				n.leftGone = process(l, from, dividePoint, a, b, c, false, 0);
			if (b > dividePoint)
				n.rightGone = process(r, dividePoint + 1, to, a, b, c, false, 0);

			if (n.leftGone && n.rightGone) {
				n.layer = -1;
				return true;
			}

		}
		return false; // not gone yet
	}

	public int getCount(int index, int from, int to) {
		int dividePoint = (from + to) >> 1;
		Node n = AllNodes[index];
		if (n.layer == -1)
			return 0;
		if (!n.isParent && n.layer == K)
			return to - from + 1;
		if (n.isParent)
			return getCount(index << 1, from, dividePoint) + getCount(index << 1 | 1, dividePoint + 1, to);
		return 0;
	}

	int K;
	Node[] AllNodes;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;
		int n = 1;
		while (n < N)
			n <<= 1;
		n <<= 1;
		AllNodes = new Node[n];
		for (int i = 1; i < n; i++)
			AllNodes[i] = new Node();

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];
			process(1, 1, N, a, b, c, false, 0);
		}
		return getCount(1, 1, N);
	}

}