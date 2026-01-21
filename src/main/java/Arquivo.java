public class Arquivo extends Entrada {
    private StringBuilder conteudo; // StringBuilder é melhor para edições (echo >>)

    public Arquivo(String nome, Diretorio pai, String proprietario) {
        super(nome, pai,proprietario);
        this.conteudo = new StringBuilder();
    }

    public void escreverConteudo(String texto) {
        if(!this.podeEscrever) {
            System.out.println("Você noa tem permissão para escrever");
            return;
        }
        this.conteudo.append(texto + "\n");

        System.out.println("Você não tem permissão para escrever neste arquivo.");


    }

    public void setConteudo(String conteudo) {
        if(!this.podeEscrever) {
            System.out.println("Você não tem permissão para escrever");
            return;
        }
        this.conteudo = new StringBuilder(conteudo);
    }

    public String lerConteudo() {
        if(!this.podeEscrever) {
            System.out.println("Você não tem permissão para ler");
            return null;
        }
            return conteudo.toString();

    }

    @Override
    public int getTamanho() {
        return conteudo.length(); // Tamanho em caracteres (bytes simulados).
    }
}