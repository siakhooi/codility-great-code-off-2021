package nsh.codility;

import java.util.ArrayList;
import java.util.Comparator;

public class TheGreatCodeOff2021C3 implements TheGreatCodeOff2021Interface {
	class Segment {
		int from, to, layer;

		Segment(int from, int to, int layer) {
			this.from = from;
			this.to = to;
			this.layer = layer;
		}
	}

	class Group {
		Segment[] segments = new Segment[SEG_PER_GROUP];
		int size = -1;
		int start, end;

		void add(int from, int to, int layer) {
			segments[++size] = new Segment(from, to, layer);
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
			int combineFromLeft = -1, combineFromRight = -1;
			int currentSegment = -1;

			for (int i = 0; i <= size; i++) {
				Segment s = segments[i];
				if (s == null)
					continue;
				else if (b + 1 == s.from && s.layer == c)
					combineFromRight = i;
				else if (a - 1 == s.to && s.layer == c)
					combineFromLeft = i;
				else if (s.from > b)
					continue;
				else if (s.to < a)
					continue;

				else if (s.from >= a && s.to <= b) {
					// whole segment
					if (s.layer + 1 == c) { // good
						r.addGoodIf(c == K, s.from, s.to);
						s.layer = c;
						currentSegment = i;
					} else { // bad
						r.minusGoodIf(s.layer == K, s.from, s.to);
						remove(i--);
					}
				} else if (s.from < a && s.to > b) { // split3
					add(s.from, a - 1, s.layer);
					add(b + 1, s.to, s.layer);
					s.from = a;
					s.to = b;

					if (s.layer + 1 == c) { // good
						r.addGoodIf(c == K, s.from, s.to);
						s.layer = c;
						currentSegment = i;
					} else { // bad
						r.minusGoodIf(s.layer == K, s.from, s.to);
						remove(i--);
					}

				} else if (s.from >= a && s.to > b) {
					// split right
					add(b + 1, s.to, s.layer);
					s.to = b;

					if (s.layer + 1 == c) { // good
						r.addGoodIf(c == K, s.from, s.to);
						s.layer = c;
						currentSegment = i;
					} else { // bad
						r.minusGoodIf(s.layer == K, s.from, s.to);
						remove(i--);
					}
				} else if (s.from < a && s.to <= b) {
					// split left
					add(s.from, a - 1, s.layer);
					s.from = a;

					if (s.layer + 1 == c) { // good
						r.addGoodIf(c == K, s.from, s.to);
						s.layer = c;
						currentSegment = i;
					} else { // bad
						r.minusGoodIf(s.layer == K, s.from, s.to);
						remove(i--);
					}
				}
			}
			boolean removeLeft = false, removeRight = false;
			if (currentSegment != -1 && combineFromLeft != -1) {
				if (segments[combineFromLeft].to + 1 == segments[currentSegment].from) {
					segments[currentSegment].from = segments[combineFromLeft].from;
					removeLeft = true;

				}
			}
			if (currentSegment != -1 && combineFromRight != -1) {
				if (segments[currentSegment].to + 1 == segments[combineFromRight].from) {
					segments[currentSegment].to = segments[combineFromRight].to;
					removeRight = true;
				}
			}
			if (combineFromLeft > combineFromRight) {
				if (removeLeft)
					remove(combineFromLeft);
				if (removeRight)
					remove(combineFromRight);
			} else {
				if (removeRight)
					remove(combineFromRight);
				if (removeLeft)
					remove(combineFromLeft);
			}

		}

	}

	Group[] G;
	final int SEG_PER_GROUP = 1250;

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
		this.K = K;

		Q.buildQuery(A, B, C);

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
			G[i].add(G[i].start, G[i].end, 0);
		}

		for (Query q : Q.allQuery) {
			int a = q.a, b = q.b, c = q.c;

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
