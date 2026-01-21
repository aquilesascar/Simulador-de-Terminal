public class DiretorioManager {
    private Terminal terminal;

    public DiretorioManager(Terminal terminal) {
        this.terminal = terminal;
    }

    public void cmdMkdir(String nome) {
        if (nome == null) {
            System.out.println("Uso: mkdir <nome>");
            return;
        }
        // Verifica se já existe
        if (terminal.getDiretorioAtual().buscarFilho(nome) != null) {
            System.out.println("Erro: O arquivo/diretório já existe.");
            return;
        }
        Diretorio novoDir = new Diretorio(nome, terminal.getDiretorioAtual());
        terminal.getDiretorioAtual().adicionarFilho(novoDir);
    }

    public void rmdir(String diretorio){
        if(diretorio == null || diretorio.equals("")){
            System.out.println(" Use:rmdir <nome>");
            return;
        }
        Entrada ent = terminal.getDiretorioAtual().buscarFilho(diretorio);
        if (ent == null || ent instanceof Arquivo) {
            System.out.println("Diretório "+ diretorio + " não encontrado.");
            return;
        }
        Diretorio entDir = (Diretorio) ent;
        if(ent.getTamanho() != 0 || entDir.getFilhos().size() > 0){
            System.out.println(diretorio + "não está vazio");
            return;
        }
        terminal.getDiretorioAtual().removerFilho(ent);
        System.out.println("Diretório removido com sucesso");
    }

    public void cmdLs() {
        for (Entrada e : terminal.getDiretorioAtual().getFilhos()) {
            String tipo = (e instanceof Diretorio) ? "[D]" : "[A]";
            System.out.println(tipo + " " + e.getNome());
        }
    }

    public void cmdTree() {
        mostrarArvore(terminal.getDiretorioAtual(), "");
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
}