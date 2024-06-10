package io.github.IzumiDIA.listener;

import java.util.EventListener;

@FunctionalInterface
public interface WindowOnCloseListener extends EventListener, Runnable {
	
	void onClose();
	
	@Override
	default void run() {
		this.onClose();
	}
}
