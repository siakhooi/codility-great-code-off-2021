package nsh.codility;

public class TheGreatCodeOff2021K2 implements TheGreatCodeOff2021Interface {
	class Node {
		int from, to, layer;
		int dividePoint;
		boolean isNode = true;
		boolean isValid = true;

		Node(int from, int to, int layer) {
			this.from = from;
			this.to = to;
			this.layer = layer;
			this.dividePoint = (from + to) >> 1;
		}
	}

	void explode(int index, int from, int to) {
		AllNode[index] = new Node(from, to, 0);
		if (from == to)
			return;
		explode(index << 1, from, AllNode[index].dividePoint);
		explode(index << 1 | 1, AllNode[index].dividePoint + 1, to);
	}

	void process(int index, int a, int b, int c) {
		Node n = AllNode[index];
		if (!n.isValid)
			return;
		int l = index << 1;
		int r = index << 1 | 1;
		if (n.isNode) {
			if (n.from >= a && n.to <= b) {
				if (n.layer + 1 == c) { // good
					n.layer++;
				} else { // bad
					n.isValid = false;
					return;
				}
			} else {
				n.isNode = false;
				AllNode[l].layer = n.layer;
				AllNode[r].layer = n.layer;
			}
		}
		if (!n.isNode) {
			if (a <= n.dividePoint)
				process(l, a, b, c);
			if (b > n.dividePoint)
				process(r, a, b, c);

			if (!AllNode[l].isValid && !AllNode[r].isValid)
				n.isValid = false;
		}
	}

	public int getCount(int index) {
		Node n = AllNode[index];
		if (!n.isValid)
			return 0;
		if (n.isNode && n.layer == K)
			return n.to - n.from + 1;
		if (!n.isNode)
			return getCount(index << 1) + getCount(index << 1 | 1);
		return 0;
	}

	int K;
	Node[] AllNode;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;
		int n = 1;
		while (n < N)
			n <<= 1;
		n <<= 1;
		AllNode = new Node[n];
		explode(1, 1, N);

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];
			process(1, a, b, c);
		}
		return getCount(1);
	}
}