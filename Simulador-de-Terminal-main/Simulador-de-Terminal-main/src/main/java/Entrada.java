import java.time.LocalDateTime;


public abstract class Entrada {
    protected String nome;
    protected Diretorio pai;
    protected LocalDateTime dataCriacao;
    protected String permissao;

    public Entrada(String nome, Diretorio pai) {
        this.nome = nome;
        this.pai = pai;
        this.dataCriacao = LocalDateTime.now();
        this.permissao = "rwxr-xr-x";
    }

    public String getNome() { return nome; }
    public Diretorio getPai() { return pai; }


    public abstract int getTamanho();
}