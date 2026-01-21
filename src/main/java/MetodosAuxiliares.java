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

            Arquivo copia = new Arquivo(arqOrig.getNome(), novoPai, arqOrig.getProprietario());
            copia.setConteudo(arqOrig.lerConteudo());

            return copia;
        }

        // Cópia de diretório (recursiva)
        if (origem instanceof Diretorio) {
            Diretorio dirOrig = (Diretorio) origem;

            Diretorio copiaDir = new Diretorio(dirOrig.getNome(), novoPai, dirOrig.getProprietario());

            for (Entrada filho : dirOrig.getFilhos()) {
                Entrada copiaFilho = copiarEntrada(filho, copiaDir);
                copiaDir.adicionarFilho(copiaFilho);
            }

            return copiaDir;
        }

        return null;
    }
    public static boolean[] converterPermissao(String perm) {
        boolean[] p = new boolean[3];

        int valor = Character.getNumericValue(perm.charAt(0));
        p[0] = (valor & 4) != 0; // leitura
        p[1] = (valor & 2) != 0; // escrita
        p[2] = (valor & 1) != 0; // execução

        return p;
    }

}
