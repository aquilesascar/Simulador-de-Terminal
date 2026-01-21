import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class CommandHandler {
    private Terminal terminal;
    private FileSystemManager fileSystemManager;
    private DirectoryManager directoryManager;
    private NavigationManager navigationManager;
    private Map<String, BiConsumer<String[], String>> commandMap;

    public CommandHandler(Terminal terminal) {
        this.terminal = terminal;
        this.fileSystemManager = new FileSystemManager(terminal);
        this.directoryManager = new DirectoryManager(terminal);
        this.navigationManager = new NavigationManager(terminal);
        initializeCommands();
    }

    private void initializeCommands() {
        commandMap = new HashMap<>();
        
        // Comandos de diretório
        commandMap.put("mkdir", (args, linha) -> directoryManager.cmdMkdir(args.length > 1 ? args[1] : null));
        commandMap.put("rmdir", (args, linha) -> directoryManager.rmdir(args.length > 1 ? args[1] : null));
        commandMap.put("ls", (args, linha) -> directoryManager.cmdLs());
        commandMap.put("tree", (args, linha) -> directoryManager.cmdTree());
        
        // Comandos de arquivo
        commandMap.put("touch", (args, linha) -> fileSystemManager.cmdTouch(args.length > 1 ? args[1] : null));
        commandMap.put("rm", (args, linha) -> fileSystemManager.rm(args.length > 1 ? args[1] : null));
        commandMap.put("cat", (args, linha) -> fileSystemManager.cmdCat(args.length > 1 ? args[1] : null));
        commandMap.put("head", (args, linha) -> fileSystemManager.head(linha));
        commandMap.put("tail", (args, linha) -> fileSystemManager.tail(linha));
        commandMap.put("echo", (args, linha) -> fileSystemManager.cmdEcho(linha));
        
        // Comandos de navegação
        commandMap.put("cd", (args, linha) -> navigationManager.cmdCd(args.length > 1 ? args[1] : null));
        commandMap.put("pwd", (args, linha) -> navigationManager.cmdPwd());
        commandMap.put("..", (args, linha) -> navigationManager.tresPontos());
        commandMap.put("...", (args, linha) -> navigationManager.tresPontos());
        
        // Comandos de sistema
        commandMap.put("rename", (args, linha) -> {
            if (args.length > 2) {
                fileSystemManager.rename(args[1], args[2]);
            } else {
                System.out.println("use: rename <nome antigo> <nome novo>");
            }
        });
        
        commandMap.put("exit", (args, linha) -> terminal.setExecutando(false));
    }

    public void interpretarComando(String linhaComando) {
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