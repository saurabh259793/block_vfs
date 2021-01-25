package fileSystem;

import block_device.MemoryNode;

public interface FileSystem {

    MemoryNode fopen(String fileName);

    void fwrite(MemoryNode memoryNode, String contents);

    void fclose(MemoryNode memoryNode);

    String fread(MemoryNode memoryNode);
}
