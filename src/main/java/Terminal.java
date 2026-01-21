import java.util.Scanner;

public class Terminal {
    private Diretorio raiz;
    private Diretorio diretorioAtual;
    private boolean executando;
    private LinhaComando linhaComando;

    public Terminal() {
        // Inicializa o sistema com a raiz "/"
        this.raiz = new Diretorio("", null);
        this.diretorioAtual = raiz;
        this.executando = true;
        this.linhaComando = new LinhaComando(this);
    }

    public void iniciar() {
        // Limpa tela e muda cor (se o terminal suportar)
        System.out.println("\033[H\033[2J"); // Limpa console
        System.out.println("\u001B[32m");    // Muda texto para VERDE (Estilo Hacker)

        // Imprime um Banner em ASCII
        System.out.println(" ░▒▓███████▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓████████▓▒░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓████████▓▒░▒▓██████████████▓▒░ ░▒▓██████▓▒░  \n" +
                "░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓█▓▒极      ░▒▓█▓▒░░▒▓█▓▒极░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                "░▒▓█▓▒░      ░▒▓█▓▒极░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒极░░▒▓█▓▒极░░▒▓█▓▒░▒▓█▓▒░        \n" +
                " ░▒▓██████▓▒░░▒▓████████▓▒░▒▓██████▓▒░ ░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓██████▓▒░ ░▒▓█▓▒极░░▒▓█▓▒极░░▒▓█▓▒░▒▓█▓▒▒▓███▓▒░ \n" +
                "       ░▒▓█▓▒░▒▓█▓▒极░░▒▓█▓▒░▒▓极█▓▒░      ░▒▓█▓▒░      ░▒▓█极▓▒░             ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒极░░▒▓█▓▒极░░▒▓█▓▒░▒▓█▓▒极░░▒▓█▓▒░ \n" +
                "       ░▒▓█▓▒░▒▓█▓▒极░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░             ░▒▓█▓▒░▒▓█▓▒极      ░▒▓█▓▒极░░▒▓█▓▒极░░▒▓█极▓▒░▒▓█▓▒极░░▒▓█▓▒░ \n" +
                "░▒▓███████▓▒极░░▒▓█▓▒极░░▒▓█▓▒░▒▓████████▓▒░▒▓████████▓▒░▒▓████████▓▒极      ░▒▓█▓▒░▒极▓█▓▒极      ░▒▓█▓▒极░░▒▓█▓▒极░░▒▓█▓▒极░░▒▓██████▓▒极  \n" +
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
            this.linhaComando.interpretarComando(linhaComando);
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
        caminho = "user@ifmg: "+ caminho;

        System.out.print(caminho);
    }

    // Getters e Setters
    public Diretorio getRaiz() {
        return raiz;
    }

    public Diretorio getDiretorioAtual() {
        return diretorioAtual;
    }

    public void setDiretorioAtual(Diretorio diretorioAtual) {
        this.diretorioAtual = diretorioAtual;
    }

    public boolean isExecutando() {
        return executando;
    }

    public void setExecutando(boolean executando) {
        this.executando = executando;
    }
}