public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}



/*

    FileSystem Functions -
    1. fopen - Should return a file pointer and open a file in write mode
    fopen(String fileName);
        i. File already exists - It should overwrite so should return the existing file pointer.
        ii. If it doesnt exist will allocate a pointer and return.

    2. fwrite -
    fwrite(FilePointer, String contents)
        i. If file pointer doesnt exists


      fclose(FilePointer)
        close the stream or make this pointer null.

     fread(FilePointer fp)
        return the contents if exists.


    Block Device Interface
    1. Get data
    2. Store Data.
    3. Remove Data.
 */