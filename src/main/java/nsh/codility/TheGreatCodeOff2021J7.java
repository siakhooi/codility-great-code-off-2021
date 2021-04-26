package nsh.codility;

public class TheGreatCodeOff2021J7 implements TheGreatCodeOff2021Interface {
	class Node {
		int from, to, layer;
		boolean isNode = true;
		boolean valid = true;

		Node left, right;
		int dividePoint;

		Node(int from, int to, int layer) {
			this.from = from;
			this.to = to;
			this.layer = layer;
			this.dividePoint = (from + to + 1) >>> 1;

			if (from != to) {
				left = new Node(from, dividePoint - 1, layer);
				right = new Node(dividePoint, to, layer);
			}
		}

		boolean isCoverFullSize(int a, int b) {
			return this.from >= a && this.to <= b;
		}

		void process(int a, int b, int c) {
			if (this.isNode) {
				if (isCoverFullSize(a, b)) {
					if (this.layer + 1 == c) { // good
						this.layer++;
					} else { // bad
						this.valid = false;
					}
					return;
				} else {
					// split
					this.isNode = false;
					left.layer = this.layer;
					right.layer = this.layer;
				}
			}
			if (left.valid && a < dividePoint) {
				left.process(a, b, c);

			}
			if (right.valid && b >= dividePoint) {
				right.process(a, b, c);
			}
			if (!left.valid && !right.valid) {
				this.valid = false;
				return;
			}

			if (left.valid && right.valid && left.isNode && right.isNode && left.layer == right.layer) {
				this.isNode = true;
				this.layer = left.layer;
			}
		}

		public int getCount() {
			if (!valid)
				return 0;
			if (isNode && layer == K)
				return to - from + 1;

			if (left != null) {
				if (right != null)
					return left.getCount() + right.getCount();
				else
					return left.getCount();

			} else {
				if (right != null)
					return right.getCount();
				else
					return 0;
			}
		}
	}

	int K;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;

		Node TOP = new Node(1, N, 0);
		int m = A.length;

		for (int i = 0; i < m; i++) {
			int a = A[i], b = B[i], c = C[i];

			TOP.process(a, b, c);
		}

		return TOP.getCount();
	}
}
