public class MetodosAuxiliares {
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
}
