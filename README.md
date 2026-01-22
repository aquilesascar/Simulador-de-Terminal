# 🖥️ Simulador de Terminal Linux (Shell Virtual)

Este projeto é um **Simulador de Sistema de Arquivos e Terminal** desenvolvido em Java. Ele emula o comportamento de um shell Linux, permitindo a criação de arquivos, diretórios, navegação, gerenciamento de permissões e operações avançadas como compactação e busca.

O projeto foi desenvolvido no contexto da disciplina de **Sistemas Operacionais** do **IFMG - Campus Ouro Branco** por Aquiles Ascar e Estella Moreira (2026).

---

## 🚀 Funcionalidades

O simulador implementa uma arquitetura robusta baseada em comandos, suportando uma ampla gama de operações:

### 📂 Manipulação de Diretórios e Navegação
* **`ls`**: Lista arquivos e pastas (suporta `ls -l` para detalhes de permissões, tamanho e dono).
* **`mkdir <nome>`**: Cria novos diretórios.
* **`rmdir <nome>`**: Remove diretórios (apenas se estiverem vazios).
* **`cd <caminho>`**: Navega entre diretórios.
* **`pwd`**: Exibe o caminho absoluto atual.
* **`tree`**: Exibe a estrutura de diretórios em formato de árvore recursiva.
* **`..`**: Retorna ao diretório pai.
* **`...`**: Atalho personalizado para retornar ao diretório pai.
* **`/:`**: Atalho para retornar diretamente à raiz do sistema.

### 📄 Manipulação de Arquivos
* **`touch <nome>`**: Cria um arquivo vazio.
* **`cat <nome>`**: Exibe o conteúdo de um arquivo na tela.
* **`echo <texto> > <arquivo>`**: Escreve (sobrescreve) texto em um arquivo.
* **`echo <texto> >> <arquivo>`**: Adiciona (append) texto ao final de um arquivo.
* **`rm <nome>`**: Remove arquivos do sistema.
* **`cp <origem> <destino>`**: Copia arquivos ou diretórios (recursivamente).
* **`mv <origem> <destino>`**: Move arquivos ou diretórios para outro local.
* **`rename <antigo> <novo>`**: Renomeia um arquivo ou diretório.
* **`head <arquivo> <n>`**: Exibe as primeiras `n` linhas de um arquivo.
* **`tail <arquivo> <n>`**: Exibe as últimas `n` linhas de um arquivo.
* **`wc <arquivo>`**: Conta o número de linhas, palavras e caracteres de um arquivo.
* **`diff <arq1> <arq2>`**: Compara o conteúdo de dois arquivos linha a linha e mostra as diferenças.

### 🔍 Busca e Compactação
* **`find <dir> -name <nome>`**: Busca arquivos ou diretórios recursivamente pelo nome.
* **`grep <termo> <arquivo>`**: Busca por uma string específica dentro do conteúdo de um arquivo.
* **`zip <arquivo.zip> <itens>`**: Cria um arquivo compactado virtual contendo outros arquivos.
* **`unzip <arquivo.zip>`**: Extrai o conteúdo de um arquivo zipado para o diretório atual.

### 🛡️ Permissões e Sistema
* **`chmod <modo> <arquivo>`**: Altera permissões de leitura (r), escrita (w) e execução (x) (ex: `777`, `644`).
* **`chown <dono> <arquivo>`**: Altera o proprietário do arquivo.
* **`stat <nome>`**: Exibe metadados detalhados (datas de criação/modificação, tamanho, etc.).
* **`du <diretorio>`**: Exibe o tamanho ocupado por um diretório.
* **`history`**: Exibe o histórico de comandos digitados na sessão.

---

## 🛠️ Estrutura do Projeto

O projeto utiliza **Java 22** e segue padrões de projeto como **Composite** (para a estrutura de arquivos/pastas) e uma organização focada em "Managers".

* **`Main.java`**: Ponto de entrada da aplicação. Inicia o `Terminal`.
* **`Terminal.java`**: Gerencia o loop principal, o prompt (`user@ifmg`), o usuário atual e o histórico.
* **`Entrada.java`**: Classe abstrata base para `Arquivo` e `Diretorio`. Gerencia permissões (`rwx`), datas e proprietário.
* **Managers**: A lógica dos comandos é separada em classes gerenciadoras:
    * `ArquivoManager.java`: Manipulação básica de arquivos (`touch`, `rm`, `cat`, etc.).
    * `DiretorioManager.java`: Manipulação de pastas (`mkdir`, `ls`, `tree`).
    * `NavegacaoManager.java`: Navegação (`cd`, `pwd`).
    * `OperacoesAvançadas.java`: Comandos complexos (`zip`, `diff`, `cp`).
    * `BuscaFiltragemManager.java`: Ferramentas de busca (`find`, `grep`).
* **`LinhaComando.java`**: Utiliza um `HashMap` e Expressões Lambda (`BiConsumer`) para mapear strings de comandos para suas respectivas funções nos managers.

---

## 📦 Pré-requisitos e Como Rodar

### Pré-requisitos
* **Java JDK 22**.
* **Maven** (para gerenciamento de dependências e build).

### Instalação e Execução

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/aquilesascar/Simulador-de-Terminal.git](https://github.com/aquilesascar/Simulador-de-Terminal.git)
    cd Simulador-de-Terminal
    ```

2.  **Compile e execute via Maven:**
    ```bash
    mvn clean install
    mvn exec:java -Dexec.mainClass="Main"
    ```

3.  **Ou execute diretamente pela sua IDE (IntelliJ/Eclipse):**
    * Abra o projeto como um projeto Maven.
    * Execute a classe `src/main/java/Main.java`.

---

## 🖥️ Exemplo de Uso

Ao iniciar o programa, você verá um banner e o prompt de comando:

```text
user@ifmg: /
