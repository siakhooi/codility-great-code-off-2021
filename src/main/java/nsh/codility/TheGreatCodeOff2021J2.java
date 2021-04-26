package nsh.codility;

public class TheGreatCodeOff2021J2 implements TheGreatCodeOff2021Interface {
	class Result {
		int total_good = 0;
		int K;

		public void addIfMeetK(int layer, int size) {
			if (layer == this.K)
				total_good += size;

		}

		public void minusIfMeetK(int layer, int size) {
			if (layer == this.K)
				total_good -= size;

		}
	}

	Result R = new Result();

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
			if (!valid)
				return;
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
						R.addIfMeetK(this.layer, this.size);
					} else { // bad
						R.minusIfMeetK(this.layer, this.size);
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

				}
				if (right != null && b >= dividePoint) {
					right.process(a, b, c);
				}
			}

		}

	}

	Node TOP;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		R.K = K;

		TOP = new Node(1, N, 0);

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];

			TOP.process(a, b, c);
		}

		return R.total_good;
	}
}
