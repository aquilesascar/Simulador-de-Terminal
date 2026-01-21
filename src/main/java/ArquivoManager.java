public class ArquivoManager {
    private Terminal terminal;

    public ArquivoManager(Terminal terminal) {
        this.terminal = terminal;
    }

    public void cmdTouch(String nome) {
        if (nome == null) return;
        Arquivo novoArq = new Arquivo(nome, terminal.getDiretorioAtual());
        terminal.getDiretorioAtual().adicionarFilho(novoArq);
    }
    public void cmdWc(String arquivo){
        if (arquivo == null) return;
        Entrada ent =  terminal.getDiretorioAtual().buscarFilho(arquivo);
        if (ent == null) {
            System.out.println("Arquivo não encontrado");
            return;
        }
        if(ent instanceof Arquivo){

            Arquivo arq = (Arquivo) ent;
            String conteudo = arq.lerConteudo().toString();
            String []linhas = conteudo.split("\\r?\\n");
            int numLinhas = linhas.length;
            String [] palavras =  conteudo.split("\\s+");
            int numPalavras = palavras.length;
            int caracteres =  conteudo.length();

            System.out.println("Numero de linhas: " + numLinhas);
            System.out.println("Numero de palavras: " + numPalavras);
            System.out.println("Numero de caracteres: " + caracteres);

        }

    }

    public void rm(String entrada) {
        if(entrada == null){
            System.out.println("Use: rm <nome>");
            return;
        }
        Entrada ent =  terminal.getDiretorioAtual().buscarFilho(entrada);
        if(ent == null){
            System.out.println(entrada + "não encontrado");
            return;
        }
        terminal.getDiretorioAtual().removerFilho(ent);
        System.out.println(entrada + " removido com sucesso");
    }

    public void cmdCat(String nome) {
        Entrada alvo = terminal.getDiretorioAtual().buscarFilho(nome);
        if (alvo instanceof Arquivo) {
            System.out.println(((Arquivo) alvo).lerConteudo());
        } else {
            System.out.println("Arquivo não encontrado ou é um diretório.");
        }
    }

    public void head(String linhaComando) {
        String [] partes = linhaComando.trim().split("\\s+");
        String arquivo = partes.length > 1 ? partes[1] : null;
        String numero = partes.length > 2 ? partes[2] : null;
        if( arquivo == null || numero == null) {
            System.out.println("Use:  head <arquivo> <n>: ");
            return;
        }
        Entrada ent = terminal.getDiretorioAtual().buscarFilho(arquivo);
        if(ent == null) {
            System.out.println("Arquivo não encontrado: " + arquivo);
            return;
        }
        if(ent instanceof Arquivo) {
            try{
                int num = Integer.parseInt(numero);
                exibirPriLinhas(num, (Arquivo) ent);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println(arquivo + "não é um arquivo");
            return;
        }
    }

    private void exibirPriLinhas(int num, Arquivo arquivo) {
        String [] linhas = arquivo.lerConteudo().split("\\r?\\n");
        if(num>linhas.length) {
            num = linhas.length;
        }
        for(int i = 0; i < num; i++) {
            System.out.println(linhas[i]);
        }
    }

    public void tail(String linha) {
        String [] partes = linha.trim().split("\\s+");
        String arquivo = partes.length > 1 ? partes[1] : null;
        String numero = partes.length > 2 ? partes[2] : null;
        if( arquivo == null || numero == null) {
            System.out.println("Use:  head <arquivo> <n>: ");
            return;
        }
        Entrada ent = terminal.getDiretorioAtual().buscarFilho(arquivo);
        if(ent == null) {
            System.out.println("Arquivo não encontrado: " + arquivo);
            return;
        }
        if(ent instanceof Arquivo) {
            try{
                int num = Integer.parseInt(numero);
                exibirUltiLinhas(num, (Arquivo) ent);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println(arquivo + "não é um arquivo");
            return;
        }
    }

    private void exibirUltiLinhas(int num, Arquivo arquivo) {
        String [] linhas = arquivo.lerConteudo().split("\\r?\\n");
        if(num>linhas.length) {
            System.out.println(num +" maior que o número de linhas do arquivo");
            return;
        }
        int inicio = linhas.length - num;
        for(int i = inicio; i < linhas.length; i++) {
            System.out.println(linhas[i]);
        }
    }

    public void cmdEcho(String linhaComando) {
        linhaComando = linhaComando.replaceFirst("^echo\\s*", "");
        String operador = "";
        String [] partes = new String[0];
        if(linhaComando.contains(">>")){
            operador = ">>";
             partes = linhaComando.trim().split(">>");
        }else if(linhaComando.contains(">")){
            operador = ">";
             partes = linhaComando.trim().split(">");
        }else{
            System.out.println("Use: echo <texto> >/>> <arquivo>");
            return;
        }
        String texto = partes[0].trim();

        String nomeArquivo = partes.length > 1 ? partes[1].trim() : null;
        Arquivo arquivo;
        if (texto.isEmpty() || nomeArquivo == null || nomeArquivo.isEmpty()) {
            System.out.println("Use: echo <texto> >/>> <arquivo>");
            return;
        }

        switch (operador) {
            case ">>":
                arquivo = buscarCriarArquivo(nomeArquivo);
                arquivo.escreverConteudo(texto);
                break;
            case ">":
                arquivo = buscarCriarArquivo(nomeArquivo);
                arquivo.setConteudo(texto);
                break;
        }
    }

    private Arquivo buscarCriarArquivo(String nomeArquivo) {
        Entrada ent =  terminal.getDiretorioAtual().buscarFilho(nomeArquivo);
        if (ent instanceof Arquivo && ent != null) {
            return (Arquivo) ent;
        }
        Arquivo arquivo = new Arquivo(nomeArquivo, terminal.getDiretorioAtual());
        terminal.getDiretorioAtual().adicionarFilho(arquivo);
        System.out.println("Arquivo criado com sucesso.");
        return arquivo;
    }

    public void rename(String nomeAntigo, String novoNome) {
        if (nomeAntigo == null || novoNome == null) {
            System.out.println("use: rename <nome antigo> <nome novo nome>");
            return;
        }
        Entrada ent = terminal.getDiretorioAtual().buscarFilho(nomeAntigo);
        if (ent == null) {
            System.out.println("Diretório/Arquivo "+ nomeAntigo+ " não encontrado.");
            return;
        }
        Entrada novaEnt = terminal.getDiretorioAtual().buscarFilho(novoNome);
        if (novaEnt != null) {
            System.out.println(novoNome + " já existe.");
            return;
        }
        ent.setNome(novoNome);
        System.out.println("Nome modificado com sucesso");
    }
}