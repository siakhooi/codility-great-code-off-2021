package nsh.codility;

public class TheGreatCodeOff2021J4 implements TheGreatCodeOff2021Interface {
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
					}
				} else {
					// split
					this.isNode = false;
					left = new Node(this.from, dividePoint - 1, this.layer);
					right = new Node(dividePoint, this.to, this.layer);
				}

			}
			if (!this.isNode) {
				if (left != null && a < dividePoint) {
					left.process(a, b, c);
					if (!left.isNode & left.valid && (left.left == null || left.right == null)) {
						left = left.right != null ? left.right : left.left;
					}
					if (!left.valid)
						left = null;

				}
				if (right != null && b >= dividePoint) {
					right.process(a, b, c);
					if (!right.isNode & right.valid && (right.left == null || right.right == null)) {
						right = right.right != null ? right.right : right.left;
					}

					if (!right.valid)
						right = null;
				}

				if (left == null && right == null) {
					this.valid = false;
					return;
				}

				if (left != null && right != null && left.isNode && right.isNode && left.layer == right.layer
						&& left.to + 1 == right.from) {
					this.isNode = true;
					this.from = left.from;
					this.to = right.to;
					this.layer = left.layer;
					this.size = this.to - this.from + 1;
					this.left = null;
					this.right = null;
					this.dividePoint = ((this.from + this.to + 1) >>> 1);
				}
			}

		}

		public int getCount() {
			if (!valid)
				return 0;
			if (isNode && layer == K)
				return size;

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

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];

			TOP.process(a, b, c);
		}

		return TOP.getCount();
	}
}
