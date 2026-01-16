import java.util.ArrayList;
import java.util.List;

public class Diretorio extends Entrada {
    private List<Entrada> filhos;

    public Diretorio(String nome, Diretorio pai) {
        super(nome, pai);
        this.filhos = new ArrayList<>();
    }

    public void adicionarFilho(Entrada entrada) {
        filhos.add(entrada);
    }

    public void removerFilho(Entrada entrada) {
        filhos.remove(entrada);
    }

    // Busca um arquivo ou subdiretório pelo nome
    public Entrada buscarFilho(String nome) {
        for (Entrada e : filhos) {
            if (e.getNome().equals(nome)) {
                return e;
            }
        }
        return null; // Não encontrou
    }

    public List<Entrada> getFilhos() {
        return filhos;
    }

    @Override
    public int getTamanho() {
        //Tamanho do diretório é a soma do tamanho dos filhos
        int total = 0;
        for (Entrada e : filhos) {
            total += e.getTamanho();
        }
        return total;
    }
}