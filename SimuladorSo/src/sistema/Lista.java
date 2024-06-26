package sistema;

import java.util.ArrayList;
import java.util.Random;

public class Lista {

    private ArrayList<Integer> ListaEncadeada = new ArrayList<Integer>();

    public void adicionarRecente(Integer novo) {
        System.out.println("A lista foi alterada /--/");
        this.removerEle(novo); //remover ele da lista caso ja esteja
        ListaEncadeada.add(novo); //adicionar no fim
        this.mostrarTudoArray();
    }

    private void removerEle(Integer elemento) {
        ListaEncadeada.remove(elemento); //Retira o elemento referenciado da lista
    }

    public void mostrarTudoArray() {
        for (int i = 0; i < ListaEncadeada.size(); i++) {
            System.out.println("posicao: " + i + " = " + ListaEncadeada.get(i));
        }

        System.out.println();
    }

    public Integer removerUm() { //remove o menos recentemente usado
        return ListaEncadeada.remove(0);
    }

    public Integer removerAleatorio() {
        if (ListaEncadeada.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int indice = random.nextInt(ListaEncadeada.size());
        return ListaEncadeada.remove(indice);
    }
}