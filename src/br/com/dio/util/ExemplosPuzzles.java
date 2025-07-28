package br.com.dio.util;

/**
 * Exemplos de puzzles de Sudoku para teste e demonstração
 * Estes exemplos podem ser usados para testar diferentes cenários do jogo
 */
public final class ExemplosPuzzles {
    
    private ExemplosPuzzles() {}
    
    /**
     * Puzzle fácil - ideal para iniciantes
     */
    public static final int[][] PUZZLE_FACIL = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };
    
    /**
     * Solução para o puzzle fácil
     */
    public static final int[][] SOLUCAO_FACIL = {
        {5, 3, 4, 6, 7, 8, 9, 1, 2},
        {6, 7, 2, 1, 9, 5, 3, 4, 8},
        {1, 9, 8, 3, 4, 2, 5, 6, 7},
        {8, 5, 9, 7, 6, 1, 4, 2, 3},
        {4, 2, 6, 8, 5, 3, 7, 9, 1},
        {7, 1, 3, 9, 2, 4, 8, 5, 6},
        {9, 6, 1, 5, 3, 7, 2, 8, 4},
        {2, 8, 7, 4, 1, 9, 6, 3, 5},
        {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };
    
    /**
     * Puzzle médio - para jogadores com experiência
     */
    public static final int[][] PUZZLE_MEDIO = {
        {0, 0, 0, 6, 0, 0, 4, 0, 0},
        {7, 0, 0, 0, 0, 3, 6, 0, 0},
        {0, 0, 0, 0, 9, 1, 0, 8, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 5, 0, 1, 8, 0, 0, 0, 3},
        {0, 0, 0, 3, 0, 6, 0, 4, 5},
        {0, 4, 0, 2, 0, 0, 0, 6, 0},
        {9, 0, 3, 0, 0, 0, 0, 0, 0},
        {0, 2, 0, 0, 0, 0, 1, 0, 0}
    };
    
    /**
     * Puzzle difícil - desafio para especialistas
     */
    public static final int[][] PUZZLE_DIFICIL = {
        {0, 0, 0, 0, 0, 0, 0, 1, 0},
        {4, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 6, 0, 2},
        {0, 0, 0, 0, 0, 3, 0, 7, 0},
        {5, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 2, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    
    /**
     * Converte uma matriz de int para argumentos de linha de comando
     * Útil para testar o sistema legado com argumentos
     */
    public static String[] matrizParaArgumentos(int[][] puzzle, int[][] solucao) {
        if (puzzle.length != 9 || solucao.length != 9) {
            throw new IllegalArgumentException("Puzzle deve ser 9x9");
        }
        
        String[] args = new String[81];
        int index = 0;
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean isFixed = puzzle[i][j] != 0;
                int expected = solucao[i][j];
                args[index++] = String.format("%d,%d;%d,%s", i, j, expected, isFixed);
            }
        }
        
        return args;
    }
    
    /**
     * Imprime um puzzle no formato visual
     */
    public static void imprimirPuzzle(int[][] puzzle) {
        System.out.println("Puzzle:");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("------+-------+------");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                if (puzzle[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(puzzle[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Valida se um puzzle está correto
     */
    public static boolean validarPuzzle(int[][] puzzle) {
        // Verifica se é 9x9
        if (puzzle.length != 9) return false;
        for (int[] row : puzzle) {
            if (row.length != 9) return false;
        }
        
        // Verifica valores válidos (0-9)
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] < 0 || puzzle[i][j] > 9) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Exemplo de uso dos puzzles
     */
    public static void exemploUso() {
        System.out.println("=== EXEMPLOS DE PUZZLES ===");
        
        System.out.println("\n1. Puzzle Fácil:");
        imprimirPuzzle(PUZZLE_FACIL);
        
        System.out.println("\n2. Puzzle Médio:");
        imprimirPuzzle(PUZZLE_MEDIO);
        
        System.out.println("\n3. Puzzle Difícil:");
        imprimirPuzzle(PUZZLE_DIFICIL);
        
        System.out.println("\n4. Argumentos para teste (Puzzle Fácil):");
        String[] args = matrizParaArgumentos(PUZZLE_FACIL, SOLUCAO_FACIL);
        System.out.println("Exemplo de argumentos: " + args[0] + " " + args[1] + " ...");
        System.out.println("Total de argumentos: " + args.length);
    }
}
