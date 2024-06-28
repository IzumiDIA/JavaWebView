package io.github.IzumiDIA.windows.impl;

import io.github.IzumiDIA.Result;

@SuppressWarnings("java:S6548")
public sealed interface HResult extends Result {
	
	int value();
	
	enum S_OK implements HResult, Result.OK {
		SINGLETON;
		@Override
		public int value() {
			return 0x00000000;
		}
	}
	enum E_ABORT implements HResult, Result.ABORT {
		SINGLETON;
		@Override
		public int value() {
			return 0x80004004;
		}
	}
	enum E_FAIL implements HResult, Result.FAIL {
		SINGLETON;
		@Override
		public int value() {
			return 0x80004005;
		}
	}
	enum E_UNEXPECTED implements HResult, Result.UNEXPECTED {
		SINGLETON;
		@Override
		public int value() {
			return 0x8000FFFF;
		}
	}
	
	final class E_HANDLE extends IllegalAccessException implements HResult {
		@Override
		public boolean isOK() {
			return false;
		}
		
		@Override
		public int value() {
			return 0x80070006;
		}
	}
	
	@SuppressWarnings("SpellCheckingInspection")
	final class E_INVALIDARG extends IllegalArgumentException implements HResult {
		@Override
		public boolean isOK() {
			return false;
		}
		
		@Override
		public int value() {
			return 0x80070057;
		}
	}
	
	final class OtherResult extends RuntimeException implements HResult {
		private final int hResultValue;
		
		public OtherResult(final int hResultValue) {
			super();
			this.hResultValue = hResultValue;
		}
		
		@Override
		public boolean isOK() {
			return false;
		}
		
		@Override
		public int value() {
			return this.hResultValue;
		}
	}
	
	static HResult warpResult(final int hResult) {
		return switch (hResult) {
			case 0x00000000 -> HResult.S_OK.SINGLETON;
			case 0x80004004 -> HResult.E_ABORT.SINGLETON;
			case 0x80004005 -> HResult.E_FAIL.SINGLETON;
			case 0x8000FFFF -> HResult.E_UNEXPECTED.SINGLETON;
			case 0x80070006 -> new HResult.E_INVALIDARG();
			case 0x80070057 -> new HResult.E_HANDLE();
			default -> new HResult.OtherResult(hResult);
		};
	}
}
