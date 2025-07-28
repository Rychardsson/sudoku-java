package br.com.dio.model;

public class Space {

    private Integer actual;
    private final int expected;
    private final boolean fixed;


    public Space(final int expected, final boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if (fixed){
            actual = expected;
        }
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(final Integer actual) {
        if (fixed) return;
        this.actual = actual;
    }

    public void clearSpace(){
        setActual(null);
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }
    
    /**
     * Verifica se o valor atual está correto
     */
    public boolean isCorrect() {
        return actual != null && actual.equals(expected);
    }
    
    /**
     * Verifica se a célula está vazia
     */
    public boolean isEmpty() {
        return actual == null;
    }
    
    /**
     * Representação em string para debugging
     */
    @Override
    public String toString() {
        return String.format("Space{actual=%s, expected=%d, fixed=%s}", 
                           actual, expected, fixed);
    }
}
