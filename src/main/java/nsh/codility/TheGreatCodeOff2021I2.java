package nsh.codility;

public class TheGreatCodeOff2021I2 implements TheGreatCodeOff2021Interface {
	class Node {
		int layer;
		boolean isNode = true;

		Node left, right;

		Node(int from, int to, int layer) {
			this.layer = layer;
			int dividePoint = (from + to) >> 1;
			if (from != to) {
				this.left = new Node(from, dividePoint, layer);
				this.right = new Node(dividePoint + 1, to, layer);
			}
		}

		void process(int from, int to, int a, int b, int c) {
			if (this.isNode) {
				if (from >= a && to <= b) {
					if (this.layer + 1 == c) { // good
						this.layer++;
					} else { // bad
						this.layer = -1;
					}
					return;
				} else {
					// split
					this.isNode = false;
					left.layer = this.layer;
					right.layer = this.layer;
				}

			}
			int dividePoint = (from + to) >> 1;
			if (left != null && a <= dividePoint) {
				left.process(from, dividePoint, a, b, c);
			}
			if (right != null && b > dividePoint) {
				right.process(dividePoint + 1, to, a, b, c);
			}

			if (left.layer == -1 && right.layer == -1) {
				this.layer = -1;
				return;
			}

			if (left.isNode && right.isNode && left.layer == right.layer) {
				this.isNode = true;
				this.layer = left.layer;
			}

		}

		public int getCount(int from, int to) {
			if (layer == -1)
				return 0;
			if (isNode && layer == K)
				return to - from + 1;

			if (!this.isNode) {
				int dividePoint = (from + to) >> 1;
				return left.getCount(from, dividePoint) + right.getCount(dividePoint + 1, to);
			}
			return 0;
		}
	}

	final int N_PER_GROUP = 4096;
	final int BIT = 12; // 2^12=4096
	final int BIT_MASK = 4095;
	int K;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;

		int gsize = N >> BIT;
		int lastsize = N & BIT_MASK;
		if (lastsize > 0)
			gsize++;
		Node[] G = new Node[gsize];
		int[] base = new int[gsize];

		for (int i = 0; i < gsize; i++) {
			base[i] = i << BIT; // i*N_PER_GROUP
			G[i] = new Node(1, i == gsize - 1 ? lastsize : N_PER_GROUP, 0);
		}

		int m = A.length;
		for (int i = 0; i < m; i++) {
			int a = A[i], b = B[i], c = C[i];

			int g1 = (a - 1) >> BIT;
			int g2 = b >> BIT;
			if ((b & BIT_MASK) == 0)
				g2--;

			for (int g = g1; g <= g2; g++) {
				Node G1 = G[g];
				if (G1.layer == -1)
					continue;
				int e = g == gsize - 1 ? lastsize : N_PER_GROUP;
				G1.process(1, e, a - base[g], b - base[g], c);
			}

		}
		int r = 0;
		for (int i = 0; i < gsize; i++) {
			r += G[i].getCount(1, i == gsize - 1 ? lastsize : N_PER_GROUP);
		}

		return r;
	}
}
