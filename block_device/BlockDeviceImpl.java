package block_device;

public class BlockDeviceImpl implements BlockDevice {

    public static int BLOCK_SIZE = 50;

    public static double TOTAL_MEMORY = 1e+9;

    public static double FREE_MEMORY = TOTAL_MEMORY;

    static MemoryNode freeMemoryNodePointer;

    public static BlockDeviceImpl INSTANCE = new BlockDeviceImpl();


    public BlockDeviceImpl() {
        freeMemoryNodePointer = new MemoryNode();
        MemoryNode temp = freeMemoryNodePointer;

        for (int i = 1; i < TOTAL_MEMORY / BLOCK_SIZE; i++) {
            MemoryNode nextNode = new MemoryNode();
            temp.next = nextNode;
            temp = nextNode;
        }
    }

    public synchronized MemoryNode getNextFreePointer() {

        if (freeMemoryNodePointer == null)
            throw new RuntimeException("Memory full");

        MemoryNode nextFreePointer = freeMemoryNodePointer;
        freeMemoryNodePointer = freeMemoryNodePointer.next;

        return nextFreePointer;
    }

    @Override
    public String getData(MemoryNode memoryNode) {
        if (memoryNode == null)
            return null;

        String result = "";
        MemoryNode tempReadNode = memoryNode;
        try {

            while (tempReadNode != null) {
                result += tempReadNode.getContents();
                tempReadNode = tempReadNode.next;
            }

            return result;
        } catch (Exception exception) {
            System.out.println("ERROR :: " + exception.getMessage());
            throw exception;
        }
    }

    private void storeInMemory(MemoryNode memoryNode, String contents) {
        if (memoryNode == null || contents.length() == 0)
            return;

        memoryNode.setContents(contents);
    }

    @Override
    public synchronized void storeData(MemoryNode memoryNode, String contents) {


        if (memoryNode == null || contents.length() == 0)
            return;

        double charSize = contents.getBytes().length / contents.length();
        double fileSize = contents.getBytes().length;

        if (fileSize > FREE_MEMORY)
            throw new RuntimeException("Memory exceeded");

        MemoryNode currentFileStorageNode = memoryNode;
        int tempSize = 0;
        String tempContent = "";

        for (Character c : contents.toCharArray()) {
            if (tempSize + charSize > BLOCK_SIZE) {
                storeInMemory(currentFileStorageNode, tempContent);
                tempContent = c.toString();
                tempSize = 0;
                currentFileStorageNode.next = getNextFreePointer();
                currentFileStorageNode = currentFileStorageNode.next;
            } else {
                tempContent += c;
                tempSize += charSize;
            }
        }

        storeInMemory(currentFileStorageNode, tempContent);
        currentFileStorageNode.next = null;
        FREE_MEMORY-=fileSize;
    }

    @Override
    public synchronized void removeData(MemoryNode memoryNode) {
        if(memoryNode == null)
            return;

        MemoryNode currentFreeNode = freeMemoryNodePointer;

        if(currentFreeNode == null) {
            currentFreeNode = memoryNode;
        } else {
            while (currentFreeNode.next != null)
                currentFreeNode = currentFreeNode.next;
            currentFreeNode = memoryNode;
        }

        while (currentFreeNode.next != null) {
            String contents = currentFreeNode.getContents();
            currentFreeNode.setContents(null);
            currentFreeNode = currentFreeNode.next;

            FREE_MEMORY+=(contents.getBytes().length);
        }
    }
}
