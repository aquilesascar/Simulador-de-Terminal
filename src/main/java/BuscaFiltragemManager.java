public class BuscaFiltragemManager {
    Terminal terminal;

    public BuscaFiltragemManager(Terminal terminal) {
        this.terminal = terminal;
    }

    public void cmdFind (String linhaComando){
        String [] partes = linhaComando.trim().split("\\s+");
        if(partes.length > 3 && partes[2].equals("-name")){
            if(partes[1]!= null && partes[3] != null){
                Entrada raiz = MetodosAuxiliares.buscarDiretorio(terminal.getDiretorioAtual(), partes[1]);
                if(raiz != null){
                    Entrada encontrado = MetodosAuxiliares.buscarDiretorio(raiz, partes[3]);
                    if(encontrado != null){
                        String caminho = MetodosAuxiliares.mostrarCaminho(raiz, encontrado);
                        System.out.println(caminho);

                    }else{
                        System.out.println("diretorio não encontrado");
                    }
                }else {
                    System.out.println("Diretorio raiz nao encontrado");
                }
                }else{
                System.out.println("Use: find <diretorio> -name <nome>");

            }
        }else{
            System.out.println("Use: find <diretorio> -name <nome>");
        }

    }
    public void cmdGrep(String linhaComando) {
        String[] partes = linhaComando.trim().split("\\s+");

        if (partes.length < 3) {
            System.out.println("Use: grep <termo> <arquivo>");
            return;
        }

        String termo = partes[1];
        String nomeArquivo = partes[2];

        Entrada entrada = terminal.getDiretorioAtual().buscarFilho(nomeArquivo);

        if (entrada == null || !(entrada instanceof Arquivo)) {
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
            return;
        }

        Arquivo arquivo = (Arquivo) entrada;
        String conteudo = arquivo.lerConteudo();

        String[] linhas = conteudo.split("\\r?\\n");
        boolean encontrou = false;

        for (int i = 0; i < linhas.length; i++) {
            if (linhas[i].contains(termo)) {
                System.out.println((i + 1) + ": " + linhas[i]);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma ocorrência encontrada.");
        }
    }

}
