package io.github.IzumiDIA.windows.impl;

import io.github.IzumiDIA.Parameter;

public record WindowsParameter(
		long wParam,
		long lParam
) implements Parameter {
}
