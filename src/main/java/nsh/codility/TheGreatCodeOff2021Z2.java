package nsh.codility;

public class TheGreatCodeOff2021Z2 implements TheGreatCodeOff2021Interface {
	public void combine(int v, int c1, int s1, int c2, int s2) {
		if (s1 == -1 || s2 == -1) {
			tree[v][0] = 0;
			tree[v][1] = -1;
		} else if (s1 == 0) {
			tree[v][0] = c2;
			tree[v][1] = s2;
		} else if (s2 == 0) {
			tree[v][0] = c1;
			tree[v][1] = s1;
		} else if (c1 + s1 == c2) {
			tree[v][0] = c1;
			tree[v][1] = s1 + s2;
		} else {
			tree[v][0] = 0;
			tree[v][1] = -1;
		}
	}

	public void tree_add(int v, int xl, int xr, int a, int b, int c) {
		if (b < xl || xr < a)
			return;
		else if (a <= xl && xr <= b)
			combine(v, tree[v][0], tree[v][1], c, 1);
		else {
			combine(2 * v, tree[2 * v][0], tree[2 * v][1], tree[v][0], tree[v][1]);
			combine(2 * v + 1, tree[2 * v + 1][0], tree[2 * v + 1][1], tree[v][0], tree[v][1]);
			tree[v][0] = 0;
			tree[v][1] = 0;
			int xm = (xl + xr) / 2;
			tree_add(2 * v, xl, xm, a, b, c);
			tree_add(2 * v + 1, xm + 1, xr, a, b, c);
		}

	}

	public int tree_count(int v) {
		if (v >= base) {
			int c = tree[v][0];
			int s = tree[v][1];
			return (c == 1 && s == K) ? 1 : 0;
		} else {
			combine(2 * v, tree[2 * v][0], tree[2 * v][1], tree[v][0], tree[v][1]);
			combine(2 * v + 1, tree[2 * v + 1][0], tree[2 * v + 1][1], tree[v][0], tree[v][1]);
			return tree_count(2 * v) + tree_count(2 * v + 1);
		}
	}

	int[][] tree;
	int base = 1;
	int K;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;
		while (base < N)
			base *= 2;
		tree = new int[base * 2][2];

		for (int i = 0; i < A.length; i++)
			tree_add(1, 0, base - 1, A[i] - 1, B[i] - 1, C[i]);

		return tree_count(1);
	}
}
