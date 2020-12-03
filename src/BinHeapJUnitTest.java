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
        assertTrue(heap.size()==11,"Es sollten 11 Element drin sein!");
        assertEquals(heap.minimum(),entrys[0],"Entry 0 should have the lowest prio!");
    }

    @Test
    public void testExMin(){
        BinHeap<String, Integer> heap = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys=new BinHeap.Entry [11];
        for(int i=0;i<4;i++){
            char z=97;
            z+=i;
            entrys[i] = heap.insert(""+z, i);
        }
        OutputInStream outputStream=new OutputInStream();
        PrintStream stdout=new PrintStream(outputStream);
        PrintStream old=System.out;
        System.setOut(stdout);
        assertEquals( entrys[0],heap.extractMin(),"min should be a");
        heap.dump();
        System.setOut(old);
        //System.out.println(outputStream.getString());
        assertEquals("b 1c 2 d 3",outputStream.getString().replace("\n","").replace("\r",""));
    }

    @Test
    public void testExMinABCDEFGH(){
        BinHeap<String, Integer> heap = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys=new BinHeap.Entry [11];
        for(int i=0;i<8;i++){
            char z=97;
            z+=i;
            entrys[i] = heap.insert(""+z, i);
        }
        OutputInStream outputStream=new OutputInStream();
        PrintStream stdout=new PrintStream(outputStream);
        PrintStream old=System.out;
        System.setOut(stdout);
        assertEquals( entrys[0],heap.extractMin(),"min should be a");
        heap.dump();
        System.setOut(old);
        //System.out.println(outputStream.getString());
        assertEquals("b 1c 2 d 3e 4 f 5 g 6  h 7",outputStream.getString().replace("\n","").replace("\r",""));
    }

    @Test
    public void testExMinABCDEFGHIJK(){
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
        assertEquals(heap.size(),11);
        assertEquals( entrys[0],heap.extractMin(),"min should be a");
        heap.dump();
        System.setOut(old);
        //System.out.println(outputStream.getString());
        assertEquals("b 1 k 10c 2 d 3 i 8  j 9 e 4  f 5  g 6   h 7",outputStream.getString().replace("\n","").replace("\r",""));
        assertEquals(heap.size(),10);
    }

    @Test
    public void testInsertABCD(){
        BinHeap<String, Integer> heap = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys=new BinHeap.Entry [11];
        for(int i=0;i<4;i++){
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
        assertEquals("a 0 b 1 c 2  d 3",outputStream.getString().replace("\n","").replace("\r",""));
        assertTrue(heap.size()==4,"Es sollten 11 Element drin sein!");
        assertEquals(heap.minimum(),entrys[0],"Entry 0 should have the lowest prio!");
    }

    @Test
    public void testContainsBspdumb(){
        BinHeap<String, Integer> heap = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys=new BinHeap.Entry [1000];
        for(int i=0;i<1000;i++){
            char z=97;
            z+=i;
            entrys[i] = heap.insert(""+z, i);
        }
        for(int j=0;j<20;j++){
            for(int i=0;i<1000;i++){
                boolean b=heap.contains(entrys[i]);
                assertTrue(b,"Objekt "+entrys[i].prio()+","+entrys[i].data()+" sollte enthalten sein! daher TRUE nicht FALSE!");
            }
        }
    }


    @Test
    public void testContains1(){
        BinHeap<String, Integer> heap = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys=new BinHeap.Entry [1];
        entrys[0]=heap.insert("a",0);
        assertTrue(heap.contains(entrys[0]));
        assertTrue(heap.size()==1,"Ich hab nur eins hinzugefügt");
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