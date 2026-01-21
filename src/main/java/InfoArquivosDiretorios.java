public class InfoArquivosDiretorios {
    Terminal terminal;

    public InfoArquivosDiretorios(Terminal terminal) {
        this.terminal = terminal;
    }

    public void cmdStat(String entrada) {

        Entrada ent = this.terminal.getDiretorioAtual().buscarFilho(entrada);
        if (ent != null) {
            System.out.println("Nome: " + ent.getNome());
            System.out.println("Data da criação"+ ent.getDataCriacao());
            System.out.println("Data da ultima modificão"+ ent.getDataUltimaModificacao());
            System.out.println("Tamanho: " + ent.getTamanho());
        }

    }
    public void cmdDu(String nomeDiretorio) {
        if (nomeDiretorio == null) {
            System.out.println("Use: du <diretorio>");
            return;
        }

        Entrada ent = terminal.getDiretorioAtual().buscarFilho(nomeDiretorio);

        if (ent == null || !(ent instanceof Diretorio)) {
            System.out.println("Diretório não encontrado: " + nomeDiretorio);
            return;
        }

        Diretorio dir = (Diretorio) ent;
        int tamanho = dir.getTamanho();

        System.out.println(tamanho);
    }

}
