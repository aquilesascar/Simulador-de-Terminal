import java.util.ArrayList;
import java.util.List;

public class ArquivoZip extends Arquivo {

    private List<Entrada> itensCompactados;

    public ArquivoZip(String nome, Diretorio pai) {
        super(nome, pai);
        this.itensCompactados = new ArrayList<>();
    }

    public void adicionar(Entrada entrada) {
        itensCompactados.add(entrada);
    }

    public List<Entrada> getItensCompactados() {
        return itensCompactados;
    }

    public List<Entrada> getConteudoZipado() {
        return this.itensCompactados;
    }
}
