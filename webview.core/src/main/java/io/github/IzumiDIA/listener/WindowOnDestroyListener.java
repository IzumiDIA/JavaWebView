package io.github.IzumiDIA.listener;

import java.util.EventListener;

@FunctionalInterface
public interface WindowOnDestroyListener extends EventListener, Runnable {
	void onDestroy();
	
	@Override
	default void run() {
		this.onDestroy();
	}
}
