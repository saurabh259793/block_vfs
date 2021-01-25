package block_device;

public interface BlockDevice {

    String getData(MemoryNode memoryNode);

    void storeData(MemoryNode memoryNode, String contents);

    void removeData(MemoryNode memoryNode);
}
