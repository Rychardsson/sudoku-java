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
    
    // Códigos de cores ANSI para terminal
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    public static void main(String[] args) {
        System.out.println(BOLD + BLUE + "🎮 BEM-VINDO AO SUDOKU JAVA! 🎮" + RESET);
        System.out.println(CYAN + "Desenvolvido para consolidar conhecimentos em POO" + RESET);
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
                        System.out.println(GREEN + "👋 Obrigado por jogar! Até logo!" + RESET);
                        System.exit(0);
                    }
                    default -> System.out.println(RED + "❌ Opção inválida! Selecione uma opção do menu." + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "❌ Entrada inválida! Digite apenas números." + RESET);
                scanner.nextLine(); // Limpa o buffer
            }
            
            System.out.println("\n" + YELLOW + "Pressione Enter para continuar..." + RESET);
            scanner.nextLine();
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(BOLD + CYAN + "           🎯 MENU PRINCIPAL" + RESET);
        System.out.println("=".repeat(50));
        System.out.println("1️⃣  - Iniciar um novo jogo");
        System.out.println("2️⃣  - Colocar um número");
        System.out.println("3️⃣  - Remover um número");
        System.out.println("4️⃣  - Visualizar jogo atual");
        System.out.println("5️⃣  - Verificar status do jogo");
        System.out.println("6️⃣  - Pedir uma dica");
        System.out.println("7️⃣  - Limpar jogo");
        System.out.println("8️⃣  - Finalizar jogo");
        System.out.println("9️⃣  - Ver estatísticas");
        System.out.println("0️⃣  - Sair");
        System.out.println("=".repeat(50));
        System.out.print(BOLD + "Escolha uma opção: " + RESET);
    }
    
    private static void startNewGame() {
        if (nonNull(board)) {
            System.out.print(YELLOW + "⚠️  Já existe um jogo em andamento. Deseja começar um novo? (s/n): " + RESET);
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("s") && !response.equals("sim")) {
                return;
            }
        }
        
        System.out.println(CYAN + "\n🎲 Escolha o nível de dificuldade:" + RESET);
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
        
        System.out.println(YELLOW + "🔄 Gerando puzzle de nível " + selectedDifficulty.getLabel() + "..." + RESET);
        
        List<List<Space>> spaces = generator.generatePuzzle(selectedDifficulty);
        board = new Board(spaces);
        
        System.out.println(GREEN + "✅ Novo jogo iniciado com sucesso!" + RESET);
        System.out.println(CYAN + "🎯 Nível: " + selectedDifficulty.getLabel() + RESET);
        showCurrentGame();
    }
    
    private static void getHint() {
        if (isNull(board)) {
            System.out.println(RED + "❌ O jogo ainda não foi iniciado!" + RESET);
            return;
        }
        
        System.out.println(CYAN + "💡 Sistema de Dicas" + RESET);
        System.out.println("Informe a posição onde deseja uma dica:");
        
        System.out.print("Coluna (0-8): ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Linha (0-8): ");
        var row = runUntilGetValidNumber(0, 8);
        
        Integer hint = board.getHint(row, col);
        
        if (hint == null) {
            if (board.getSpaces().get(row).get(col).isFixed()) {
                System.out.println(YELLOW + "⚠️  Esta posição já está preenchida e é fixa!" + RESET);
            } else if (nonNull(board.getSpaces().get(row).get(col).getActual())) {
                System.out.println(YELLOW + "⚠️  Esta posição já está preenchida!" + RESET);
            } else {
                System.out.println(RED + "❌ Não foi possível encontrar uma dica para esta posição." + RESET);
            }
        } else {
            System.out.println(GREEN + "💡 Dica: O número " + hint + " pode ser colocado na posição [" + col + "," + row + "]" + RESET);
        }
    }
    
    private static void showStatistics() {
        if (isNull(board)) {
            System.out.println(RED + "❌ O jogo ainda não foi iniciado!" + RESET);
            return;
        }
        
        System.out.println(CYAN + "\n📊 ESTATÍSTICAS DO JOGO" + RESET);
        System.out.println("=".repeat(30));
        System.out.println("📈 Células preenchidas: " + board.getFilledCells() + "/81");
        System.out.println("✅ Números corretos: " + board.getCorrectNumbers());
        System.out.println("📊 Progresso: " + String.format("%.1f", (board.getFilledCells() / 81.0 * 100)) + "%");
        System.out.println("🎯 Status: " + board.getStatus().getLabel());
        
        if (board.hasErrors()) {
            System.out.println(RED + "❌ Existem erros no tabuleiro!" + RESET);
        } else {
            System.out.println(GREEN + "✅ Nenhum erro detectado!" + RESET);
        }
    }

    
    private static void runGameWithArguments(final Map<String, String> positions) {
        var option = -1;
        while (true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
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
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)){
            System.out.println("O jogo já foi iniciado");
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
        System.out.println("O jogo está pronto para começar");
    }


    private static void inputNumber() {
        if (isNull(board)){
            System.out.println(RED + "❌ O jogo ainda não foi iniciado!" + RESET);
            return;
        }

        System.out.println(CYAN + "\n🎯 Inserir Número" + RESET);
        System.out.print("Informe a coluna (0-8): ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Informe a linha (0-8): ");
        var row = runUntilGetValidNumber(0, 8);
        System.out.print("Informe o número (1-9) para a posição [" + col + "," + row + "]: ");
        var value = runUntilGetValidNumber(1, 9);
        
        if (!board.changeValue(col, row, value)){
            System.out.println(RED + "❌ A posição [" + col + "," + row + "] tem um valor fixo!" + RESET);
        } else {
            if (board.isValidMove(col, row, value)) {
                System.out.println(GREEN + "✅ Número inserido com sucesso!" + RESET);
            } else {
                System.out.println(YELLOW + "⚠️  Número inserido, mas pode haver conflito com as regras do Sudoku!" + RESET);
            }
        }
    }

    private static void removeNumber() {
        if (isNull(board)){
            System.out.println(RED + "❌ O jogo ainda não foi iniciado!" + RESET);
            return;
        }

        System.out.println(CYAN + "\n🗑️ Remover Número" + RESET);
        System.out.print("Informe a coluna (0-8): ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Informe a linha (0-8): ");
        var row = runUntilGetValidNumber(0, 8);
        
        if (!board.clearValue(col, row)){
            System.out.println(RED + "❌ A posição [" + col + "," + row + "] tem um valor fixo!" + RESET);
        } else {
            System.out.println(GREEN + "✅ Número removido com sucesso!" + RESET);
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)){
            System.out.println(RED + "❌ O jogo ainda não foi iniciado!" + RESET);
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col: board.getSpaces()){
                Space space = col.get(i);
                String displayValue;
                
                if (isNull(space.getActual())) {
                    displayValue = "  "; // Célula vazia
                } else if (space.isFixed()) {
                    displayValue = BOLD + space.getActual() + RESET; // Números fixos em negrito
                } else {
                    displayValue = " " + space.getActual(); // Números inseridos pelo usuário
                }
                
                args[argPos++] = displayValue;
            }
        }
        
        System.out.println(CYAN + "\n🎯 SEU JOGO ATUAL:" + RESET);
        System.out.println(YELLOW + "Números em negrito são fixos, outros você pode modificar" + RESET);
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if (isNull(board)){
            System.out.println(RED + "❌ O jogo ainda não foi iniciado!" + RESET);
            return;
        }

        System.out.println(CYAN + "\n📊 STATUS DO JOGO" + RESET);
        System.out.println("🎯 Status atual: " + board.getStatus().getLabel());
        
        if(board.hasErrors()){
            System.out.println(RED + "❌ O jogo contém erros nas regras do Sudoku!" + RESET);
        } else {
            System.out.println(GREEN + "✅ O jogo não contém erros!" + RESET);
        }
        
        System.out.println("📈 Progresso: " + board.getFilledCells() + "/81 células preenchidas");
    }

    private static void clearGame() {
        if (isNull(board)){
            System.out.println(RED + "❌ O jogo ainda não foi iniciado!" + RESET);
            return;
        }

        System.out.print(YELLOW + "⚠️  Tem certeza que deseja limpar seu jogo e perder todo seu progresso? (sim/não): " + RESET);
        var confirm = scanner.nextLine().trim().toLowerCase();
        
        while (!confirm.equals("sim") && !confirm.equals("não") && !confirm.equals("s") && !confirm.equals("n")){
            System.out.print("Por favor, informe 'sim' ou 'não': ");
            confirm = scanner.nextLine().trim().toLowerCase();
        }

        if(confirm.equals("sim") || confirm.equals("s")){
            board.reset();
            System.out.println(GREEN + "✅ Jogo limpo com sucesso!" + RESET);
        } else {
            System.out.println(CYAN + "🔄 Operação cancelada." + RESET);
        }
    }

    private static void finishGame() {
        if (isNull(board)){
            System.out.println(RED + "❌ O jogo ainda não foi iniciado!" + RESET);
            return;
        }

        if (board.gameIsFinished()){
            System.out.println(GREEN + BOLD + "\n🎉 PARABÉNS! VOCÊ CONCLUIU O SUDOKU! 🎉" + RESET);
            showCurrentGame();
            showStatistics();
            board = null;
            System.out.println(CYAN + "🔄 Pronto para um novo jogo!" + RESET);
        } else if (board.hasErrors()) {
            System.out.println(RED + "❌ Seu jogo contém erros. Verifique o tabuleiro e corrija-os." + RESET);
            showGameStatus();
        } else {
            System.out.println(YELLOW + "⚠️  Você ainda precisa preencher alguns espaços para completar o jogo." + RESET);
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
                    System.out.printf(RED + "❌ Informe um número entre %d e %d: " + RESET, min, max);
                }
            } catch (Exception e) {
                System.out.printf(RED + "❌ Entrada inválida! Informe um número entre %d e %d: " + RESET, min, max);
                scanner.nextLine(); // Limpa o buffer
            }
        }
    }

}
