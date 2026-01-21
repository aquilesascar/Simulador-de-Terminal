public class OperacoesAvançadas {
    Terminal terminal;

    public OperacoesAvançadas(Terminal terminal) {
        this.terminal = terminal;
    }
    public void cmdCp(String linhaComando) {
        String[] partes = linhaComando.trim().split("\\s+");

        if (partes.length < 3) {
            System.out.println("Use: cp <origem> <destino>");
            return;
        }

        String origemNome = partes[1];
        String destinoNome = partes[2];

        Diretorio atual = terminal.getDiretorioAtual();

        Entrada origem = atual.buscarFilho(origemNome);
        Entrada destino = atual.buscarFilho(destinoNome);

        if (origem == null || !(destino instanceof Diretorio)) {
            System.out.println("Origem ou destino inválido");
            return;
        }

        Entrada copia = MetodosAuxiliares.copiarEntrada(origem, (Diretorio) destino);
        ((Diretorio) destino).adicionarFilho(copia);
    }
    public void cmdMv(String linhaComando) {
        String[] partes = linhaComando.trim().split("\\s+");

        if (partes.length < 3) {
            System.out.println("Use: mv <origem> <destino>");
            return;
        }

        String origemNome = partes[1];
        String destinoNome = partes[2];

        Diretorio atual = terminal.getDiretorioAtual();

        Entrada origem = atual.buscarFilho(origemNome);
        Entrada destino = atual.buscarFilho(destinoNome);

        if (origem == null || !(destino instanceof Diretorio)) {
            System.out.println("Origem ou destino inválido");
            return;
        }

        atual.removerFilho(origem);
        origem.pai = (Diretorio) destino;
        ((Diretorio) destino).adicionarFilho(origem);
    }
    public void cmdDiff(String linhaComando) {
        String[] partes = linhaComando.trim().split("\\s+");

        if (partes.length < 3) {
            System.out.println("Use: diff <arquivo1> <arquivo2>");
            return;
        }

        Diretorio atual = terminal.getDiretorioAtual();

        Entrada e1 = atual.buscarFilho(partes[1]);
        Entrada e2 = atual.buscarFilho(partes[2]);

        if (!(e1 instanceof Arquivo) || !(e2 instanceof Arquivo)) {
            System.out.println("diff só funciona com arquivos");
            return;
        }

        String[] l1 = ((Arquivo) e1).lerConteudo().split("\\r?\\n");
        String[] l2 = ((Arquivo) e2).lerConteudo().split("\\r?\\n");

        int max = Math.max(l1.length, l2.length);

        for (int i = 0; i < max; i++) {
            String s1 = i < l1.length ? l1[i] : "";
            String s2 = i < l2.length ? l2[i] : "";

            if (!s1.equals(s2)) {
                System.out.println("Linha " + (i + 1));
                System.out.println("< " + s1);
                System.out.println("> " + s2);
            }
        }
    }
    public void cmdZip(String linhaComando) {
        String[] partes = linhaComando.trim().split("\\s+");

        if (partes.length < 3) {
            System.out.println("Use: zip <arquivo.zip> <itens>");
            return;
        }

        Diretorio atual = terminal.getDiretorioAtual();
        ArquivoZip zip = new ArquivoZip(partes[1], atual, this.terminal.getUsuarioAtual());

        for (int i = 2; i < partes.length; i++) {
            Entrada e = atual.buscarFilho(partes[i]);
            if (e != null) {
                zip.adicionar(e);
            }
        }

        atual.adicionarFilho(zip);
    }
    public void cmdUnzip(String linhaComando) {
        String[] partes = linhaComando.trim().split("\\s+");

        if (partes.length < 2) {
            System.out.println("Use: unzip <arquivo.zip>");
            return;
        }

        Diretorio atual = terminal.getDiretorioAtual();
        Entrada e = atual.buscarFilho(partes[1]);

        if (!(e instanceof ArquivoZip)) {
            System.out.println("Arquivo zip inválido");
            return;
        }

        ArquivoZip zip = (ArquivoZip) e;

        for (Entrada ent : zip.getConteudoZipado()) {
            Entrada copia = MetodosAuxiliares.copiarEntrada(ent, atual);
            atual.adicionarFilho(copia);
        }
    }





}
