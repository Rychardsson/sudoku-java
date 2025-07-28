package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;
import br.com.dio.util.SudokuGenerator;
import br.com.dio.util.SudokuGenerator.Difficulty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;
    private final static SudokuGenerator generator = new SudokuGenerator();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("    BEM-VINDO AO SUDOKU JAVA!");
        System.out.println("=========================================");
        System.out.println("Desenvolvido para consolidar conhecimentos em POO");
        System.out.println();
        
        // Se argumentos foram passados, usa o método antigo
        if (args.length > 0) {
            final var positions = Stream.of(args)
                    .collect(toMap(
                            k -> k.split(";")[0],
                            v -> v.split(";")[1]
                    ));
            runGameWithArguments(positions);
        } else {
            runInteractiveGame();
        }
    }
    
    private static void runInteractiveGame() {
        var option = -1;
        while (true) {
            displayMainMenu();
            
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Consome a quebra de linha
                
                switch (option) {
                    case 1 -> startNewGame();
                    case 2 -> inputNumber();
                    case 3 -> removeNumber();
                    case 4 -> showCurrentGame();
                    case 5 -> showGameStatus();
                    case 6 -> getHint();
                    case 7 -> clearGame();
                    case 8 -> finishGame();
                    case 9 -> showStatistics();
                    case 0 -> {
                        System.out.println("[!] Obrigado por jogar! Ate logo!");
                        System.exit(0);
                    }
                    default -> System.out.println("[X] Opcao invalida! Selecione uma opcao do menu.");
                }
            } catch (Exception e) {
                System.out.println("[X] Entrada invalida! Digite apenas numeros.");
                scanner.nextLine(); // Limpa o buffer
            }
            
            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MENU PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1  - Iniciar um novo jogo");
        System.out.println("2  - Colocar um numero");
        System.out.println("3  - Remover um numero");
        System.out.println("4  - Visualizar jogo atual");
        System.out.println("5  - Verificar status do jogo");
        System.out.println("6  - Pedir uma dica");
        System.out.println("7  - Limpar jogo");
        System.out.println("8  - Finalizar jogo");
        System.out.println("9  - Ver estatisticas");
        System.out.println("0  - Sair");
        System.out.println("=".repeat(50));
        System.out.print("Escolha uma opcao: ");
    }
    
    private static void startNewGame() {
        if (nonNull(board)) {
            System.out.print("[!] Ja existe um jogo em andamento. Deseja comecar um novo? (s/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("s") && !response.equals("sim")) {
                return;
            }
        }
        
        System.out.println("\nEscolha o nivel de dificuldade:");
        System.out.println("1 - " + Difficulty.EASY.getLabel() + " (" + Difficulty.EASY.getClues() + " pistas)");
        System.out.println("2 - " + Difficulty.MEDIUM.getLabel() + " (" + Difficulty.MEDIUM.getClues() + " pistas)");
        System.out.println("3 - " + Difficulty.HARD.getLabel() + " (" + Difficulty.HARD.getClues() + " pistas)");
        System.out.println("4 - " + Difficulty.EXPERT.getLabel() + " (" + Difficulty.EXPERT.getClues() + " pistas)");
        
        int difficultyChoice = runUntilGetValidNumber(1, 4);
        Difficulty selectedDifficulty = switch (difficultyChoice) {
            case 1 -> Difficulty.EASY;
            case 2 -> Difficulty.MEDIUM;
            case 3 -> Difficulty.HARD;
            case 4 -> Difficulty.EXPERT;
            default -> Difficulty.MEDIUM;
        };
        
        System.out.println("Gerando puzzle de nivel " + selectedDifficulty.getLabel() + "...");
        
        List<List<Space>> spaces = generator.generatePuzzle(selectedDifficulty);
        board = new Board(spaces);
        
        System.out.println("[OK] Novo jogo iniciado com sucesso!");
        System.out.println("Nivel: " + selectedDifficulty.getLabel());
        showCurrentGame();
    }
    
    private static void getHint() {
        if (isNull(board)) {
            System.out.println("[X] O jogo ainda nao foi iniciado!");
            return;
        }
        
        System.out.println("\nSistema de Dicas");
        System.out.println("Informe a posicao onde deseja uma dica:");
        
        System.out.print("Coluna (0-8): ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Linha (0-8): ");
        var row = runUntilGetValidNumber(0, 8);
        
        Integer hint = board.getHint(row, col);
        
        if (hint == null) {
            if (board.getSpaces().get(row).get(col).isFixed()) {
                System.out.println("[!] Esta posicao ja esta preenchida e e fixa!");
            } else if (nonNull(board.getSpaces().get(row).get(col).getActual())) {
                System.out.println("[!] Esta posicao ja esta preenchida!");
            } else {
                System.out.println("[X] Nao foi possivel encontrar uma dica para esta posicao.");
            }
        } else {
            System.out.println("[DICA] O numero " + hint + " pode ser colocado na posicao [" + col + "," + row + "]");
        }
    }
    
    private static void showStatistics() {
        if (isNull(board)) {
            System.out.println("[X] O jogo ainda nao foi iniciado!");
            return;
        }
        
        System.out.println("\nESTATISTICAS DO JOGO");
        System.out.println("=".repeat(30));
        System.out.println("Celulas preenchidas: " + board.getFilledCells() + "/81");
        System.out.println("Numeros corretos: " + board.getCorrectNumbers());
        System.out.println("Progresso: " + String.format("%.1f", (board.getFilledCells() / 81.0 * 100)) + "%");
        System.out.println("Status: " + board.getStatus().getLabel());
        
        if (board.hasErrors()) {
            System.out.println("[X] Existem erros no tabuleiro!");
        } else {
            System.out.println("[OK] Nenhum erro detectado!");
        }
    }

    
    private static void runGameWithArguments(final Map<String, String> positions) {
        var option = -1;
        while (true){
            System.out.println("Selecione uma das opcoes a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo numero");
            System.out.println("3 - Remover um numero");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opcao invalida, selecione uma das opcoes do menu");
            }
        }
    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)){
            System.out.println("O jogo ja foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo esta pronto para comecar");
    }


    private static void inputNumber() {
        if (isNull(board)){
            System.out.println("[X] O jogo ainda nao foi iniciado!");
            return;
        }

        System.out.println("\nInserir Numero");
        System.out.print("Informe a coluna (0-8): ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Informe a linha (0-8): ");
        var row = runUntilGetValidNumber(0, 8);
        System.out.print("Informe o numero (1-9) para a posicao [" + col + "," + row + "]: ");
        var value = runUntilGetValidNumber(1, 9);
        
        if (!board.changeValue(col, row, value)){
            System.out.println("[X] A posicao [" + col + "," + row + "] tem um valor fixo!");
        } else {
            if (board.isValidMove(col, row, value)) {
                System.out.println("[OK] Numero inserido com sucesso!");
            } else {
                System.out.println("[!] Numero inserido, mas pode haver conflito com as regras do Sudoku!");
            }
        }
    }

    private static void removeNumber() {
        if (isNull(board)){
            System.out.println("[X] O jogo ainda nao foi iniciado!");
            return;
        }

        System.out.println("\nRemover Numero");
        System.out.print("Informe a coluna (0-8): ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Informe a linha (0-8): ");
        var row = runUntilGetValidNumber(0, 8);
        
        if (!board.clearValue(col, row)){
            System.out.println("[X] A posicao [" + col + "," + row + "] tem um valor fixo!");
        } else {
            System.out.println("[OK] Numero removido com sucesso!");
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)){
            System.out.println("[X] O jogo ainda nao foi iniciado!");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col: board.getSpaces()){
                Space space = col.get(i);
                String displayValue;
                
                if (isNull(space.getActual())) {
                    displayValue = "  "; // Celula vazia
                } else if (space.isFixed()) {
                    displayValue = "*" + space.getActual(); // Numeros fixos com asterisco
                } else {
                    displayValue = " " + space.getActual(); // Numeros inseridos pelo usuario
                }
                
                args[argPos++] = displayValue;
            }
        }
        
        System.out.println("\nSEU JOGO ATUAL:");
        System.out.println("(*) Numeros fixos, outros voce pode modificar");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if (isNull(board)){
            System.out.println("[X] O jogo ainda nao foi iniciado!");
            return;
        }

        System.out.println("\nSTATUS DO JOGO");
        System.out.println("Status atual: " + board.getStatus().getLabel());
        
        if(board.hasErrors()){
            System.out.println("[X] O jogo contem erros nas regras do Sudoku!");
        } else {
            System.out.println("[OK] O jogo nao contem erros!");
        }
        
        System.out.println("Progresso: " + board.getFilledCells() + "/81 celulas preenchidas");
    }

    private static void clearGame() {
        if (isNull(board)){
            System.out.println("[X] O jogo ainda nao foi iniciado!");
            return;
        }

        System.out.print("[!] Tem certeza que deseja limpar seu jogo e perder todo seu progresso? (sim/nao): ");
        var confirm = scanner.nextLine().trim().toLowerCase();
        
        while (!confirm.equals("sim") && !confirm.equals("nao") && !confirm.equals("s") && !confirm.equals("n")){
            System.out.print("Por favor, informe 'sim' ou 'nao': ");
            confirm = scanner.nextLine().trim().toLowerCase();
        }

        if(confirm.equals("sim") || confirm.equals("s")){
            board.reset();
            System.out.println("[OK] Jogo limpo com sucesso!");
        } else {
            System.out.println("Operacao cancelada.");
        }
    }

    private static void finishGame() {
        if (isNull(board)){
            System.out.println("[X] O jogo ainda nao foi iniciado!");
            return;
        }

        if (board.gameIsFinished()){
            System.out.println("\n*** PARABENS! VOCE CONCLUIU O SUDOKU! ***");
            showCurrentGame();
            showStatistics();
            board = null;
            System.out.println("Pronto para um novo jogo!");
        } else if (board.hasErrors()) {
            System.out.println("[X] Seu jogo contem erros. Verifique o tabuleiro e corrija-os.");
            showGameStatus();
        } else {
            System.out.println("[!] Voce ainda precisa preencher alguns espacos para completar o jogo.");
            showStatistics();
        }
    }


    private static int runUntilGetValidNumber(final int min, final int max){
        int current;
        
        while (true) {
            try {
                current = scanner.nextInt();
                if (current >= min && current <= max) {
                    return current;
                } else {
                    System.out.printf("[X] Informe um numero entre %d e %d: ", min, max);
                }
            } catch (Exception e) {
                System.out.printf("[X] Entrada invalida! Informe um numero entre %d e %d: ", min, max);
                scanner.nextLine(); // Limpa o buffer
            }
        }
    }

}
