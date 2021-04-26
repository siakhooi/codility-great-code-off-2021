package nsh.codility;

public class TheGreatCodeOff2021M1 implements TheGreatCodeOff2021Interface {
	interface NodeInterface {
		void init(int from, int to, int layer);

		void process(int from, int to, int a, int b, int c);

		int getCount(int from, int to);
	}

	class NodeA implements NodeInterface {
		int layer = 0;
		boolean isNode = true;
		NodeInterface left, right;

		NodeA(int from, int to) {
			if (to - from < LAST_NODE_MAX_SIZE) {
				left = new NodeC();
				right = new NodeC();
			} else {
				int dividePoint = (from + to) >> 1;
				left = new NodeA(from, dividePoint);
				right = new NodeA(dividePoint + 1, to);
			}
		}

		public void init(int from, int to, int layer) {
			this.layer = layer;
		}

		public void process(int from, int to, int a, int b, int c) {
			if (layer == INVALID)
				return;
			if (this.isNode) {
				if (from >= a && to <= b) {
					if (this.layer + 1 == c) { // good
						this.layer++;
					} else { // bad
						this.layer = INVALID;
					}
					return;
				} else {
					// split
					this.isNode = false;
					int dividePoint = (from + to) >> 1;
					left.init(from, dividePoint, this.layer);
					right.init(dividePoint + 1, to, this.layer);
					if (a <= dividePoint)
						left.process(from, dividePoint, a, b, c);
					if (b > dividePoint)
						right.process(dividePoint + 1, to, a, b, c);
					return;
				}
			}
			int dividePoint = (from + to) >> 1;
			if (a <= dividePoint)
				left.process(from, dividePoint, a, b, c);
			if (b > dividePoint)
				right.process(dividePoint + 1, to, a, b, c);
		}

		public int getCount(int from, int to) {
			if (layer == INVALID)
				return 0;
			if (isNode && layer == K)
				return to - from + 1;
			if (!isNode) {
				int dividePoint = (from + to) >> 1;
				return left.getCount(from, dividePoint) + right.getCount(dividePoint + 1, to);
			}
			return 0;
		}
	}

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

	class NodeC implements NodeInterface {
		long[] layers;
		long status;
		int base;

		public void init(int from, int to, int layer) {
			int i = K - layer + 1;
			layers = new long[i];
			i = to - from;
			status = LONG_0_TO_N[i];
			layers[0] = status;
			base = layer;
		}

		public void process(int from, int to, int a, int b, int c) {
			if (status == 0)
				return;

			int f = Math.max(from, a) - from;
			int t = Math.min(to, b) - from;
			long l = LONG_N_TO_63[f] & LONG_0_TO_N[t];
			if (c < base) {
				status &= (~l);// any instruction is not valid
				return;
			}
			int idx1 = c - base;
			long sl = l & status;
			long l1 = layers[idx1];
			long l0 = (c == base) ? status : layers[idx1 - 1];

			long xl = l0 & sl & (~l1);

			layers[idx1] = ((xl) | (l1 & (~l))) & status;
			status = (((sl) & (xl)) | (status & (~(sl))));
			if (status == 0l)
				layers = null;
		}

		public int getCount(int from, int to) {
			if (status == 0)
				return 0;
			int r = 0;
			long l = status & layers[K - base];
			if (l == 0)
				return 0;

			for (int j = 0; j < 64; j++) {
				long r1 = l & 1l;
				r += r1;
				l >>>= 1;
			}

			return r;
		}
	}

	int K;
	final int LAST_NODE_MAX_SIZE = 128;
	final int INVALID = -99;

	public int solution(int N, int K, int[] A, int[] B, int[] C) {
		this.K = K;
		NodeInterface TOP = new NodeA(1, N);

		int m = A.length;

		for (int i = 0; i < m; i++) {
			int a = A[i], b = B[i], c = C[i];
			TOP.process(1, N, a, b, c);
		}
		return TOP.getCount(1, N);
	}
}