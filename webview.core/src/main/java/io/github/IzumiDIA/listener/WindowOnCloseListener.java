package io.github.IzumiDIA.listener;

import java.util.EventListener;

@FunctionalInterface
public interface WindowOnCloseListener extends EventListener {
	
	void onClose();
}
