package block_device;

public class MemoryNode {

    String contents;

    MemoryNode next;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public MemoryNode getNext() {
        return next;
    }

    public void setNext(MemoryNode next) {
        this.next = next;
    }

    public MemoryNode() {
        contents = null;
        next = null;

    }

    public MemoryNode(String contents) {
        contents = contents;
        next = null;
    }
}
