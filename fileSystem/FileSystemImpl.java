package fileSystem;

import block_device.BlockDeviceImpl;
import block_device.MemoryNode;

import java.util.Hashtable;

public class FileSystemImpl {

    static Hashtable<String, MemoryNode> fileMap = new Hashtable<>();

    static BlockDeviceImpl blockDevice = BlockDeviceImpl.INSTANCE;


    public static MemoryNode fopen(String fileName) {


        if(fileName == null || fileName.length() == 0)
            return null;

        if(fileMap.containsKey(fileName))
            return fileMap.get(fileName);

        MemoryNode fileNode = blockDevice.getNextFreePointer();
        fileMap.put(fileName, fileNode);

        return fileNode;
    }

    public static void fwrite(MemoryNode memoryNode, String contents) {

        if(memoryNode == null || contents.length() == 0)
            return;

        try {
            blockDevice.storeData(memoryNode, contents);
        } catch (Exception exception) {
            System.out.println(exception.getCause());
        }
    }

    public static void fclose(MemoryNode memoryNode) {
        memoryNode = null;
    }

    public static String fread(String fileName) {

        if(!fileMap.containsKey(fileName))
            throw new RuntimeException("file does not exists");

        MemoryNode memoryNode = fileMap.get(fileName);

        if(memoryNode == null)
            return null;
        String result;
        try {
             result = blockDevice.getData(memoryNode);
        } catch (Exception exception) {
            System.out.println(exception.getCause());
            throw exception;
        }
        return result;
    }


    public static void main(String[] args) {
        MemoryNode memoryNode = fopen("test");
        testFwrite();
    }


    public static void testFwrite() {
        String actual = "test_string";

        MemoryNode memoryNode = fopen("tesst");
        fwrite(memoryNode, actual);

        String expected = fread("test");
        if(!actual.equals(expected))
            throw new RuntimeException("Test case failed");
    }
}
