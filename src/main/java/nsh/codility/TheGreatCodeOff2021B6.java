package nsh.codility;

public class TheGreatCodeOff2021B6 implements TheGreatCodeOff2021Interface {
	class Segment {
		int from, to, layer;
		boolean status;

		Segment(int from, int to, int layer, boolean status) {
			this.from = from;
			this.to = to;
			this.layer = layer;
			this.status = status;
		}

		void process(int c) {
			this.layer++;
			this.status = this.status && (c == this.layer);
		}
	}

	class Group {
		Segment[] segments;
		int size = -1;

		Group(int n) {
			segments = new Segment[n];
		}

		void add(int from, int to, int layer, boolean status) {
			segments[++size] = new Segment(from, to, layer, status);
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
					s.layer++;
					s.status = s.status && (c == s.layer);
				} else if (s.from < a && s.to > b) { // split3
					add(s.from, a - 1, s.layer, s.status);
					add(b + 1, s.to, s.layer, s.status);

					s.from = a;
					s.to = b;

					s.process(c);

				} else if (s.from >= a && s.to > b) {
					// split right
					add(b + 1, s.to, s.layer, s.status);

					s.to = b;
					s.process(c);
				} else if (s.from < a && s.to <= b) {
					// split left
					add(s.from, a - 1, s.layer, s.status);

					s.from = a;
					s.process(c);
				}

			}
		}
	}

	Group G;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		G = new Group(N);
		G.add(1, N, 0, true);

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];
			G.process(a, b, c);

		}

		int r = 0;
		for (int i = 0; i <= G.size; i++) {
			Segment s = G.segments[i];
			if (s.layer == K && s.status)
				r += (s.to - s.from + 1);
		}
		return r;
	}
}
