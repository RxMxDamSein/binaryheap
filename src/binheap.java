import javafx.util.Pair;

import java.util.Vector;

// Als Binomial-Halde implementierte Minimum-Vorrangwarteschlange
// mit Prioritäten eines beliebigen Typs P (der die Schnittstelle
// Comparable<P> oder Comparable<P'> für einen Obertyp P' von P
// implementieren muss) und zusätzlichen Daten eines beliebigen Typs D.
class BinHeap <P extends Comparable<? super P>, D> {
	//Vector<Node> nodes;
	//P lowestPrio;
	Node wurzel;
	//Integer anzNodes;

	/**
	 * ToDo
	 */
	public BinHeap(){
		//nodes=new Vector<Node>();
		wurzel=null;
	}

	// Ist die Halde momentan leer?
	public boolean isEmpty (){
		return wurzel==null;
	}

	/**
	 * Todo
	 * Größe der Halde, d. h. Anzahl momentan gespeicherter Einträge liefern.
	 * @return
	 */
	public int size() {
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

	public int anzTree(){
		int anz=0;
		if(wurzel==null)
			return 0;
		Node n=wurzel;
		while (n!=null){
			anz++;
			n=n.sibling;
		}
		return anz;
	}


	/** ToDo
	 * Enthält die Halde den Eintrag e?
	 * REWRITE!
	 * geh vom node wo du giregst einfach soweit hoch wie möglich und kuck ob du bei der wurzel rauskommst!
	 */
	public boolean contains (Entry<P, D> e){
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

	/*ToDo

	 */
	private boolean containsInSubTree(Node n,Node w){
		if(w==null)
			return false;
		w=w.sibling;
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
	public Entry<P, D> insert(P p, D d) {
		if(p==null || d==null)
			return null;
		Entry e=new Entry(p,d);
		Node n = new Node(e);
		n.degree=0;
		//nodes.add(n);
		if (isEmpty()){
			wurzel=n;
			//lowestPrio=p;
		}else{
			n.sibling=wurzel;
			wurzel=n;
			//if(p.compareTo(lowestPrio)<0)
			//	lowestPrio=p;
			checkSameDegree();
		}
		return e;
	}


	private void checkSameDegree(){
		checkSameDegree(null);
	}
	private void checkSameDegree(Node s){
		Node start;
		if(s==null)
			start= wurzel;
		else
			start=s;

		while (start.sibling != null) {
			if (start.degree == start.sibling.degree) {
				start = mergeSameDegree(start, start.sibling);
				if (start.sibling == null)
					break;
				else
					continue;
			}
			start = start.sibling;
		}

	}

	private Node mergeSameDegree(Node n1,Node n2){
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
			/*Node currNote=lower.child;
			while(currNote.sibling!=lower.child){
				currNote=currNote.sibling;
			}
			currNote.sibling=higher;
			higher.sibling=lower.child;*/
			higher.sibling=lower.child.sibling;
			lower.child.sibling=higher;
			lower.child=higher;
		}else {
			lower.child=higher;
			higher.sibling=higher;
		}

		higher.parent=lower;
		lower.degree=lower.degree+1;
		return lower;
	}
	/**
	 * Todo dump (Ausgabe)
	 */
	public void dump() {
		//(P) nodes.get(i).prio();

		if(wurzel == null) {
			return;
		}
		Node x = wurzel;
		while (x != null) {
			System.out.println(x.prio()+" "+x.entry.data+" "+x.degree);
			Node y=x.child;
			if(y!=null){
				printChild(y,1);
			}
			x = x.sibling;
		}
	}



	private void printChild(Node c,int leer){
		String sleer="";
		c=c.sibling;
		for (int i=0;i<leer;i++)
			sleer+=" ";
		Node cz=c;
		do{
			System.out.println(sleer+cz.prio()+" "+cz.entry.data+" "+cz.degree);
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
		/*if(wurzel==null)
			return null;
		Node n =wurzel;
		Node min= wurzel;
		while (n.sibling!=null){
			if(((P)n.sibling.prio()).compareTo((P)min.prio())<0)
				min=n.sibling;
			n=n.sibling;
		}
		return min.entry;*/
		Node[] mins=mins();
		if(mins==null){
			return null;
		}else {
			return mins[0].entry;
		}
	}

	private Node[] mins(){
		if(wurzel==null)
			return null;
		Node n =wurzel;
		Node ln=null;
		Node min= wurzel;
		while (n.sibling!=null){
			if(((P)n.sibling.prio()).compareTo((P)min.prio())<0){
				min=n.sibling;
				ln=n;
			}

			n=n.sibling;
		}
		return new Node[]{min,ln};
	}

	/**
	 * ToDo
	 * @return
	 */
	public Entry<P, D> extractMin() {
		Node[] e=mins();
		if(e==null)
			return null;

		Node child=e[0].child;
		if(e[1]!=null){//Min aus Heap entfernen
			e[1].sibling=e[0].sibling;
		}else{
			wurzel=e[0].sibling;
		}

		if(child!=null){
			BinHeap<P,D> bh=new BinHeap<>();
			int k=0;
			Node h1=wurzel;
			Node h2=child.sibling;
			child.sibling=null;
			Node h=null;
			Node he=null;
			while(h1!=null || h2!=null || h!=null){
				if(h1!=null &&h1.degree==k){
					if(h==null){
						h=new Node(new Entry(h1.prio(),h1.entry.data()));
						h.child=h1.child;
						h.degree=h1.degree;
					}else {
						Node z=h;
						while (z.sibling!=null)
							z=z.sibling;
						z.sibling=new Node(new Entry(h1.prio(),h1.entry.data()));
						z.sibling.child=h1.child;
						z.sibling.degree=h1.degree;
					}
					h1=h1.sibling;
				}
				if(h2!= null &&h2.degree==k){
					if(h==null){
						h=new Node(new Entry(h2.prio(),h2.entry.data()));
						h.child=h2.child;
						h.degree=h2.degree;
					}else {
						Node z=h;
						while (z.sibling!=null)
							z=z.sibling;
						z.sibling=new Node(new Entry(h2.prio(),h2.entry.data()));
						z.sibling.child=h2.child;
						z.sibling.degree=h2.degree;
					}
					h2=h2.sibling;
				}
				bh.wurzel=h;
				if(bh.anzTree()%2==1){//anzahl Bäume %2 == 1
					if(he==null) {
						he = new Node(new Entry(h.prio(), h.entry.data()));
						he.child = h.child;
						he.degree=h.degree;
					}else{
						Node z=he;
						while (z.sibling!=null)
							z=z.sibling;
						z.sibling=new Node(new Entry(h.prio(),h.entry.data()));
						z.sibling.child=h.child;
						z.degree=h.degree;
					}

					h=h.sibling;
				}

				if(h!=null){
					bh.wurzel=h;
					bh.checkSameDegree();
					h=bh.wurzel;
				}

				k+=1;
			}
			wurzel=he;
		}
		//if(wurzel!=null)
		//		checkSameDegree();
		return e[0].entry;
	}

	/**
	 * ToDo
	 * @param entry
	 * @param s
	 * @return
	 */
	public boolean changePrio(Entry<P, D> entry, P s) {
		if(s==null || entry==null || !contains(entry) )
			return false;
		//Node e = entry.node;
		Integer a;
		a = s.compareTo(entry.prio);
		//gleich
		if (a == 0) {
			return false;
		}
		entry.prio = s;

		//neue prio kleiner als alte (ab nach oben)
		if (a < 0) {
			up(entry);
			return true;
		}

		//neu prio größer als alte (ab nach unten)
		if (a > 0) {
			down(entry);
			return true;
		}
		/*
		System.out.println("Prio:");
		System.out.println(entry.prio());
		System.out.println("____________");
		System.out.println("New Prio= "+s);
		entry.prio = s;
		System.out.println("Gechante Prio: "+entry.prio());
		s.compareTo()

		 */

		return false;
	}
	public void up (Entry<P, D> a) {
		Integer x = a.node.prio().compareTo(a.node.parent.prio());
		while (x < 0) {
			swap(a.node,a.node.parent);
			if (a.node.parent == null) {
				return;
			}
			x = a.node.prio().compareTo(a.node.parent.prio());
		}
	}
	public void down (Entry<P, D> a) {
		Integer x = a.node.prio().compareTo(a.node.parent.prio());
		while (x > 0) {
			swap(a.node,a.node.parent);
			if (a.node.parent == null) {
				return;
			}
			x = a.node.prio().compareTo(a.node.parent.prio());
		}
	}
	public void swap(Node a,Node b) {
		Node temp;
		temp = a;
		a = b;
		b = temp;
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
			int c=-999999;
			if(cmd.length>2){
				c=Integer.parseInt(cmd[2]);
				entrys[n] = heap.insert(cmd[1], c);
			}else{
				entrys[n] = heap.insert(cmd[1], n);
				n++;
			}


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
