import java.util.Scanner;

public class Terminal {
    private Diretorio raiz;
    private Diretorio diretorioAtual;
    private boolean executando;

    public Terminal() {
        // Inicializa o sistema com a raiz "/"
        this.raiz = new Diretorio("", null);
        this.diretorioAtual = raiz;
        this.executando = true;
    }

    public void iniciar() {
        // Limpa tela e muda cor (se o terminal suportar)
        System.out.println("\033[H\033[2J"); // Limpa console
        System.out.println("\u001B[32m");    // Muda texto para VERDE (Estilo Hacker)

        // Imprime um Banner em ASCII
        System.out.println(" ░▒▓███████▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓████████▓▒░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓████████▓▒░▒▓██████████████▓▒░ ░▒▓██████▓▒░  \n" +
                "░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                "░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░        \n" +
                " ░▒▓██████▓▒░░▒▓████████▓▒░▒▓██████▓▒░ ░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓██████▓▒░ ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒▒▓███▓▒░ \n" +
                "       ░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                "       ░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                "░▒▓███████▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓████████▓▒░▒▓████████▓▒░▒▓████████▓▒░      ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓██████▓▒░  \n" +
                "                                                                                                                              \n" +
                "                                                                                                                              ");
        System.out.println("      SISTEMA DE ARQUIVOS VIRTUAL - IFMG - SO 2026           ");
        System.out.println("_____________________________________________________________\n");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Terminal iniciado. Digite 'exit' para sair.");

        while (executando) {
            // Exibe o prompt similar ao Linux: usuario@sistema:/caminho$
            exibirPrompt();
            String linhaComando = scanner.nextLine();
            interpretarComando(linhaComando);
        }
        scanner.close();
    }

    private void exibirPrompt() {
        Entrada raiz = this.diretorioAtual;
        String caminho = raiz.getNome();
        while(raiz != null) {
            raiz = raiz.getPai();
            caminho = raiz !=null ? raiz.getNome() + "/" + caminho : caminho;
        }
        caminho = "user@ifmg: "+caminho;

        System.out.print(caminho);
    }

    private void interpretarComando(String linha) {
        String[] partes = linha.trim().split("\\s+"); // Divide por espaços
        if (partes.length == 0) return;

        String comando = partes[0];
        String argumento = partes.length > 1 ? partes[1] : null;
        String argumento2 = partes.length > 2 ? partes[2] : null;

        try {
            switch (comando) {
                case "mkdir":
                    cmdMkdir(argumento);
                    break;
                case "rmdir":
                    rmdir(argumento);
                    break;
                case "rename":
                    rename(argumento, argumento2);
                    break;
                case "rm":
                    rm(argumento);
                    break;
                case "head":
                    head(linha);
                    break;
                case "tail":
                    tail(linha);
                    break;
                case "ls":
                    cmdLs();
                    break;
                case "cd":
                    cmdCd(argumento);
                    break;
                case "...":
                    tresPontos();
                    break;
                case "touch":
                    cmdTouch(argumento);
                    break;
                case "exit":
                    executando = false;
                    break;
                case "pwd":
                    cmdPwd();
                    break;
                case "echo":
                    cmdEcho(linha);
                    break;
                case "cat":
                    cmdCat(argumento);
                    break;
                case "tree":
                    cmdTree();
                    break;

                default:
                    System.out.println("Comando não encontrado: " + comando);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void tail(String linha) {
        String [] partes = linha.trim().split("\\s+");
        String arquivo = partes.length > 1 ? partes[1] : null;
        String numero = partes.length > 2 ? partes[2] : null;
        if( arquivo == null || numero == null) {
            System.out.println("Use:  head <arquivo> <n>: ");
            return;
        }
        Entrada ent = this.diretorioAtual.buscarFilho(arquivo);
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

    private void head(String linhaComando) {
        String [] partes = linhaComando.trim().split("\\s+");
        String arquivo = partes.length > 1 ? partes[1] : null;
        String numero = partes.length > 2 ? partes[2] : null;
        if( arquivo == null || numero == null) {
            System.out.println("Use:  head <arquivo> <n>: ");
            return;
        }
        Entrada ent = this.diretorioAtual.buscarFilho(arquivo);
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

    private void tresPontos() {
        if(this.diretorioAtual.getPai() == null) {
            return;
        }
        this.diretorioAtual = this.diretorioAtual.getPai();
    }

    private void rm(String entrada) {
        if(entrada == null){
            System.out.println("Use: rm <nome>");
            return;
        }
        Entrada ent =  this.diretorioAtual.buscarFilho(entrada);
        if(ent == null){
            System.out.println(entrada + "não encontrado");
            return;
        }
        this.diretorioAtual.removerFilho(ent);
        System.out.println(entrada + " removido com sucesso");
    }

    private void cmdTree() {
        mostrarArvore(diretorioAtual, "");
    }

    //método recursivo auxiliar
    private void mostrarArvore(Entrada entrada, String prefixo) {
        System.out.println(prefixo + "|-- " + entrada.getNome());

        if (entrada instanceof Diretorio) {
            Diretorio dir = (Diretorio) entrada;
            for (Entrada filho : dir.getFilhos()) {
                //chama a si mesmo com um prefixo maior
                mostrarArvore(filho, prefixo + "    ");
            }
        }
    }

    private void cmdCat(String nome) {
        Entrada alvo = diretorioAtual.buscarFilho(nome);
        if (alvo instanceof Arquivo) {
            System.out.println(((Arquivo) alvo).lerConteudo());
        } else {
            System.out.println("Arquivo não encontrado ou é um diretório.");
        }
    }

    private Arquivo buscarCriarArquivo(String nomeArquivo) {
        Entrada ent =  this.diretorioAtual.buscarFilho(nomeArquivo);
        if (ent instanceof Arquivo && ent != null) {
            return (Arquivo) ent;
        }
        Arquivo arquivo = new Arquivo(nomeArquivo, this.diretorioAtual);
        this.diretorioAtual.adicionarFilho(arquivo);
        System.out.println("Arquivo criado com sucesso.");
        return arquivo;
    }

    private void cmdEcho(String linhaComando) {
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

    private void cmdPwd() {
        // Começa do diretório atual e vai subindo até a raiz
        Diretorio temp = diretorioAtual;
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

    private void cmdMkdir(String nome) {
        if (nome == null) {
            System.out.println("Uso: mkdir <nome>");
            return;
        }
        // Verifica se já existe
        if (diretorioAtual.buscarFilho(nome) != null) {
            System.out.println("Erro: O arquivo/diretório já existe.");
            return;
        }
        Diretorio novoDir = new Diretorio(nome, diretorioAtual);
        diretorioAtual.adicionarFilho(novoDir);
    }
    private void rmdir(String diretorio){

        if(diretorio.equals("")){
            System.out.println(" Use:rmdir <nome>");
            return;
        }
        Entrada ent = diretorioAtual.buscarFilho(diretorio);
        if (ent == null || ent instanceof Arquivo) {
            System.out.println("Diretório "+ diretorio + " não encontrado.");
            return;
        }
        Diretorio entDir = (Diretorio) ent;
        if(ent.getTamanho() != 0 || entDir.getFilhos().size() > 0){
            System.out.println(diretorio + "não está vazio");
            return;
        }
        diretorioAtual.removerFilho(ent);
        System.out.println("Diretório removido com sucesso");
    }

    private void rename(String nomeAntigo, String novoNome) {
        if (nomeAntigo == null || novoNome == null) {
            System.out.println("use: rename <nome antigo> <nome novo nome>");
            return;
        }
        Entrada ent = diretorioAtual.buscarFilho(nomeAntigo);
        if (ent == null) {
            System.out.println("Diretório/Arquivo "+ nomeAntigo+ " não encontrado.");
            return;
        }
        Entrada novaEnt = diretorioAtual.buscarFilho(novoNome);
        if (novaEnt != null) {
            System.out.println(novoNome + " já existe.");
            return;
        }
        ent.setNome(novoNome);
        System.out.println("Nome modificado com sucesso");
    }

    private void cmdLs() {
        for (Entrada e : diretorioAtual.getFilhos()) {
            String tipo = (e instanceof Diretorio) ? "[D]" : "[A]";
            System.out.println(tipo + " " + e.getNome());
        }
    }

    private void cmdTouch(String nome) {
        if (nome == null) return;
        Arquivo novoArq = new Arquivo(nome, diretorioAtual);
        diretorioAtual.adicionarFilho(novoArq);
    }

    private void cmdCd(String caminho) {
        //tratamento para voltar à raiz ou home.
        if (caminho == null || caminho.equals("~") || caminho.equals("/")) { // trata a "/"
            diretorioAtual = raiz;
            return;
        }

        if (caminho.equals("..")) {
            if (diretorioAtual.getPai() != null) {
                diretorioAtual = diretorioAtual.getPai();
            }
            return;
        }

        //tenta encontrar o diretório filho
        Entrada alvo = diretorioAtual.buscarFilho(caminho);

        if (alvo == null) {
            System.out.println("Diretório não encontrado: " + caminho);
        } else if (alvo instanceof Diretorio) {
            diretorioAtual = (Diretorio) alvo;
        } else {
            System.out.println("Erro: '" + caminho + "' é um arquivo, não um diretório.");
        }
    }
}