import javafx.util.Pair;

import java.util.Vector;

// Als Binomial-Halde implementierte Minimum-Vorrangwarteschlange
// mit Prioritäten eines beliebigen Typs P (der die Schnittstelle
// Comparable<P> oder Comparable<P'> für einen Obertyp P' von P
// implementieren muss) und zusätzlichen Daten eines beliebigen Typs D.
class BinHeap <P extends Comparable<? super P>, D> {
	//Vector<Node> nodes;
	P lowestPrio;
	Node wurzel;
	Integer anzNodes;

	/**
	 * ToDo
	 */
	public BinHeap(){
		//nodes=new Vector<Node>();
		wurzel=null;
	}

	// Ist die Halde momentan leer?
	boolean isEmpty (){
		return wurzel==null;
	}

	/**
	 * Todo
	 * Größe der Halde, d. h. Anzahl momentan gespeicherter Einträge liefern.
	 * @return
	 */
	int size() {
		//return nodes.size();
		int size=0;
		if(wurzel==null)
			return 0;
		Node n=wurzel;
		while (n!=null){
			size+=Math.pow(2,n.degree);
			n=n.sibling;
		}
		return size;
	}


	/** ToDo
	 * Enthält die Halde den Eintrag e?
	 */
	boolean contains (Entry<P, D> e){
		if(e==null ||e.prio==null || wurzel==null)
			return false;
		Node n=e.node;
		int degree=n.degree;
		if(degree<0)
			return false;
		Node w=wurzel;
		while (w!=null && w.degree<degree){
			w=w.sibling;
		}
		do{
			if((w.degree==n.degree && ((P)w.prio()).compareTo((P)(n.prio()))==0 && w.entry.data().equals(n.entry.data())|| (w.degree>n.degree) &&containsInSubTree(n,w.child)))
				return true;
			w=w.sibling;
		} while (w!=null);
		return false;
	}

	//ToDo
	private boolean containsInSubTree(Node n,Node w){
		if(w==null)
			return false;
		Node c=w;
		while(c.degree<n.degree && c.sibling!=w){
			c=c.sibling;
		}
		if(c.degree<n.degree)
			return false;
		do{
			if(c.degree==n.degree && ((P)c.prio()).compareTo((P)(n.prio()))==0 && c.entry.data().equals(n.entry.data()))
				return true;
			if(c.child!=null)
				if (containsInSubTree(n,c.child))
					return true;
			c=c.sibling;
		} while(c!=w.child && c.degree>n.degree);
		return false;
	}

	/**
	 * Todo
	 * // Neuen Eintrag mit Priorität p und zusätzlichen Daten d erzeugen,// zur Halde hinzufügen und zurückliefern.
	 * @return
	 */
	Entry<P, D> insert(P p, D d) {
		Entry e=new Entry(p,d);
		Node n = new Node(e);
		n.degree=0;
		//nodes.add(n);
		if (isEmpty()){
			wurzel=n;
			lowestPrio=p;
		}else{
			n.sibling=wurzel;
			wurzel=n;
			if(p.compareTo(lowestPrio)<0)
				lowestPrio=p;
			while(checkSameDegree());
		}
		return e;
	}

	private boolean checkSameDegree(){
		Node start=wurzel;
		while(start.sibling!=null){
			if(start.degree==start.sibling.degree){
				mergeSameDegree(start,start.sibling);
				return true;
			}
			start=start.sibling;
		}
		return false;
	}

	private void mergeSameDegree(Node n1,Node n2){
		Node higher,lower;
		if(((P)n1.prio()).compareTo((P)n2.prio())>0){
			higher=n1;
			lower=n2;
		}else{
			higher=n2;
			lower=n1;
		}
		Node bFh=wurzel;	//before higher
		if(wurzel!=higher) {
			while (bFh.sibling != higher) {
				bFh = bFh.sibling;
			}
			bFh.sibling = higher.sibling;
		}else {
			wurzel=higher.sibling;
		}

		if(lower.child!=null){
			Node currNote=lower.child;
			while(currNote.sibling!=lower.child){
				currNote=currNote.sibling;
			}
			currNote.sibling=higher;
			higher.sibling=lower.child;
		}else {
			lower.child=higher;
			higher.sibling=higher;
		}

		higher.parent=lower;
		lower.degree=lower.degree+1;
	}
	/**
	 * Todo dump (Ausgabe)
	 */
	void dump() {
		//(P) nodes.get(i).prio();

		if(wurzel == null) {
			return;
		}
		Node x = wurzel;
		while (x != null) {
			System.out.println(x.prio()+" "+x.entry.data);
			Node y=x.child;
			if(y!=null){
				printChild(y,1);
			}
			x = x.sibling;
		}
	}

	private void printChild(Node c,int leer){
		String sleer="";
		for (int i=0;i<leer;i++)
			sleer+=" ";
		Node cz=c;
		do{
			System.out.println(sleer+cz.prio()+" "+cz.entry.data);
			if(cz.child!=null){
				printChild(cz.child,leer+1);
			}
			cz=cz.sibling;
		}while (cz!=c);
	}





	/**
	 * ToDo
	 * @param entry
	 * @return
	 */
	public boolean remove(Entry<P, D> entry) {
		return false;
	}

	/**
	 * ToDo
	 * @return
	 */
	public Entry<P, D> minimum() {
		return null;
	}

	/**
	 * ToDo
	 * @return
	 */
	public Entry<P, D> extractMin() {
		return null;
	}

	/**
	 * ToDo
	 * @param entry
	 * @param s
	 * @return
	 */
	public boolean changePrio(Entry<P, D> entry, P s) {
		return false;
	}

	// Eintrag einer solchen Warteschlange bzw. Halde, bestehend aus
    // einer Priorität prio mit Typ P und zusätzlichen Daten data mit
    // Typ D.
    // Wenn der Eintrag momentan tatsächlich zu einer Halde gehört,
    // verweist node auf den zugehörigen Knoten eines Binomialbaums
    // dieser Halde.
    public static class Entry <P, D> {
	// Priorität, zusätzliche Daten und zugehöriger Knoten.
	private P prio;
	private D data;
	private Node<P, D> node;

	// Eintrag mit Priorität p und zusätzlichen Daten d erzeugen.
	private Entry (P p, D d) {
	    prio = p;
	    data = d;
	}

	// Priorität bzw. zusätzliche Daten liefern.
	public P prio () { return prio; }
	public D data () { return data; }
    }

    // Knoten eines Binomialbaums innerhalb einer solchen Halde.
    // Neben den eigentlichen Knotendaten (degree, parent, child,
    // sibling), enthält der Knoten einen Verweis auf den zugehörigen
    // Eintrag.
    private static class Node <P, D> {

	// Zugehöriger Eintrag.
	private Entry<P, D> entry;

	// Grad des Knotens.
	private int degree;

	// Vorgänger (falls vorhanden; bei einem Wurzelknoten null).
	private Node<P, D> parent;

	// Nachfolger mit dem größten Grad
		// JEtzt anscheinend mit kleinstem GRAD WTF
	// (falls vorhanden; bei einem Blattknoten null).
	private Node<P, D> child;

	// Zirkuläre Verkettung aller Nachfolger eines Knotens
	// bzw. einfache Verkettung aller Wurzelknoten einer Halde,
	// jeweils sortiert nach aufsteigendem Grad.
	private Node<P, D> sibling;

	// Knoten erzeugen, der auf den Eintrag e verweist
	// und umgekehrt.
	private Node (Entry<P, D> e) {
	    entry = e;
	    e.node = this;
	}

	// Priorität des Knotens, d. h. des zugehörigen Eintrags
	// liefern.
	private P prio () { return entry.prio; }
    }

}

// Interaktives Testprogramm für die Klasse BinHeap.
class BinHeapTest {
    public static void main (String [] args) throws java.io.IOException {
	// Leere Halde mit Prioritäten des Typs String und zugehörigen
	// Daten des Typs Integer erzeugen.
	// (Die Implementierung muss aber natürlich auch mit anderen
	// Typen funktionieren.)
	BinHeap<String, Integer> heap = new BinHeap<String, Integer>();

	// Feld mit allen eingefügten Einträgen, damit sie später
	// für remove und changePrio verwendet werden können.
	// Achtung: Obwohl die Klasse BinHeap ebenfalls Typparameter
	// besitzt, schreibt man "BinHeap.Entry<String, Integer>" und
	// nicht "BinHeap<String, Integer>.Entry<String, Integer>".
	// Achtung: "new BinHeap.Entry [100]" führt zu einem Hinweis
	// über "unchecked or unsafe operations"; die eigentlich "korrekte"
	// Formulierung "new BinHeap.Entry<String, Integer> [100]"
	// führt jedoch zu einem Übersetzungsfehler!
	BinHeap.Entry<String, Integer> [] entrys = new BinHeap.Entry [100];

	// Anzahl der bis jetzt eingefügten Einträge.
	int n = 0;

	// Standardeingabestrom System.in als InputStreamReader
	// und diesen wiederum als BufferedReader "verpacken",
	// damit man bequem zeilenweise lesen kann.
	java.io.BufferedReader r = new java.io.BufferedReader(
			    new java.io.InputStreamReader(System.in));

	// Endlosschleife.
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
		entrys[n] = heap.insert(cmd[1], n);
		n++;
		break;
	    case "-": // remove entry
		heap.remove(entrys[Integer.parseInt(cmd[1])]);
		break;
	    case "?": // minimum
		BinHeap.Entry<String, Integer> e = heap.minimum();
		System.out.println("--> " + e.prio() + " " + e.data());
		break;
	    case "!": // extractMin
		e = heap.extractMin();
		System.out.println("--> " + e.prio() + " " + e.data());
		break;
	    case "=": // changePrio entry prio
		heap.changePrio(entrys[Integer.parseInt(cmd[1])], cmd[2]);
		break;
	    }
	}
    }
}
