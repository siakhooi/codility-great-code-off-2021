package nsh.codility;

public class TheGreatCodeOff2021K8 implements TheGreatCodeOff2021Interface {
	class Node {
		int layer;
		boolean isParent;
	}

	void process(int index, int from, int to, int a, int b, int c) {
		Node n = AllNodes[index];
		if (!n.isParent) {
			if (from >= a && to <= b) {
				if (n.layer + 1 == c) { // good
					n.layer++;
				} else { // bad
					n.layer = -1;
				}
				return;
			} else {
				n.isParent = true;
				AllNodes[index << 1].layer = n.layer;
				AllNodes[index << 1 | 1].layer = n.layer;
			}
		}
		int dividePoint = (from + to) >> 1;
		int l = index << 1, r = l | 1;
		if (a <= dividePoint)
			process(l, from, dividePoint, a, b, c);
		if (b > dividePoint)
			process(r, dividePoint + 1, to, a, b, c);

		Node Left = AllNodes[l];
		Node Right = AllNodes[r];
		if (Left.layer == -1 && Right.layer == -1) {
			n.layer = -1;
			return;
		}
		if (!Left.isParent && !Right.isParent && Left.layer == Right.layer) {
			n.isParent = false;
			n.layer = Left.layer;
		}

	}

	public int getCount(int index, int from, int to) {
		Node n = AllNodes[index];
		if (n.layer == -1)
			return 0;
		if (!n.isParent && n.layer == K)
			return to - from + 1;
		if (n.isParent) {
			int dividePoint = (from + to) >> 1;
			return getCount(index << 1, from, dividePoint) + getCount(index << 1 | 1, dividePoint + 1, to);
		}
		return 0;
	}

	Node[] AllNodes;
	int K;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;
		int n = 1;
		while (n < N)
			n <<= 1;
		n <<= 1;
		AllNodes = new Node[n];
		for (int i = 1; i < n; i++)
			AllNodes[i] = new Node();

		int m = A.length;

		for (int i = 0; i < m; i++) {
			int a = A[i], b = B[i], c = C[i];
			process(1, 1, N, a, b, c);
		}
		return getCount(1, 1, N);
	}

}