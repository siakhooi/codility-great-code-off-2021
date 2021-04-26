package nsh.codility;

public class TheGreatCodeOff2021J5 implements TheGreatCodeOff2021Interface {
	class Node {
		int from, to, layer, size;
		boolean isNode = true;
		boolean valid = true;

		Node left, right;
		int dividePoint;

		Node(int from, int to, int layer) {
			this.from = from;
			this.to = to;
			this.layer = layer;
			this.size = to - from + 1;
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
			if (b < this.from)
				return;
			if (a > this.to)
				return;
			if (a < from)
				a = from;
			if (b > to)
				b = to;
			if (this.isNode) {
				if (isCoverFullSize(a, b)) {
					if (this.layer + 1 == c) { // good
						this.layer++;
					} else { // bad
						this.valid = false;
						return;
					}
				} else {
					this.isNode = false;
					this.left.layer = this.layer;
					this.right.layer = this.layer;
				}
			}
			if (!this.isNode) {
				if (a < dividePoint)
					left.process(a, b, c);
				if (b >= dividePoint)
					right.process(a, b, c);

				if (!left.valid && !right.valid) {
					this.valid = false;
					return;
				}

				if (left.isNode && right.isNode && left.layer == right.layer && left.valid && right.valid) {
					this.isNode = true;
					this.layer = left.layer;
				}
			}

		}

		public int getCount() {
			if (!valid)
				return 0;
			if (isNode && layer == K)
				return size;
			if (!isNode)
				return left.getCount() + right.getCount();
			return 0;
		}
	}

	int K;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;

		Node TOP = new Node(1, N, 0);

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];

			TOP.process(a, b, c);
		}

		return TOP.getCount();
	}
}