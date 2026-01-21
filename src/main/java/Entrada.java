import java.time.LocalDateTime;

public abstract class Entrada {
    protected String nome;
    protected Diretorio pai;
    protected LocalDateTime dataCriacao;
    protected String permissao;
    protected LocalDateTime dataUltimaModificacao;
    protected boolean podeLer = true;
    protected boolean podeEscrever = true;
    protected boolean podeExecutar = true;
    protected String proprietario;

    public Entrada(String nome, Diretorio pai, String proprietario) {
        this.nome = nome;
        this.pai = pai;
        this.dataCriacao = LocalDateTime.now();
        this.permissao = "rwxr-xr-x";
        this.dataUltimaModificacao = LocalDateTime.now();
        this.podeLer = true;
        this.podeEscrever = true;
        this.podeExecutar = true;
        this.proprietario = proprietario;

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

    public boolean isPodeExecutar() {
        return podeExecutar;
    }

    public void setPodeExecutar(boolean podeExecutar) {
        this.podeExecutar = podeExecutar;
    }

    public boolean isPodeEscrever() {
        return podeEscrever;
    }

    public void setPodeEscrever(boolean podeEscrever) {
        this.podeEscrever = podeEscrever;
    }

    public boolean isPodeLer() {
        return podeLer;
    }

    public void setPodeLer(boolean podeLer) {
        this.podeLer = podeLer;
    }

    public void setPermissoes(boolean b, boolean b1, boolean b2) {
        this.podeLer = b;
        this.podeEscrever = b1;
        this.podeExecutar = b2;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }
    public String getPermissoes() {
        return (podeLer ? "r" : "-") +
                (podeEscrever ? "w" : "-") +
                (podeExecutar ? "x" : "-");
    }
}