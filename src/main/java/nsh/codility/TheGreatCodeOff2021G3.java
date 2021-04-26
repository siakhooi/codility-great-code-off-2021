package nsh.codility;

public class TheGreatCodeOff2021G3 implements TheGreatCodeOff2021Interface {
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

	class LongNode {
		int from, to;
		long status = -1;
		int level_from = 0;
		int level_to = 0;
		Data top;
		Data last;

		LongNode(int from, int to, long s) {
			this.from = from;
			this.to = to;
			this.status = s;
			top = new Data(s);
			last = top;
		}

		public boolean init(int c) {
			if (c > level_to) {
				Data d0 = last;
				while (c > level_to) {
					Data d = new Data(0);
					d.prev = d0;
					d0 = d;
					level_to++;
				}
				last = d0;
				return true;
			} else if (c >= level_from && c <= level_to) {
				return true;
			} else {
				destroy();
				return false;
			}
		}

		private void destroy() {
			status = 0;
			top = null;
			last = null;
			// System.gc();
		}

		public void update(long sl, long l, int c) {
			int n = level_to;
			Data d1 = last;
			while (n > c) {
				n--;
				d1 = d1.prev;
			}
			Data d0 = d1.prev;

			long l0 = d0.data;
			long l1 = d1.data;

			long xl = l0 & sl & (~l1);

			d1.data = ((xl) | (l1 & (~l))) & status;
			this.status = (((sl) & (xl)) | (status & (~(sl))));

			if (this.status == 0)
				destroy();
		}

	}

	class Data {
		Data prev;
		long data;

		Data(long l) {
			this.data = l;
		}

	}

	LongNode[] L;

	class Result {
		int total_good = 0;
	}

	Result R = new Result();

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		int lcount = N / 64;
		int remain = N % 64;
		if (remain > 0)
			lcount++;
		L = new LongNode[lcount];

		for (int i = 0; i < lcount; i++) {
			int from = i * 64 + 1;
			int to = from + 64;
			long s = -1;
			if (i == lcount - 1 && remain > 0)
				s = LONG_0_TO_N[remain];
			L[i] = new LongNode(from, to, s);

		}

		for (int i = 0; i < A.length; i++) {
			int a = A[i], b = B[i], c = C[i];

			int index1 = (a - 1) / 64;
			int index2 = b / 64;
			if (b % 64 == 0)
				index2--;
			for (int j = index1; j <= index2; j++) {
				int start = (j == index1) ? ((a - 1) % 64) : 0;
				int end = (j == index2 && b % 64 > 0) ? (b % 64) - 1 : 63;
				long l = LONG_N_TO_63[start] & LONG_0_TO_N[end];

				long s = L[j].status;
				long sl = l & s;

				if (sl != 0) {
					LongNode ln = L[j];
					if (ln.init(c)) {
						ln.update(sl, l, c);
					}
				}
			}
		}

		for (int i = 0; i < lcount; i++) {
			LongNode ln1 = L[i];
			if (ln1.status == 0)
				continue;
			if (ln1.level_to != K)
				continue;
			long l1 = ln1.last.data;
			if (l1 == 0)
				continue;

			long l = l1 & ln1.status;

			for (int j = 0; j < 64 && l != 0; j++) {
				long r1 = l & 1l;
				R.total_good += r1;
				l >>>= 1;
			}
		}

		return R.total_good;
	}

}
