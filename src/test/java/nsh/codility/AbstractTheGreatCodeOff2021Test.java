package nsh.codility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public abstract class AbstractTheGreatCodeOff2021Test {
	abstract TheGreatCodeOff2021Interface getTestObject();

	@Test
	void test01() {
		int N = 5;
		int K = 3;
		int[] A = new int[] { 1, 1, 4, 1, 4 };
		int[] B = new int[] { 5, 2, 5, 5, 4 };
		int[] C = new int[] { 1, 2, 2, 3, 3 };
		int a = 3;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test02() {
		int N = 6;
		int K = 4;
		int[] A = new int[] { 1, 2, 1, 1 };
		int[] B = new int[] { 3, 3, 6, 6 };
		int[] C = new int[] { 1, 2, 3, 4 };
		int a = 2;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test03() {
		int N = 3;
		int K = 2;
		int[] A = new int[] { 1, 3, 3, 1, 1 };
		int[] B = new int[] { 2, 3, 3, 1, 2 };
		int[] C = new int[] { 1, 2, 1, 2, 2 };
		int a = 1;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test04() {
		int N = 5;
		int K = 2;
		int[] A = new int[] { 1, 1, 2 };
		int[] B = new int[] { 5, 5, 3 };
		int[] C = new int[] { 1, 2, 1 };
		int a = 3;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test05() {
		int N = 6;
		int K = 1;
		int[] A = new int[] { 2 };
		int[] B = new int[] { 4 };
		int[] C = new int[] { 1 };
		int a = 3;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test06() {
		int N = 6;
		int K = 2;
		int[] A = new int[] { 2 };
		int[] B = new int[] { 4 };
		int[] C = new int[] { 1 };
		int a = 0;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test07() {
		int N = 6;
		int K = 2;
		int[] A = new int[] { 1, 1, 3, 6, 2, 5 };
		int[] B = new int[] { 6, 1, 4, 6, 2, 5 };
		int[] C = new int[] { 1, 2, 2, 2, 2, 2 };
		int a = 6;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test08() {
		int N = 100;
		int K = 2;
		int[] A = new int[] { 1, 58, 1, 26, 37, 25, 11 };
		int[] B = new int[] { 100, 92, 10, 35, 50, 25, 20 };
		int[] C = new int[] { 1, 2, 2, 2, 2, 2, 2 };
		int a = 80;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test09() {
		int N = 200;
		int K = 2;
		int[] A = new int[] { 10, 58, 163, 26, 37, 25, 111 };
		int[] B = new int[] { 150, 92, 181, 35, 50, 25, 120 };
		int[] C = new int[] { 1, 2, 1, 2, 2, 2, 2 };
		int a = 70;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test10() {
		int N = 10;
		int K = 1;
		int[] A = new int[] { 1, 3, 5, 7, 9, 2, 4, 6, 8, 10 };
		int[] B = new int[] { 1, 3, 5, 7, 9, 2, 4, 6, 8, 10 };
		int[] C = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		int a = 10;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test11() {
		int N = 150;
		int K = 2;
		int[] A = new int[] { 1, 64 };
		int[] B = new int[] { 64, 129 };
		int[] C = new int[] { 1, 2 };
		int a = 1;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test12() {
		int N = 100000;
		int K = 100000;
		int[] A = new int[] { 1, 64 };
		int[] B = new int[] { 64, 129 };
		int[] C = new int[] { 1, 2 };
		int a = 0;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test13() {
		int N = 100000;
		int K = 5;
		int[] A = new int[] { 1, 1, 1, 1, 1 };
		int[] B = new int[] { 100000, 100000, 100000, 100000, 10000 };
		int[] C = new int[] { 1, 2, 3, 4, 5 };
		int a = 10000;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test14() {
		int N = 6;
		int K = 3;
		int[] A = new int[] { 1, 4, 2, 2 };
		int[] B = new int[] { 6, 6, 3, 5 };
		int[] C = new int[] { 1, 2, 2, 3 };
		int a = 4;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test15() {
		int N = 9;
		int K = 2;
		int[] A = new int[] { 1, 3, 1, 5, 7, 9 };
		int[] B = new int[] { 9, 3, 1, 5, 7, 9 };
		int[] C = new int[] { 1, 2, 2, 2, 2, 2 };
		int a = 5;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}

	@Test
	void test16() {
		int N = 5;
		int K = 3;
		int[] A = new int[] { 1, 1, 4, 1, 4, 4 };
		int[] B = new int[] { 5, 2, 5, 5, 4, 4 };
		int[] C = new int[] { 1, 2, 2, 3, 3, 3 };
		int a = 3;

		assertEquals(a, getTestObject().solution(N, K, A, B, C));
	}
}
