package treesearch;



import java.util.Iterator;

import es.upm.aedlib.Position;
import es.upm.aedlib.set.*;
import es.upm.aedlib.positionlist.*;
import es.upm.aedlib.tree.*;


public class TreeSearch {

	private static void searchRec (Tree<String> t, PositionList<String> searchExprs , Position<String> cExpr, Position<String> cTree, Set<Position<String>> set, int counter) { 
		Position<String> next = searchExprs.next(cExpr);	
		//final de lista compruebo segun ultimo elemento de lista
		if(counter==searchExprs.size()) { 
			if (counter==1 || t.isExternal(cTree)) set.add(cTree);//Primer elemento -> al set 
			//comodin
			else if(cExpr.element().equals("*")) {
				//Hoja del arbol
				if (t.isExternal(cTree)) set.add(cTree);
				else for(Position<String> son: t.children(t.parent(cTree))) {
					if(son.element()!=null) set.add(son);
				}
			} 	
			else for(Position<String> son: t.children(t.parent(cTree))) {
				if(son.element()!=null) { 
					if(cTree.element().equals(son.element())) {
						set.add(son);
					}
				}
			}
			return;
		}
		//recorrido normal, busco el siguiente nodo en los hijos desde donde estoy
		for(Position<String> son: t.children(cTree)) {	
			//encontrado, recursion con el y el siguiente elemento de la expresion de busqueda
			if(son.element().equals(next.element()) || next.element().equals("*")){
				searchRec(t, searchExprs, next, son, set, counter+1);	
			}
		}
	}

	public static Set<Position<String>> search(Tree<String> t, PositionList<String> searchExprs) {
		Set<Position<String>> resultado = new PositionListSet<Position<String>> ();
		Position<String> first = searchExprs.first(); Position<String> root = t.root(); int counter=1;
		if(first.element().equals(root.element()) || first.element().equals("*")) {
			if(searchExprs.size()<=t.size()) {
				searchRec(t, searchExprs, first, root, resultado, counter);
			}
		}
		return resultado;
	}

	//Dado un arbol, una posicion de un camino y la posicion actual en dicho arbol, 
	//Devuelve la posicion en el arbol si el el elemento del camino es hijo de la posicion actual en el arbol. Si el return=null no es hijo 
	private static Position<String> esHijo (Tree<String> arbol, Position<String> cCamino, Position<String> cArbol){
		Position<String> hijoArbol = null;
		for(Position<String> hijo: arbol.children(cArbol)) {
			if(hijo!=null) {
				if(cCamino.element().equals(hijo.element())) {
					hijoArbol=hijo;
				}
			}
		}
		return hijoArbol;
	}

	public static Tree<String> constructDeterministicTree(Set<PositionList<String>> paths) {
		LinkedGeneralTree<String> arbol = new LinkedGeneralTree<String> ();
		for(PositionList<String> lista: paths) if(lista!=null) if (lista.isEmpty()) paths.remove(lista);//Limpiamos el set de listas vacias
		if(!paths.isEmpty()) {
			arbol.addRoot(paths.iterator().next().first().element());//Añadimos raíz	
			for (PositionList<String> camino : paths ) {
				Position<String> cArbol=arbol.root();
				Set<Position<String>> posCamino = search(arbol, camino);//Set de posiciones si existe el camino 
				if (posCamino.isEmpty()) { //Set vacio = hay que add camino
					Position<String> cCamino = camino.first();	
					cCamino=camino.next(cCamino);
					while(cCamino!=null) { 
						Position<String> hijoArbol = esHijo(arbol, cCamino, cArbol);
						if(hijoArbol==null) {
							arbol.addChildLast(cArbol, cCamino.element());
							cArbol = esHijo(arbol, cCamino, cArbol);
						}
						else {cArbol=hijoArbol;}
						cCamino=camino.next(cCamino);
					}
				}
			}
		}
		return arbol;
	}
}
