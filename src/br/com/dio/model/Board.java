package br.com.dio.model;

import java.util.Collection;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import static br.com.dio.model.GameStatusEnum.COMPLETE;
import static br.com.dio.model.GameStatusEnum.INCOMPLETE;
import static br.com.dio.model.GameStatusEnum.NON_STARTED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> spaces;
    private static final int BOARD_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus(){
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){
            return NON_STARTED;
        }

        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors(){
        if(getStatus() == NON_STARTED){
            return false;
        }

        return hasSudokuRuleViolations();
    }
    
    /**
     * Verifica se há violações das regras do Sudoku
     */
    private boolean hasSudokuRuleViolations() {
        // Verifica linhas
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (hasRowConflict(row)) {
                return true;
            }
        }
        
        // Verifica colunas
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (hasColumnConflict(col)) {
                return true;
            }
        }
        
        // Verifica quadrados 3x3
        for (int boxRow = 0; boxRow < BOARD_SIZE; boxRow += SUBGRID_SIZE) {
            for (int boxCol = 0; boxCol < BOARD_SIZE; boxCol += SUBGRID_SIZE) {
                if (hasBoxConflict(boxRow, boxCol)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Verifica conflitos em uma linha
     */
    private boolean hasRowConflict(int row) {
        Set<Integer> seen = new HashSet<>();
        for (int col = 0; col < BOARD_SIZE; col++) {
            Integer value = spaces.get(row).get(col).getActual();
            if (nonNull(value)) {
                if (seen.contains(value)) {
                    return true;
                }
                seen.add(value);
            }
        }
        return false;
    }
    
    /**
     * Verifica conflitos em uma coluna
     */
    private boolean hasColumnConflict(int col) {
        Set<Integer> seen = new HashSet<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            Integer value = spaces.get(row).get(col).getActual();
            if (nonNull(value)) {
                if (seen.contains(value)) {
                    return true;
                }
                seen.add(value);
            }
        }
        return false;
    }
    
    /**
     * Verifica conflitos em um quadrado 3x3
     */
    private boolean hasBoxConflict(int startRow, int startCol) {
        Set<Integer> seen = new HashSet<>();
        for (int row = startRow; row < startRow + SUBGRID_SIZE; row++) {
            for (int col = startCol; col < startCol + SUBGRID_SIZE; col++) {
                Integer value = spaces.get(row).get(col).getActual();
                if (nonNull(value)) {
                    if (seen.contains(value)) {
                        return true;
                    }
                    seen.add(value);
                }
            }
        }
        return false;
    }
    
    /**
     * Verifica se um número pode ser colocado em uma posição específica
     */
    public boolean isValidMove(int row, int col, int value) {
        // Salva o valor atual
        Space space = spaces.get(row).get(col);
        Integer originalValue = space.getActual();
        
        // Testa temporariamente o novo valor
        space.setActual(value);
        
        boolean isValid = !hasRowConflict(row) && 
                         !hasColumnConflict(col) && 
                         !hasBoxConflict((row / SUBGRID_SIZE) * SUBGRID_SIZE, 
                                       (col / SUBGRID_SIZE) * SUBGRID_SIZE);
        
        // Restaura o valor original
        space.setActual(originalValue);
        
        return isValid;
    }
    
    /**
     * Obtém dica para uma posição específica
     */
    public Integer getHint(int row, int col) {
        if (spaces.get(row).get(col).isFixed() || 
            nonNull(spaces.get(row).get(col).getActual())) {
            return null; // Célula já preenchida
        }
        
        for (int value = 1; value <= 9; value++) {
            if (isValidMove(row, col, value)) {
                return value;
            }
        }
        
        return null; // Nenhum valor válido encontrado
    }
    
    /**
     * Conta quantos números estão corretos no tabuleiro
     */
    public int getCorrectNumbers() {
        int count = 0;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Space space = spaces.get(row).get(col);
                if (nonNull(space.getActual()) && 
                    space.getActual().equals(space.getExpected())) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Conta quantas células estão preenchidas
     */
    public int getFilledCells() {
        return (int) spaces.stream()
                .flatMap(Collection::stream)
                .mapToInt(s -> nonNull(s.getActual()) ? 1 : 0)
                .sum();
    }

    public boolean changeValue(final int col, final int row, final int value){
        var space = spaces.get(col).get(row);
        if (space.isFixed()){
            return false;
        }

        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row){
        var space = spaces.get(col).get(row);
        if (space.isFixed()){
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset(){
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished(){
        return !hasErrors() && getStatus().equals(COMPLETE);
    }

}
