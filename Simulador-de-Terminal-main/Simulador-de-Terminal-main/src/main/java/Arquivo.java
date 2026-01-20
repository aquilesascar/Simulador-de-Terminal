public class Arquivo extends Entrada {
    private StringBuilder conteudo; // StringBuilder é melhor para edições (echo >>)

    public Arquivo(String nome, Diretorio pai) {
        super(nome, pai);
        this.conteudo = new StringBuilder();
    }

    public void escreverConteudo(String texto) {

            this.conteudo.append(texto);

    }

    public void setConteudo(String conteudo) {
        this.conteudo = new StringBuilder(conteudo);
    }

    public String lerConteudo() {
        return conteudo.toString();
    }

    @Override
    public int getTamanho() {
        return conteudo.length(); // Tamanho em caracteres (bytes simulados).
    }
}