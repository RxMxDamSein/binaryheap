import org.junit.jupiter.api.Test;

import java.beans.beancontext.BeanContextChild;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class BinHeapJUnitTest extends BinHeap{

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
            assertEquals(heap.size(),i);
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
            assertEquals(heap.size(),i);
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
            assertEquals(heap.size(),i);
            entrys[i] = heap.insert(""+z, i);
        }
        OutputInStream outputStream=new OutputInStream();
        PrintStream stdout=new PrintStream(outputStream);
        PrintStream old=System.out;
        System.setOut(stdout);
        heap.dump();
        System.setOut(old);
        assertEquals("a 0 b 1 c 2  d 3",outputStream.getString().replace("\n","").replace("\r",""));
        assertTrue(heap.size()==4,"Es sollten 4 Element drin sein!");
        assertEquals(heap.minimum(),entrys[0],"Entry 0 should have the lowest prio!");
    }

    @Test
    public void testContainsBspdumb(){
        BinHeap<String, Integer> heap = new BinHeap<String, Integer>();
        BinHeap<String, Integer> heap10 = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys=new BinHeap.Entry [1100];
        for(int i=0;i<1100;i++){
            char z=97;
            z+=i;

            if(i<1000){
                assertEquals(heap.size(),i);
                entrys[i] = heap.insert(""+z, i);

            }else{
                entrys[i]= heap10.insert(""+z, i);
                assertEquals(heap.size(),1000);
            }

        }
        BinHeap<String, Integer> heap2 = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys2=new BinHeap.Entry [1000];
        for(int i=0;i<1000;i++){
            char z=97;
            z+=i;
            entrys2[i] = heap2.insert(""+z, i);
        }
        BinHeap<Double, String> heap3 = new BinHeap<>();
        BinHeap.Entry [] entrys3=new BinHeap.Entry [10];
        for(int i=0;i<10;i++){
            char z=97;
            z+=i;
            entrys3[i] = heap3.insert(i*0.99, ""+z);
        }
        for(int j=0;j<20;j++){
            for(int i=0;i<1000;i++){
                boolean b=heap.contains(entrys[i]);
                boolean b2=heap.contains(entrys2[i]);
                if(i<10){
                    boolean b3=heap.contains(entrys3[i]);
                    assertFalse(b3,"ist aus nem anderen heap");
                    assertTrue(heap3.contains(entrys3[i]));
                }
                assertFalse(heap.isEmpty());
                assertTrue(b,"Objekt "+entrys[i].prio()+","+entrys[i].data()+" sollte enthalten sein! daher TRUE nicht FALSE!");
                assertFalse(b2,"ist aus nem anderen heap -> nicht bei dir");
                assertTrue(heap2.contains(entrys2[i]));
            }
            for(int i=1000;i<1100;i++){
                assertFalse(heap.contains(entrys[i]));
            }
        }
    }

    @Test
    public void testRemoveHfromABCDEFGHIJK(){
        BinHeap<String, Integer> heap = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys=new BinHeap.Entry [11];
        assertTrue(heap.isEmpty());
        for(int i=0;i<11;i++){
            char z=97;
            z+=i;
            entrys[i] = heap.insert(""+z, i);
            assertFalse(heap.isEmpty());
        }
        OutputInStream outputStream=new OutputInStream();
        PrintStream stdout=new PrintStream(outputStream);
        PrintStream old=System.out;
        System.setOut(stdout);
        assertTrue(heap.remove(entrys[7]));
        heap.dump();
        System.setOut(old);
        assertEquals("b 1 k 10a 0 f 5 e 4  g 6 c 2  d 3  i 8   j 9",outputStream.getString().replace("\n","").replace("\r",""));
        assertTrue(heap.size()==10,"Es sollten 4 Element drin sein!");
        assertFalse(heap.isEmpty());
        assertEquals(heap.minimum(),entrys[0],"Entry 0 should have the lowest prio!");
    }

    @Test
    public void testRemove12from1to16_double(){
        BinHeap<Double, String> heap = new BinHeap<>();
        BinHeap.Entry<Double,String> [] entrys=new BinHeap.Entry [16];
        assertTrue(heap.isEmpty());
        for(int i=1;i<17;i++){
            String s=String.valueOf(i-1);
            Double d= Double.valueOf(i);
            entrys[i-1] = heap.insert(d,s);
            assertFalse(heap.isEmpty());
        }
        OutputInStream outputStream=new OutputInStream();
        PrintStream stdout=new PrintStream(outputStream);
        PrintStream old=System.out;
        System.setOut(stdout);
        assertTrue(heap.remove(entrys[11]));
        heap.dump();
        System.setOut(old);

        String[] reference=("2.0 1\n" +
                "3.0 2\n" +
                " 4.0 3\n" +
                "5.0 4\n" +
                " 6.0 5\n" +
                " 7.0 6\n" +
                "  8.0 7\n" +
                "1.0 0\n" +
                " 10.0 9\n" +
                " 9.0 8\n" +
                "  11.0 10\n" +
                " 13.0 12\n" +
                "  14.0 13\n" +
                "  15.0 14\n" +
                "   16.0 15").split("\n");
        String[] in=outputStream.getString().split("\n");
        //System.out.println(outputStream.getString());
        for (int i=0;i<reference.length;i++){
            assertEquals(reference[i],in[i].replace("\n","").replace("\r",""));
        }
        assertTrue(heap.size()==15,"Es sollten 15 Element drin sein!");
        assertFalse(heap.isEmpty());
        assertEquals(heap.minimum(),entrys[0],"Entry 0 should have the lowest prio!");
    }


    @Test
    public void testContains1(){
        BinHeap<String, Integer> heap = new BinHeap<String, Integer>();
        BinHeap.Entry<String, Integer> [] entrys=new BinHeap.Entry [1];
        entrys[0]=heap.insert("a",0);
        assertTrue(heap.contains(entrys[0]));
        assertTrue(heap.size()==1,"Ich hab nur eins hinzugefügt");
        assertFalse(heap.isEmpty());
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