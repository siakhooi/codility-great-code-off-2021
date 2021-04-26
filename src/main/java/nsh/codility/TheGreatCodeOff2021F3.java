package nsh.codility;

public class TheGreatCodeOff2021F3 implements TheGreatCodeOff2021Interface {
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

		public void init(int k) {
			this.K = k;
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
						R.addIfMeetK(this.layer, this.size);
					} else { // bad
						R.minusIfMeetK(this.layer, this.size);
						this.valid = false;
					}
				} else {
					// split
					this.isNode = false;

					if (a > this.from && b < this.to) {
						// split 3 (split 2 only)
						dividePoint = a;
						left = new Node(this.from, a - 1, this.layer);
						right = new Node(a, this.to, this.layer);
						RN.set(right);
					} else if (a <= this.from && b < this.to) {
						dividePoint = b + 1;
						left = new Node(this.from, b, this.layer);
						right = new Node(b + 1, this.to, this.layer);
						RN.set(right);
					} else if (a > this.from && b >= this.to) {
						dividePoint = a;
						left = new Node(this.from, a - 1, this.layer);
						right = new Node(a, this.to, this.layer);
						RN.set(right);
					}
				}

			}
			if (!this.isNode) {
				if (left != null && a < dividePoint) {
					left.process(a, b, c);
					if (!left.isNode & left.valid && (left.left == null || left.right == null)) {
						left = left.right != null ? left.right : left.left;
					}
					if (!left.valid) {
						left = null;
					}

				}
				if (right != null && b >= dividePoint) {
					right.process(a, b, c);
					if (!right.isNode & right.valid && (right.left == null || right.right == null)) {
						right = right.right != null ? right.right : right.left;
					}

					if (!right.valid) {
						right = null;
					}
				}

				if (left == null && right == null) {
					RN.removeIfSame(this);
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
					RN.removeIfSame(this.right);
					this.right = null;
					this.dividePoint = 0;
				}
			}

		}

	}

	class RightNode {
		Node[] AllRightNode;

		void init(int N) {
			AllRightNode = new Node[N + 1];
		}

		void set(Node n) {
			AllRightNode[n.from] = n;
		}

		void remove(Node n) {
			AllRightNode[n.from] = null;
		}

		void removeIfSame(Node n) {
			if (AllRightNode[n.from] == n)
				AllRightNode[n.from] = null;
		}
	}

	RightNode RN = new RightNode();

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		R.init(K);

		RN.init(N);
		Node TOP = new Node(1, N, 0);
		RN.set(TOP);

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];
			TOP.process(a, b, c);
			if (!TOP.valid)
				return 0;
			if (!TOP.isNode) {
				if (TOP.left == null) {
					TOP = TOP.right;
					RN.remove(TOP);
				} else if (!TOP.left.valid) {
					TOP.left = null;
					TOP = TOP.right;
					RN.remove(TOP);
				} else if (TOP.right == null) {
					TOP = TOP.left;
				} else if (!TOP.right.valid) {
					TOP.right = null;
					TOP = TOP.left;
				}
			}

		}
		return R.total_good;
	}
}
