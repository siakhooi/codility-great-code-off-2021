package nsh.codility;

public class TheGreatCodeOff2021G4 implements TheGreatCodeOff2021Interface {
	final long LONG_N_TO_63[] = new long[] { -1l, -2l, -4l, -8l, -16l, -32l, -64l, -128l, -256l, -512l, -1024l, -2048l,
			-4096l, -8192l, -16384l, -32768l, -65536l, -131072l, -262144l, -524288l, -1048576l, -2097152l, -4194304l,
			-8388608l, -16777216l, -33554432l, -67108864l, -134217728l, -268435456l, -536870912l, -1073741824l,
			-2147483648l, -4294967296l, -8589934592l, -17179869184l, -34359738368l, -68719476736l, -137438953472l,
			-274877906944l, -549755813888l, -1099511627776l, -2199023255552l, -4398046511104l, -8796093022208l,
			-17592186044416l, -35184372088832l, -70368744177664l, -140737488355328l, -281474976710656l,
			-562949953421312l, -1125899906842624l, -2251799813685248l, -4503599627370496l, -9007199254740992l,
			-18014398509481984l, -36028797018963968l, -72057594037927936l, -144115188075855872l, -288230376151711744l,
			-576460752303423488l, -1152921504606846976l, -2305843009213693952l, -4611686018427387904l,
			-9223372036854775808l };
	final long LONG_0_TO_N[] = new long[] { 1l, 3l, 7l, 15l, 31l, 63l, 127l, 255l, 511l, 1023l, 2047l, 4095l, 8191l,
			16383l, 32767l, 65535l, 131071l, 262143l, 524287l, 1048575l, 2097151l, 4194303l, 8388607l, 16777215l,
			33554431l, 67108863l, 134217727l, 268435455l, 536870911l, 1073741823l, 2147483647l, 4294967295l,
			8589934591l, 17179869183l, 34359738367l, 68719476735l, 137438953471l, 274877906943l, 549755813887l,
			1099511627775l, 2199023255551l, 4398046511103l, 8796093022207l, 17592186044415l, 35184372088831l,
			70368744177663l, 140737488355327l, 281474976710655l, 562949953421311l, 1125899906842623l, 2251799813685247l,
			4503599627370495l, 9007199254740991l, 18014398509481983l, 36028797018963967l, 72057594037927935l,
			144115188075855871l, 288230376151711743l, 576460752303423487l, 1152921504606846975l, 2305843009213693951l,
			4611686018427387903l, 9223372036854775807l, -1l };

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		int K_SIZE = N > 50000 ? Math.min(10000, K + 1) : K + 1;
		int R = 0;

		int lcount = N / 64;
		int remain = N % 64;
		if (remain > 0)
			lcount++;

		long[][] L = new long[lcount][K_SIZE];
		long[] status = new long[lcount];
		int[] start_index = new int[lcount];
		int[] last_index = new int[lcount];
		int[] start_level = new int[lcount];
		int[] last_level = new int[lcount];

		for (int i = 0; i < lcount; i++) {
			if (i == lcount - 1 && remain > 0) {
				L[i][0] = LONG_0_TO_N[remain - 1];
				status[i] = LONG_0_TO_N[remain - 1];
			} else {
				status[i] = -1l;
				L[i][0] = -1l;
			}
		}

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];

			int index1 = (a - 1) / 64;
			int index2 = b / 64;
			if (b % 64 == 0)
				index2--;

			for (int j = index1; j <= index2; j++) {
				long[] Lj = L[j];
				long l = -1l;
				if (j == index1) {
					l &= LONG_N_TO_63[(a - 1) % 64];
				}
				if (j == index2 && b % 64 > 0) {
					l &= LONG_0_TO_N[(b % 64) - 1];
				}

				long s = status[j];
				int idx1 = start_index[j];
				int idx2 = last_index[j];
				int lvl1 = start_level[j];
				int lvl2 = last_level[j];

				if (s == 0)
					continue;

				if (c - lvl1 >= K_SIZE) {
					return lvl1 - c; // over flow
				}

				long sl = l & s;
				if (c < lvl1) {
					status[j] = s & (~l); // any instruction is not valid

				} else if (sl != 0) {
					if (c > lvl2) {
						idx2 += (c - lvl2);
						if (idx2 >= K_SIZE)
							idx2 -= K_SIZE;
						lvl2 = c;
						last_level[j] = c;
						last_index[j] = idx2;
					}

					int idx_c1 = c - lvl1 + idx1;
					if (idx_c1 >= K_SIZE)
						idx_c1 -= K_SIZE;
					int idx_c0 = idx_c1 - 1;
					if (idx_c0 < 0)
						idx_c0 = K_SIZE - 1;

					long l0 = Lj[idx_c0];
					long l1 = Lj[idx_c1];

					if (c == lvl1) {
						l0 = s;
					}

					long xl = l0 & sl & (~l1);

					Lj[idx_c1] = ((xl) | (l1 & (~l))) & s;
					s = (((sl) & (xl)) | (s & (~(sl))));
					status[j] = s;
				}

				while (start_level[j] < lvl2 && s == Lj[start_index[j]]) {
					Lj[start_index[j]] = 0l;
					start_level[j]++;
					start_index[j]++;
					if (start_index[j] == K_SIZE)
						start_index[j] = 0;

				}

			}
		}
		for (int i = 0; i < lcount; i++) {
			int lvl2 = last_level[i];
			if (lvl2 != K)
				continue;
			long l1 = status[i];
			if (l1 == 0)
				continue;
			long l2 = L[i][last_index[i]];
			long l = l1 & l2;
			for (int j = 0; j < 64 && l != 0; j++) {
				long r1 = l & 1l;
				R += r1;
				l >>>= 1;
			}
		}
		return R;
	}
}
