package io.github.IzumiDIA;

public interface Result {
	boolean isOK();
	
	interface OK extends Result {
		@Override
		default boolean isOK() {
			return true;
		}
	}
	interface ABORT extends Result {
		@Override
		default boolean isOK() {
			return false;
		}
	}
	interface FAIL extends Result {
		@Override
		default boolean isOK() {
			return false;
		}
	}
	interface UNEXPECTED extends Result {
		@Override
		default boolean isOK() {
			return false;
		}
	}
}
