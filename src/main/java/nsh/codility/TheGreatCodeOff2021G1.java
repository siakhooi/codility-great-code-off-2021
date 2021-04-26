package nsh.codility;

public class TheGreatCodeOff2021G1 implements TheGreatCodeOff2021Interface {
	long rangeToLongBits(int from, int to) {
		int i = 64 - (to - from + 1);
		return (-1l >>> i) << from;
	}

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		final int N_SIZE = (K > 20000) ? 8192 : N;
		// final int N_SIZE = 33344;

		int N_LOOP = N / N_SIZE;
		if (N % N_SIZE > 0)
			N_LOOP++;
		int R = 0;

		int ll = N_SIZE / 64;
		if (N_SIZE % 64 > 0)
			ll++;
		long[][] L = new long[K + 1][ll];
		long[] Status = new long[ll];

		for (int x = 0; x < N_LOOP; x++) {
			int NBASE = x * N_SIZE;
			int CURSIZE = (x == N_LOOP - 1 && N % N_SIZE > 0) ? N % N_SIZE : N_SIZE;

			int lcount = CURSIZE / 64;
			int remain = CURSIZE % 64;
			if (remain > 0)
				lcount++;
			int N1 = NBASE + 1;
			int N2 = NBASE + CURSIZE;

			for (int i = 0; i < lcount; i++) {
				L[0][i] = -1l;
				if (i == lcount - 1 && remain > 0) {
					L[0][i] = rangeToLongBits(0, remain - 1);
				}
				Status[i] = L[0][i];
			}
			for (int i = 1; i <= K; i++) {
				for (int j = 0; j < lcount; j++) {
					L[i][j] = 0l;
				}
			}

			for (int i = 0; i < A.length; i++) {
				int a = A[i], b = B[i], c = C[i];
				if (b < N1 || a > N2)
					continue;
				if (a < N1)
					a = N1;
				if (b > N2)
					b = N2;
				a -= NBASE;
				b -= NBASE;

				int index1 = (a - 1) / 64;
				int index2 = b / 64;
				if (b % 64 == 0)
					index2--;
				for (int j = index1; j <= index2; j++) {
					int start = (j == index1) ? ((a - 1) % 64) : 0;
					int end = (j == index2 && b % 64 > 0) ? (b % 64) - 1 : 63;
					long l = rangeToLongBits(start, end);
					long l0 = L[c - 1][j];
					long l1 = L[c][j];
					long s = Status[j];

					L[c][j] = ((l0 & l & s & (~l1)) | (l1 & (~l))) & s;
					Status[j] = (((l & s) & (l0 & l & s & (~l1))) | (s & (~(l & s))));
				}
			}

			for (int i = 0; i < lcount; i++) {
				long l1 = Status[i];
				long l2 = L[K][i];
				long l = l1 & l2;
				for (int j = 0; j < 64 && l != 0; j++) {
					long r1 = l & 1l;
					R += r1;
					l >>>= 1;
				}
			}

		}
		return R;
	}

}
