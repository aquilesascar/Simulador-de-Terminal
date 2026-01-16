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
                // Adicionar outros cases aqui...
                default:
                    System.out.println("Comando não encontrado: " + comando);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // --- Implementação dos Comandos ---

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
        // Tratamento para voltar à raiz ou home
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