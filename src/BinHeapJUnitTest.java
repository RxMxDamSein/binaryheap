import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class BinHeapJUnitTest {

    @Test
    public void testBSPdumb(){
        BinHeap<String, Integer> heap = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys=new BinHeap.Entry [11];
        for(int i=0;i<11;i++){
            char z=97;
            z+=i;
            entrys[i] = heap.insert(""+z, i);


        }
        OutputInStream outputStream=new OutputInStream();
        PrintStream stdout=new PrintStream(outputStream);
        PrintStream old=System.out;
        System.setOut(stdout);
        heap.dump();
        System.setOut(old);
        assertEquals("k 10i 8 j 9a 0 b 1 c 2  d 3 e 4  f 5  g 6   h 7",outputStream.getString().replace("\n","").replace("\r",""));
    }
}
class OutputInStream extends OutputStream{
    StringBuilder mBuf=new StringBuilder();
    @Override
    public void write(int b) throws IOException {
        mBuf.append((char)b);
    }
    public String getString(){
        return mBuf.toString();
    }
}