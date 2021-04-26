package nsh.codility;

import java.util.ArrayList;
import java.util.Comparator;

public class TheGreatCodeOff2021F2 implements TheGreatCodeOff2021Interface {
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
					} else if (a <= this.from && b < this.to) {
						dividePoint = b + 1;
						left = new Node(this.from, b, this.layer);
						right = new Node(b + 1, this.to, this.layer);
					} else if (a > this.from && b >= this.to) {
						dividePoint = a;
						left = new Node(this.from, a - 1, this.layer);
						right = new Node(a, this.to, this.layer);
					}
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
					this.dividePoint = 0;
				}
			}

		}

	}

	Node[] G;
	final int N_PER_GROUP = 1250;

	class Query {
		int a, b, c;

		Query(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}

	class AllQuery {
		ArrayList<Query> allQuery = new ArrayList<Query>();

		void buildQuery(int[] A, int B[], int C[]) {

			ArrayList<Query> q = new ArrayList<Query>();

			int lastc = -1;

			for (int i = 0; i < A.length; i++) {
				int a = A[i], b = B[i], c = C[i];
				if (c == lastc) {
					q.add(new Query(a, b, c));
				} else {
					if (lastc != -1) {
						AddQueryArray(q);
						q = new ArrayList<Query>();
					}
					lastc = c;
					q.add(new Query(a, b, c));
				}
			}
			if (lastc != -1) {
				AddQueryArray(q);
			}
		}

		void AddQueryArray(ArrayList<Query> q) {
			q.sort(new Comparator<Query>() {
				public int compare(Query o1, Query o2) {
					return o1.a - o2.a;
				}
			});
			Query q1 = q.get(0);
			for (int i = 1; i < q.size(); i++) {
				if (q1.b + 1 == q.get(i).a) {
					q1.b = q.get(i).b;
				} else {
					allQuery.add(q1);
					q1 = q.get(i);
				}
			}
			allQuery.add(q1);
		}
	}

	AllQuery Q = new AllQuery();

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		R.K = K;

		Q.buildQuery(A, B, C);

		int i = N / N_PER_GROUP;
		if (N % N_PER_GROUP > 0)
			i++;
		G = new Node[i];

		for (i = 0; i < G.length; i++) {
			int s = i * N_PER_GROUP + 1;
			int e = i * N_PER_GROUP;
			if (i == G.length - 1 && N % N_PER_GROUP > 0) {
				e += N % N_PER_GROUP;
			} else {
				e += N_PER_GROUP;
			}
			G[i] = new Node(s, e, 0);
		}
		for (Query q : Q.allQuery) {
			int a = q.a, b = q.b, c = q.c;

			int g1 = (a - 1) / N_PER_GROUP;
			int g2 = b / N_PER_GROUP;
			if (b % N_PER_GROUP == 0)
				g2--;

			for (int g = g1; g <= g2; g++) {
				Node G1 = G[g];
				if (G1 == null)
					continue;
				int f = (g == g1 ? a : G1.from);
				int t = (g == g2 ? b : G1.to);
				G1.process(f, t, c);
				if (!G1.valid)
					G[g] = null;
				else {
					if (!G1.isNode && G1.left == null) {
						G[g] = G[g].right;
					}
					if (!G1.isNode && G1.right == null) {
						G[g] = G[g].left;
					}
				}

			}

		}
		return R.total_good;
	}
}
