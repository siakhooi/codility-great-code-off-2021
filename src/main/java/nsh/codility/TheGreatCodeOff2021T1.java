package nsh.codility;

import java.util.ArrayList;

public class TheGreatCodeOff2021T1 implements TheGreatCodeOff2021Interface {
	// T1
	class Node {
		int initCount = 0;
		boolean isValid = true;
		int m0 = 0, m1 = 0;
		Node left, right;

		Node(int from, int to) {
			if (from != to) {
				int dividePoint = (from + to) >> 1;
				left = new Node(from, dividePoint);
				right = new Node(dividePoint + 1, to);
			}
		}

		public void init(int from, int to, int m, int c) {
			if (from == to) {
				initCount++;
				isValid = initCount < 2;// either 0 or 1
				m0 = c;
				m1 = c;
			} else {
				int dividePoint = (from + to) >> 1;
				if (m <= dividePoint)
					left.init(from, dividePoint, m, c);
				else
					right.init(dividePoint + 1, to, m, c);

				isValid = left.isValid && right.isValid && (left.m0 == 0 || right.m1 == 0 || left.m1 + 1 == right.m0);
				m0 = left.m0 == 0 ? right.m0 : right.m0 == 0 ? left.m0 : Math.min(left.m0, right.m0);
				m1 = Math.max(right.m1, left.m1);
			}
		}

		public void uninit(int from, int to, int m, int c) {
			if (from == to) {
				initCount--;
				isValid = initCount < 2;// either 0 or 1
				if (initCount == 0) {
					m0 = 0;
					m1 = 0;
				}
			} else {
				int dividePoint = (from + to) >> 1;
				if (m <= dividePoint)
					left.uninit(from, dividePoint, m, c);
				else
					right.uninit(dividePoint + 1, to, m, c);

				isValid = left.isValid && right.isValid && (left.m0 == 0 || right.m1 == 0 || left.m1 + 1 == right.m0);
				m0 = left.m0 == 0 ? right.m0 : right.m0 == 0 ? left.m0 : Math.min(left.m0, right.m0);
				m1 = Math.max(right.m1, left.m1);
			}
		}
	}

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		int M = A.length;
		Node TOP = new Node(0, M);
		ArrayList<ArrayList<Integer>> start = new ArrayList<ArrayList<Integer>>(N + 1);
		ArrayList<ArrayList<Integer>> end = new ArrayList<ArrayList<Integer>>(N + 1);
		for (int i = 0; i < N + 1; i++) {
			start.add(new ArrayList<Integer>());
			end.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < M; i++) {
			start.get(A[i]).add(i);
			end.get(B[i]).add(i);
		}
		int r = 0;

		for (int i = 1; i <= N; i++) {
			for (int m : start.get(i))
				TOP.init(0, M, m, C[m]);

			if (TOP.isValid && TOP.m0 == 1 && TOP.m1 == K)
				r++;

			for (int m : end.get(i))
				TOP.uninit(0, M, m, C[m]);
		}
		return r;
	}
}