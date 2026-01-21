public class NavegacaoManager {
    private Terminal terminal;

    public NavegacaoManager(Terminal terminal) {
        this.terminal = terminal;
    }

    public void cmdDiretorioraiz(){
        this.terminal.setDiretorioAtual(this.terminal.getRaiz());

    }

    public void cmdCd(String caminho) {
        //tratamento para voltar à raiz ou home.
        if (caminho == null || caminho.equals("~") || caminho.equals("/")) { // trata a "/"
            terminal.setDiretorioAtual(terminal.getRaiz());
            return;
        }

        if (caminho.equals("..")) {
            if (terminal.getDiretorioAtual().getPai() != null) {
                terminal.setDiretorioAtual(terminal.getDiretorioAtual().getPai());
            }
            return;
        }

        //tenta encontrar o diretório filho
        Entrada alvo = terminal.getDiretorioAtual().buscarFilho(caminho);

        if (alvo == null) {
            System.out.println("Diretório não encontrado: " + caminho);
        } else if (alvo instanceof Diretorio) {
            terminal.setDiretorioAtual((Diretorio) alvo);
        } else {
            System.out.println("Erro: '" + caminho + "' é um arquivo, não um diretório.");
        }
    }

    public void cmdPwd() {
        // Começa do diretório atual e vai subindo até a raiz
        Diretorio temp = terminal.getDiretorioAtual();
        String caminho = "";

        // Caso especial: se já estiver na raiz
        if (temp.getPai() == null) {
            System.out.println("/");
            return;
        }

        while (temp.getPai() != null) {
            caminho = "/" + temp.getNome() + caminho;
            temp = temp.getPai();
        }
        System.out.println(caminho);
    }

    public void tresPontos() {
        if(terminal.getDiretorioAtual().getPai() == null) {
            return;
        }
        terminal.setDiretorioAtual(terminal.getDiretorioAtual().getPai());
    }
}