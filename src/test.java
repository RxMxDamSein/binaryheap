import java.io.IOException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args)throws IOException {
        BinHeap<Double,String> heap=new BinHeap<>();
        //BinHeap.Entry<String, Integer> [] entrys = new BinHeap.Entry [100];
        ArrayList<BinHeap.Entry<Double,String>> entrys=new ArrayList<>();

        java.io.BufferedReader r = new java.io.BufferedReader(
                new java.io.InputStreamReader(System.in));

        int n=0;
        BinHeap.Entry<Double, String> e;
        while (true) {
            // Inhalt und Größe der Halde ausgeben.
            heap.dump();
            System.out.println(heap.size() + " entry(s)");

            // Eingabezeile vom Benutzer lesen, ggf. ausgeben (wenn das
            // Programm nicht interaktiv verwendet wird) und in einzelne
            // Wörter zerlegen.
            // Abbruch bei Ende der Eingabe oder leerer Eingabezeile.
            System.out.print(">>> ");
            String line = r.readLine();
            if (line == null || line.equals("")) return;
            if (System.console() == null) System.out.println(line);
            String [] cmd = line.split(" "); //+ sakdlas

            // Fallunterscheidung anhand des ersten Worts.
            switch (cmd[0]) {
                case "+": // insert prio
                    // Die laufende Nummer n wird als zusätzliche Daten
                    // verwendet.
                    if(cmd.length>2){
                        entrys.add(heap.insert(Double.parseDouble(cmd[1]), cmd[2]));
                    }else{
                        entrys.add( heap.insert(Double.parseDouble(cmd[1]), String.valueOf(n)));
                        n++;
                    }
                    break;
                case "++":
                    entrys.add(heap.insert(Double.parseDouble(cmd[1]), null));
                    break;
                case "-": // remove entry
                    heap.remove(entrys.get(Integer.parseInt(cmd[1])));
                    break;
                case "?": // minimum
                    e = heap.minimum();
                    System.out.println("--> " + e.prio() + " " + e.data());
                    break;
                case "!": // extractMin
                    e = heap.extractMin();
                    System.out.println("--> " + e.prio() + " " + e.data());
                    break;
                case "=": // changePrio entry prio
                    heap.changePrio(entrys.get(Integer.parseInt(cmd[1])), Double.parseDouble(cmd[2]));
                    break;
            }
        }
    }
}
