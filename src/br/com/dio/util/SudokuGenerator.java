package br.com.dio.util;

import br.com.dio.model.Space;

import java.util.*;

public class SudokuGenerator {
    
    private static final int BOARD_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private final Random random = new Random();
    
    public enum Difficulty {
        EASY(45, "Fácil"),
        MEDIUM(35, "Médio"),
        HARD(25, "Difícil"),
        EXPERT(17, "Expert");
        
        private final int clues;
        private final String label;
        
        Difficulty(int clues, String label) {
            this.clues = clues;
            this.label = label;
        }
        
        public int getClues() {
            return clues;
        }
        
        public String getLabel() {
            return label;
        }
    }
    
    /**
     * Gera um tabuleiro de Sudoku completo e válido
     */
    public List<List<Space>> generateCompleteBoard() {
        int[][] solution = new int[BOARD_SIZE][BOARD_SIZE];
        generateSolution(solution);
        return convertToSpacesList(solution, Difficulty.EASY); // Todas as células preenchidas para o tabuleiro completo
    }
    
    /**
     * Gera um puzzle de Sudoku com dificuldade específica
     */
    public List<List<Space>> generatePuzzle(Difficulty difficulty) {
        int[][] solution = new int[BOARD_SIZE][BOARD_SIZE];
        generateSolution(solution);
        int[][] puzzle = createPuzzle(solution, difficulty);
        return convertToSpacesList(puzzle, difficulty);
    }
    
    /**
     * Gera uma solução completa e válida de Sudoku
     */
    private boolean generateSolution(int[][] board) {
        // Encontra a primeira célula vazia
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 0) {
                    // Tenta números de 1 a 9 em ordem aleatória
                    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
                    Collections.shuffle(numbers, random);
                    
                    for (int num : numbers) {
                        if (isValidPlacement(board, row, col, num)) {
                            board[row][col] = num;
                            
                            if (generateSolution(board)) {
                                return true;
                            }
                            
                            board[row][col] = 0; // Backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true; // Tabuleiro completo
    }
    
    /**
     * Cria um puzzle removendo números da solução completa
     */
    private int[][] createPuzzle(int[][] solution, Difficulty difficulty) {
        int[][] puzzle = new int[BOARD_SIZE][BOARD_SIZE];
        
        // Copia a solução
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(solution[i], 0, puzzle[i], 0, BOARD_SIZE);
        }
        
        // Remove números até atingir a dificuldade desejada
        int cellsToRemove = 81 - difficulty.getClues();
        List<int[]> positions = new ArrayList<>();
        
        // Cria lista de todas as posições
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                positions.add(new int[]{i, j});
            }
        }
        
        Collections.shuffle(positions, random);
        
        for (int i = 0; i < cellsToRemove && i < positions.size(); i++) {
            int[] pos = positions.get(i);
            puzzle[pos[0]][pos[1]] = 0;
        }
        
        return puzzle;
    }
    
    /**
     * Converte matriz int[][] para List<List<Space>>
     */
    private List<List<Space>> convertToSpacesList(int[][] solution, Difficulty difficulty) {
        List<List<Space>> spaces = new ArrayList<>();
        
        // Primeiro, gera um puzzle removendo números da solução
        int[][] puzzle = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(solution[i], 0, puzzle[i], 0, BOARD_SIZE);
        }
        
        // Remove números baseado na dificuldade
        if (difficulty != Difficulty.EASY || solution[0][0] == 0) { // Se não é um tabuleiro completo
            int cellsToRemove = 81 - difficulty.getClues();
            List<int[]> positions = new ArrayList<>();
            
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    positions.add(new int[]{i, j});
                }
            }
            
            Collections.shuffle(positions, random);
            
            for (int i = 0; i < cellsToRemove && i < positions.size(); i++) {
                int[] pos = positions.get(i);
                puzzle[pos[0]][pos[1]] = 0;
            }
        }
        
        // Converte para List<List<Space>>
        for (int i = 0; i < BOARD_SIZE; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_SIZE; j++) {
                int puzzleValue = puzzle[i][j];
                int solutionValue = solution[i][j];
                boolean isFixed = puzzleValue != 0;
                
                Space space = new Space(solutionValue, isFixed);
                spaces.get(i).add(space);
            }
        }
        
        return spaces;
    }
    
    /**
     * Gera um valor esperado para uma posição específica (para debugging)
     */
    private int generateExpectedValue(int row, int col) {
        // Para células vazias, gera um valor válido como "esperado"
        // Isso é usado principalmente para debugging e validação
        return ((row * 3 + row / 3 + col) % 9) + 1;
    }
    
    /**
     * Verifica se um número pode ser colocado em uma posição específica
     */
    public static boolean isValidPlacement(int[][] board, int row, int col, int num) {
        // Verifica linha
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[row][j] == num) {
                return false;
            }
        }
        
        // Verifica coluna
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }
        
        // Verifica quadrado 3x3
        int startRow = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int startCol = (col / SUBGRID_SIZE) * SUBGRID_SIZE;
        
        for (int i = startRow; i < startRow + SUBGRID_SIZE; i++) {
            for (int j = startCol; j < startCol + SUBGRID_SIZE; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Resolve um puzzle de Sudoku (para sistema de dicas)
     */
    public static boolean solvePuzzle(int[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValidPlacement(board, row, col, num)) {
                            board[row][col] = num;
                            
                            if (solvePuzzle(board)) {
                                return true;
                            }
                            
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
