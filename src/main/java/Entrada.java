import java.time.LocalDateTime;

public abstract class Entrada {
    protected String nome;
    protected Diretorio pai;
    protected LocalDateTime dataCriacao;
    protected String permissao;
    protected LocalDateTime dataUltimaModificacao;

    public Entrada(String nome, Diretorio pai) {
        this.nome = nome;
        this.pai = pai;
        this.dataCriacao = LocalDateTime.now();
        this.permissao = "rwxr-xr-x";
        this.dataUltimaModificacao = LocalDateTime.now();
    }

    public String getNome() { return nome; }
    public Diretorio getPai() { return pai; }


    public abstract int getTamanho();

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataUltimaModificacao(LocalDateTime dataUltimaModificacao) {
        this.dataUltimaModificacao = dataUltimaModificacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getPermissao() {
        return permissao;
    }

    public LocalDateTime getDataUltimaModificacao() {
        return dataUltimaModificacao;
    }


}