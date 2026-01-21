public class MetodosAuxiliares {
    //Calsse para metodos auxiliares
    public static Entrada buscarDiretorio(Entrada raiz, String diretorio) {
        if(raiz.getNome().equals(diretorio)){
            return raiz;
        }
        if(raiz instanceof Diretorio) {
            for (Entrada ent : ((Diretorio) raiz).getFilhos()) {
                Entrada encontrado = buscarDiretorio(ent, diretorio);
                if (encontrado != null) {
                    return encontrado;

                }
            }
        }
        return null;


    }
    public static String mostrarCaminho(Entrada raiz, Entrada ent) {
        if (ent.equals(raiz)) {
            return "";
        }

        return mostrarCaminho(raiz,ent.getPai()) + "/" + ent.getNome();
    }
    public static Entrada copiarEntrada(Entrada origem, Diretorio novoPai) {

        // Cópia de arquivo
        if (origem instanceof Arquivo) {
            Arquivo arqOrig = (Arquivo) origem;

            Arquivo copia = new Arquivo(arqOrig.getNome(), novoPai);
            copia.setConteudo(arqOrig.lerConteudo());

            return copia;
        }

        // Cópia de diretório (recursiva)
        if (origem instanceof Diretorio) {
            Diretorio dirOrig = (Diretorio) origem;

            Diretorio copiaDir = new Diretorio(dirOrig.getNome(), novoPai);

            for (Entrada filho : dirOrig.getFilhos()) {
                Entrada copiaFilho = copiarEntrada(filho, copiaDir);
                copiaDir.adicionarFilho(copiaFilho);
            }

            return copiaDir;
        }

        return null;
    }
}
