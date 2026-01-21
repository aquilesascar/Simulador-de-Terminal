import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class LinhaComando {
    private Terminal terminal;
    private ArquivoManager arquivoManager;
    private DiretorioManager diretorioManager;
    private NavegacaoManager navegacaoManager;
    private BuscaFiltragemManager buscaFiltragemManager;
    private InfoArquivosDiretorios infoArquivosDiretorios;
    private OperacoesAvançadas operacoesAvançadas;
    private Map<String, BiConsumer<String[], String>> commandMap;


    public LinhaComando(Terminal terminal) {
        this.terminal = terminal;
        this.arquivoManager = new ArquivoManager(terminal);
        this.diretorioManager = new DiretorioManager(terminal);
        this.navegacaoManager = new NavegacaoManager(terminal);
        this.buscaFiltragemManager = new BuscaFiltragemManager(terminal);
        this.infoArquivosDiretorios = new InfoArquivosDiretorios(terminal);
        this.operacoesAvançadas = new OperacoesAvançadas(terminal);
        initializeCommands();
    }

    private void initializeCommands() {
        commandMap = new HashMap<>();
        
        // Comandos de diretório

        commandMap.put("mkdir", (args, linha) -> diretorioManager.cmdMkdir(args.length > 1 ? args[1] : null));
        commandMap.put("rmdir", (args, linha) -> diretorioManager.rmdir(args.length > 1 ? args[1] : null));
        commandMap.put("ls", (args, linha) -> diretorioManager.cmdLs());
        commandMap.put("tree", (args, linha) -> diretorioManager.cmdTree());
        
        // Comandos de arquivo
        commandMap.put("touch", (args, linha) -> arquivoManager.cmdTouch(args.length > 1 ? args[1] : null));
        commandMap.put("rm", (args, linha) -> arquivoManager.rm(args.length > 1 ? args[1] : null));
        commandMap.put("cat", (args, linha) -> arquivoManager.cmdCat(args.length > 1 ? args[1] : null));
        commandMap.put("head", (args, linha) -> arquivoManager.head(linha));
        commandMap.put("tail", (args, linha) -> arquivoManager.tail(linha));
        commandMap.put("echo", (args, linha) -> arquivoManager.cmdEcho(linha));
        commandMap.put("wc", (args, linha)-> arquivoManager.cmdWc(args.length > 1 ? args[1] : null));
        
        // Comandos de navegação
        commandMap.put("cd", (args, linha) -> navegacaoManager.cmdCd(args.length > 1 ? args[1] : null));
        commandMap.put("pwd", (args, linha) -> navegacaoManager.cmdPwd());
        commandMap.put("..", (args, linha) -> navegacaoManager.tresPontos());
        commandMap.put("...", (args, linha) -> navegacaoManager.tresPontos());
        commandMap.put("/:", (args, linha) -> navegacaoManager.cmdDiretorioraiz());

        //Comando de busca e filtragem
         commandMap.put("find",(args, linha) -> buscaFiltragemManager.cmdFind(linha));
         commandMap.put("grep",(args, linha) -> buscaFiltragemManager.cmdGrep(linha));

         //Comandos de informações
        commandMap.put("stat",(args, linha) -> infoArquivosDiretorios.cmdStat(args.length > 1 ? args[1] : null));
        commandMap.put("du",(args, linha) -> infoArquivosDiretorios.cmdStat(args.length > 1 ? args[1] : null));

        //Operações avançadas
        commandMap.put("cp", (args,linha) -> operacoesAvançadas.cmdCp(linha));
        commandMap.put("mv", (args,linha) -> operacoesAvançadas.cmdMv(linha));
        commandMap.put("diff", (args,linha) -> operacoesAvançadas.cmdDiff(linha));
        commandMap.put("zip", (args,linha) -> operacoesAvançadas.cmdZip(linha));
        commandMap.put("unzip", (args,linha) -> operacoesAvançadas.cmdUnzip(linha));

        //Extras

        commandMap.put("history", (args, linha) -> arquivoManager.cmdHistory());

        //Permissoes
        commandMap.put("chmod", (args, linha) -> arquivoManager.cmdChmod(linha));
        commandMap.put("chown", (args, linha) -> arquivoManager.cmdChown(linha));
        commandMap.put("ls", (args, linha) -> {
            if (args.length > 1 && args[1].equals("-l")) {
                diretorioManager.cmdLsLongo();
            } else {
                diretorioManager.cmdLs();
            }
        });





        // Comandos de sistema
        commandMap.put("rename", (args, linha) -> {
            if (args.length > 2) {
                arquivoManager.rename(args[1], args[2]);
            } else {
                System.out.println("use: rename <nome antigo> <nome novo>");
            }
        });
        
        commandMap.put("exit", (args, linha) -> terminal.setExecutando(false));
    }

    public void interpretarComando(String linhaComando) {
        terminal.adicionarHistorico(linhaComando);

        String[] partes = linhaComando.trim().split("\\s+");
        if (partes.length == 0) return;

        String comando = partes[0];

        try {
            BiConsumer<String[], String> handler = commandMap.get(comando);
            if (handler != null) {
                handler.accept(partes, linhaComando);
            } else {
                System.out.println("Comando não encontrado: " + comando);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}