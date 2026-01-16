public class Arquivo extends Entrada {
    private StringBuilder conteudo; // StringBuilder é melhor para edições (echo >>)

    public Arquivo(String nome, Diretorio pai) {
        super(nome, pai);
        this.conteudo = new StringBuilder();
    }

    public void escreverConteudo(String texto, boolean append) {
        if (append) {
            this.conteudo.append(texto);
        } else {
            this.conteudo = new StringBuilder(texto); // Sobrescreve
        }
    }

    public String lerConteudo() {
        return conteudo.toString();
    }

    @Override
    public int getTamanho() {
        return conteudo.length(); // Tamanho em caracteres (bytes simulados)
    }
}