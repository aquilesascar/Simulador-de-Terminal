import java.util.Scanner;

public class Terminal {
    private Diretorio raiz;
    private Diretorio diretorioAtual;
    private boolean executando;

    public Terminal() {
        // Inicializa o sistema com a raiz "/"
        this.raiz = new Diretorio("/", null);
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
        // Dica: Você precisará montar o caminho completo recursivamente para o comando 'pwd'
        System.out.print("user@ifmg:" + diretorioAtual.getNome() + "$ ");
    }

    private void interpretarComando(String linha) {
        String[] partes = linha.trim().split("\\s+"); // Divide por espaços
        if (partes.length == 0) return;

        String comando = partes[0];
        String argumento = partes.length > 1 ? partes[1] : null;

        try {
            switch (comando) {
                case "mkdir":
                    cmdMkdir(argumento);
                    break;
                case "rmdir":
                    rmdir(argumento);

                case "ls":
                    cmdLs();
                    break;
                case "cd":
                    cmdCd(argumento);
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
                    cmdEcho(argumento);
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



    private void cmdTree() {
        mostrarArvore(diretorioAtual, "");
    }

    // Método recursivo auxiliar
    private void mostrarArvore(Entrada entrada, String prefixo) {
        System.out.println(prefixo + "|-- " + entrada.getNome());

        if (entrada instanceof Diretorio) {
            Diretorio dir = (Diretorio) entrada;
            for (Entrada filho : dir.getFilhos()) {
                // Chama a si mesmo com um prefixo maior
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

    private void cmdEcho(String linhaComando) {

        boolean append = linhaComando.contains(">>");
        String operador = append ? ">>" : ">";

        // 2. Quebrar a string no operador
        String[] partes = linhaComando.split(operador);
        if (partes.length < 2) {
            System.out.println("Erro. Uso: echo <texto> > <arquivo>");
            return;
        }

        // 3. Limpar o texto (remover "echo" e aspas extras)
        String texto = partes[0].replaceFirst("echo", "").trim();
        if (texto.startsWith("\"") && texto.endsWith("\"")) {
            texto = texto.substring(1, texto.length() - 1);
        }

        String nomeArquivo = partes[1].trim();

        // 4. Buscar ou criar o arquivo
        Entrada alvo = diretorioAtual.buscarFilho(nomeArquivo);
        Arquivo arquivo;

        if (alvo == null) {
            // Se não existe, cria um novo
            arquivo = new Arquivo(nomeArquivo, diretorioAtual);
            diretorioAtual.adicionarFilho(arquivo);
        } else if (alvo instanceof Arquivo) {
            arquivo = (Arquivo) alvo;
        } else {
            System.out.println("Erro: " + nomeArquivo + " é um diretório.");
            return;
        }

        // 5. Escrever no arquivo (método que criamos na classe Arquivo)
        arquivo.escreverConteudo(texto, append);
        if (append) arquivo.escreverConteudo("\n", true); // Quebra de linha opcional
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
        if (ent == null) {
            System.out.println("Diretório "+ diretorio + " não encontrado.");
            return;
        }
        diretorioAtual.removerFilho(ent);
        System.out.println("Diretório removido com sucesso");


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
        // Tratamento para voltar à raiz ou home.
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

        // Tenta encontrar o diretório filho
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