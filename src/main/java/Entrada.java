import java.time.LocalDateTime;

// Classe abstrata que serve de base para Arquivos e Diretórios
public abstract class Entrada {
    protected String nome;
    protected Diretorio pai; // Importante para o comando 'cd ..'
    protected LocalDateTime dataCriacao;
    protected String permissao; // Ex: "rwxrwxrwx"

    public Entrada(String nome, Diretorio pai) {
        this.nome = nome;
        this.pai = pai;
        this.dataCriacao = LocalDateTime.now();
        this.permissao = "rwxr-xr-x"; // Permissão padrão
    }

    public String getNome() { return nome; }
    public Diretorio getPai() { return pai; }

    // Método abstrato: cada um calcula seu tamanho de um jeito
    public abstract int getTamanho();
}