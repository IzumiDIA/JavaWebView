package io.github.IzumiDIA.listener;

import java.util.EventListener;

@FunctionalInterface
public interface WindowOnDestroyListener extends EventListener {
	void onDestroy();
}
