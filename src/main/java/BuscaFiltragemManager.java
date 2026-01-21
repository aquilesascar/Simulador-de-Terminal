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
}
