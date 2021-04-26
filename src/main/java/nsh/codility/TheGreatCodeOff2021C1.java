package nsh.codility;

public class TheGreatCodeOff2021C1 implements TheGreatCodeOff2021Interface {
	class Segment {
		int from, to, layer, status;

		Segment(int from, int to, int layer, int status) {
			this.from = from;
			this.to = to;
			this.layer = layer;
			this.status = status;
		}
	}

	class Group {
		Segment[] segments = new Segment[SEG_PER_GROUP];
		int size = -1;
		int start, end;

		void add(int from, int to, int layer, int status) {
			segments[++size] = new Segment(from, to, layer, status);
		}

		void remove(int i) {
			segments[i] = null;
			if (i < size) {
				segments[i] = segments[size];
				segments[size] = null;
			}
			size--;
		}

		void process(int a, int b, int c) {

			for (int i = 0; i <= size; i++) {
				Segment s = segments[i];
				if (s == null)
					continue;
				else if (s.from > b)
					continue;
				else if (s.to < a)
					continue;

				else if (s.from >= a && s.to <= b) {
					// whole segment
					if (s.layer + 1 == c) { // good
						r.addGoodIf(c == K, s.from, s.to);
						s.layer = c;
					} else { // bad
						r.minusGoodIf(s.layer == K, s.from, s.to);
						remove(i--);
					}
				} else if (s.from < a && s.to > b) { // split3
					add(s.from, a - 1, s.layer, s.status);
					add(b + 1, s.to, s.layer, s.status);
					s.from = a;
					s.to = b;

					if (s.layer + 1 == c) { // good
						r.addGoodIf(c == K, s.from, s.to);
						s.layer = c;
					} else { // bad
						r.minusGoodIf(s.layer == K, s.from, s.to);
						remove(i--);
					}

				} else if (s.from >= a && s.to > b) {
					// split right
					add(b + 1, s.to, s.layer, s.status);
					s.to = b;

					if (s.layer + 1 == c) { // good
						r.addGoodIf(c == K, s.from, s.to);
						s.layer = c;
					} else { // bad
						r.minusGoodIf(s.layer == K, s.from, s.to);
						remove(i--);
					}
				} else if (s.from < a && s.to <= b) {
					// split left
					add(s.from, a - 1, s.layer, s.status);
					s.from = a;

					if (s.layer + 1 == c) { // good
						r.addGoodIf(c == K, s.from, s.to);
						s.layer = c;
					} else { // bad
						r.minusGoodIf(s.layer == K, s.from, s.to);
						remove(i--);
					}
				}

			}

		}

	}

	Group[] G;
	final int SEG_PER_GROUP = 200;

	class Result {
		int total_good = 0;

		void addGoodIf(boolean doIf, int from, int to) {
			if (doIf)
				total_good += (to - from + 1);
		}

		void minusGoodIf(boolean doIf, int from, int to) {
			if (doIf)
				total_good -= (to - from + 1);
		}
	}

	Result r = new Result();
	int K;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;
		// init group
		int i = N / SEG_PER_GROUP;
		if (N % SEG_PER_GROUP > 0)
			i++;
		G = new Group[i];

		for (i = 0; i < G.length; i++) {
			G[i] = new Group();
			G[i].start = i * SEG_PER_GROUP + 1;
			G[i].end = i * SEG_PER_GROUP;
			if (i == G.length - 1 && N % SEG_PER_GROUP > 0) {
				G[i].end += N % SEG_PER_GROUP;

			} else {
				G[i].end += SEG_PER_GROUP;
			}
			G[i].add(G[i].start, G[i].end, 0, 0);
		}

		for (i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];

			int g1 = (a - 1) / SEG_PER_GROUP;
			int g2 = b / SEG_PER_GROUP;
			if (b % SEG_PER_GROUP == 0)
				g2--;

			for (int g = g1; g <= g2; g++) {
				Group G1 = G[g];
				int f = (g == g1 ? a : G1.start);
				int t = (g == g2 ? b : G1.end);
				G1.process(f, t, c);

			}

		}
		return r.total_good;
	}

}
