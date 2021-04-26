package nsh.codility;

public class TheGreatCodeOff2021D1 implements TheGreatCodeOff2021Interface {

	class Result {
		int total_good = 0;

		void add(int n) {
			total_good += n;
		}

		void minus(int n) {
			total_good -= n;
		}
	}

	Result R = new Result();

	class Data {
		int K;

		boolean isK(int c) {
			return c == this.K;
		}
	}

	Data D = new Data();

	class Group2 {
		int base;
		int size;

		boolean allSame = true;
		int allSameLayer = 0;
		boolean allValid = true;

		Cake[] C = new Cake[TOTAL_CAKE];
		int total_bad = 0;

		Group2(int base, int size) {
			this.base = base;
			this.size = size;
		}

		void initCake() {
			for (int i = 0; i < TOTAL_CAKE; i++) {
				C[i] = new Cake(base + i);
				C[i].layer = this.allSameLayer;
			}
		}

		boolean isFullSize(int a, int b) {
			return a == 0 && b == this.size - 1;
		}

		void process(int a, int b, int c) {
			if (this.allSame) {
				if (!this.allValid)
					return;
				else if (isFullSize(a, b)) {
					if (allSameLayer + 1 == c) {
						allSameLayer = c;
						if (D.isK(c))
							R.add(this.size);
					} else {
						allValid = false;
						if (D.isK(allSameLayer))
							R.minus(this.size);
					}
				} else {
					initCake();
					this.allSame = false;
				}
			}
			if (!this.allSame) {
				for (int j = a; j <= b; j++) {
					Cake c1 = C[j];

					if (!c1.valid)
						continue;
					else if (c1.layer + 1 == c) {
						c1.layer = c;
						if (D.isK(c))
							R.add(1);

					} else {
						c1.valid = false;
						total_bad++;
						if (D.isK(c1.layer))
							R.minus(1);
					}
				}
				if (this.total_bad == this.size) {
					this.allSame = true;
					this.allValid = false;
				}
			}

		}

	}

	class Cake {
		int cake = -1;
		int layer = 0;
		boolean valid = true;

		Cake(int cake) {
			this.cake = cake;
		}
	}

	class Group1 {
		int base;
		int size;

		boolean allSame = true;
		int allSameLayer = 0;
		boolean allValid = true;

		Group2[] G2 = new Group2[TOTAL_G2];
		int total_bad = 0;

		Group1(int base, int size) {
			this.base = base;
			this.size = size;
		}

		void initG2() {
			for (int i = 0; i < G2.length; i++) {
				G2[i] = new Group2(this.base + i * TOTAL_CAKE, TOTAL_CAKE);
				G2[i].allSameLayer = this.allSameLayer;
			}
		}

		boolean isFullSize(int a, int b) {
			return a == 0 && b == this.size - 1;
		}

		void process(int a, int b, int c) {
			if (this.allSame) {
				if (!this.allValid)
					return;
				else if (isFullSize(a, b)) {
					if (allSameLayer + 1 == c) {
						allSameLayer = c;
						if (D.isK(c))
							R.add(this.size);
					} else {
						allValid = false;
						if (D.isK(allSameLayer))
							R.minus(this.size);
					}
				} else {
					initG2();
					this.allSame = false;
				}
			}

			if (!this.allSame) {
				int g2f = a / TOTAL_CAKE;
				int g2t = b / TOTAL_CAKE;

				for (int j = g2f; j <= g2t; j++) {
					Group2 g2 = G2[j];
					if (g2.allSame && !g2.allValid)
						continue;
					int f = 0;
					int t = TOTAL_CAKE - 1;
					if (j == g2f) {
						f = a + this.base - g2.base;
					}
					if (j == g2t) {
						t = b + this.base - g2.base;
					}
					g2.process(f, t, c);
					if (g2.allSame && !g2.allValid)
						this.total_bad += g2.total_bad;
				}
				if (this.total_bad == this.size) {
					this.allSame = true;
					this.allValid = false;
				}
			}
		}

	}

	final int TOTAL_G1 = 2000;
	final int TOTAL_G2 = 10;
	final int TOTAL_CAKE = 5;
	final int G1_SIZE = TOTAL_G2 * TOTAL_CAKE;

	Group1[] G1 = new Group1[TOTAL_G1];

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		D.K = K;
		for (int i = 0; i < TOTAL_G1; i++) {
			G1[i] = new Group1(i * G1_SIZE, G1_SIZE);
		}

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];
			int f1 = (a - 1) / G1_SIZE;
			int f2 = b / G1_SIZE;
			if (b % G1_SIZE == 0)
				f2--;

			a--;
			b--;

			for (int j = f1; j <= f2; j++) {
				Group1 g = G1[j];
				int f = 0;
				int t = G1_SIZE - 1;
				if (j == f1) {
					f = a - g.base;
				}
				if (j == f2) {
					t = b - g.base;
				}

				g.process(f, t, c);
			}
		}

		return R.total_good;
	}
}
