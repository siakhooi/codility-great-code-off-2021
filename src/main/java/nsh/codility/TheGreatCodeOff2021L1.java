package nsh.codility;

public class TheGreatCodeOff2021L1 implements TheGreatCodeOff2021Interface {
	interface NodeInterface {
		void init(int from, int to, int layer);

		void process(int from, int to, int a, int b, int c);

		int getCount(int from, int to);

		boolean isValid();
	}

	class NodeA implements NodeInterface {
		int layer = 0;
		boolean isNode = true;
		NodeInterface left, right;

		NodeA(int from, int to) {
			if (to - from < LAST_NODE_MAX_SIZE) {
				left = new NodeB();
				right = new NodeB();
			} else {
				int dividePoint = (from + to) >> 1;
				left = new NodeA(from, dividePoint);
				right = new NodeA(dividePoint + 1, to);
			}
		}

		public void init(int from, int to, int layer) {
			this.layer = layer;
		}

		public void process(int from, int to, int a, int b, int c) {
			if (this.isNode) {
				if (from >= a && to <= b) {
					if (this.layer + 1 == c) { // good
						this.layer++;
					} else { // bad
						this.layer = INVALID;
					}
					return;
				} else {
					// split
					this.isNode = false;
					int dividePoint = (from + to) >> 1;
					left.init(from, dividePoint, this.layer);
					right.init(dividePoint + 1, to, this.layer);
				}
			}
			int dividePoint = (from + to) >> 1;
			if (left.isValid() && a <= dividePoint) {
				left.process(from, dividePoint, a, b, c);

			}
			if (right.isValid() && b > dividePoint) {
				right.process(dividePoint + 1, to, a, b, c);
			}
		}

		public int getCount(int from, int to) {
			if (layer == INVALID)
				return 0;
			if (isNode && layer == K)
				return to - from + 1;
			if (!isNode) {
				int dividePoint = (from + to) >> 1;
				return left.getCount(from, dividePoint) + right.getCount(dividePoint + 1, to);
			}
			return 0;
		}

		public final boolean isValid() {
			return layer != INVALID;
		}
	}

	class NodeB implements NodeInterface {
		private int[] layers = new int[ARRAY_SIZE];
		int goodCount = 0;

		public void init(int from, int to, int layer) {
			for (int i = from; i <= to; i++)
				layers[goodCount++] = layer;
		}

		public void process(int from, int to, int a, int b, int c) {
			int f = Math.max(from, a);
			int t = Math.min(to, b);
			int j = f - from;
			for (int i = f; i <= t; i++) {
				if (layers[j] == INVALID)
					;
				else if (layers[j] + 1 == c) {
					layers[j]++;
				} else {
					layers[j] = INVALID;
					goodCount--;
				}
				j++;
			}
		}

		public int getCount(int from, int to) {
			if (goodCount == 0)
				return 0;
			int j = 0;
			int r = 0;
			for (int i = from; i <= to; i++)
				if (layers[j++] == K)
					r++;

			return r;
		}

		public boolean isValid() {
			return goodCount > 0;
		}

	}

	int K;
	final int ARRAY_SIZE = 4096;
	final int LAST_NODE_MAX_SIZE = 8192;
	final int INVALID = -99;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;
		NodeInterface TOP = new NodeA(1, N);

		int m = A.length;

		for (int i = 0; i < m; i++) {
			int a = A[i], b = B[i], c = C[i];
			TOP.process(1, N, a, b, c);
		}
		return TOP.getCount(1, N);
	}
}