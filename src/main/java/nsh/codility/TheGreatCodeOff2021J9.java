package nsh.codility;

public class TheGreatCodeOff2021J9 implements TheGreatCodeOff2021Interface {
	class Node {
		int layer = 0;
		boolean isNode = true;

		Node left, right;

		Node(int from, int to) {
			if (from != to) {
				int dividePoint = (from + to) >> 1;
				left = new Node(from, dividePoint);
				right = new Node(dividePoint + 1, to);
			}
		}

		int process(int from, int to, int a, int b, int c) {
			if (layer == INVALID || a > to || b < from)
				return isNode ? layer : ISPARENT;
			if (this.isNode) {
				if (from >= a && to <= b) {
					if (this.layer + 1 == c) { // good
						this.layer++;
					} else { // bad
						this.layer = INVALID;
					}
					return layer;
				} else {
					// split
					this.isNode = false;
					right.layer = left.layer = this.layer;
				}
			}
			int dividePoint = (from + to) >> 1;
			int ll = left.process(from, dividePoint, a, b, c);
			int rr = right.process(dividePoint + 1, to, a, b, c);
			if (ll == rr && ll != ISPARENT) {
				this.isNode = true;
				layer = ll;
				return layer;
			}
			return ISPARENT;
		}

		public int getCount(int from, int to, int K) {
			if (layer == INVALID)
				return 0;
			if (isNode && layer == K)
				return to - from + 1;
			if (from != to) {
				int dividePoint = (from + to) >> 1;
				return left.getCount(from, dividePoint, K) + right.getCount(dividePoint + 1, to, K);
			}
			return 0;
		}
	}

	final int INVALID = -99;
	final int ISPARENT = -100;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {

		Node TOP = new Node(1, N);
		int m = A.length;

		for (int i = 0; i < m; i++) {
			int a = A[i], b = B[i], c = C[i];

			TOP.process(1, N, a, b, c);
		}

		return TOP.getCount(1, N, K);
	}
}
