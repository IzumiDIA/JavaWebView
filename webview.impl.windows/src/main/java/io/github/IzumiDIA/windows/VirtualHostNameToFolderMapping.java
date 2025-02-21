package io.github.IzumiDIA.windows;

import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;

public record VirtualHostNameToFolderMapping(@NotNull MemorySegment virtualHostName, MemorySegment folderMapping, int accessKind) {
}
