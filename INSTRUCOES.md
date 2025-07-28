## 🎮 COMO EXECUTAR O SUDOKU JAVA

### Método 1: Usando o script automatizado
```bash
# No Windows
run.bat

# No PowerShell/Git Bash
./run.bat
```

### Método 2: Compilação manual
```bash
# Criar diretório bin
mkdir bin

# Compilar
javac -encoding UTF-8 -d bin -sourcepath src src/br/com/dio/Main.java

# Executar
java -cp bin br.com.dio.Main
```

### Método 3: Com argumentos (modo legado)
```bash
java -cp bin br.com.dio.Main "0,0;1,true" "0,1;2,false" ...
```

## 🎯 EXEMPLO DE JOGO

### 1. Iniciar Novo Jogo
```
Escolha uma opcao: 1
Escolha o nivel de dificuldade:
1 - Facil (45 pistas)
2 - Medio (35 pistas)  
3 - Dificil (25 pistas)
4 - Expert (17 pistas)
```

### 2. Visualizar Tabuleiro
O tabuleiro mostra:
- `*N` = Números fixos (não podem ser alterados)
- ` N` = Números inseridos por você
- `  ` = Células vazias

### 3. Inserir Números
```
Escolha uma opcao: 2
Informe a coluna (0-8): 0
Informe a linha (0-8): 0
Informe o numero (1-9) para a posicao [0,0]: 5
```

### 4. Sistema de Coordenadas
```
     COLUNAS
   0 1 2 3 4 5 6 7 8
L0 . . . . . . . . .
I1 . . . . . . . . .
N2 . . . . . . . . .
H3 . . . . . . . . .
A4 . . . . . . . . .
S5 . . . . . . . . .
 6 . . . . . . . . .
 7 . . . . . . . . .
 8 . . . . . . . . .
```

### 5. Pedir Dica
```
Escolha uma opcao: 6
Informe a posicao onde deseja uma dica:
Coluna (0-8): 0
Linha (0-8): 0
[DICA] O numero 5 pode ser colocado na posicao [0,0]
```

### 6. Verificar Status
```
Escolha uma opcao: 5
STATUS DO JOGO
Status atual: incompleto
[OK] O jogo nao contem erros!
Progresso: 25/81 celulas preenchidas
```

### 7. Estatísticas
```
Escolha uma opcao: 9
ESTATISTICAS DO JOGO
==============================
Celulas preenchidas: 25/81
Numeros corretos: 23
Progresso: 30.9%
Status: incompleto
[OK] Nenhum erro detectado!
```

## 🛠️ DICAS DE JOGABILIDADE

### Estratégias Básicas
1. **Números únicos**: Procure células onde apenas um número é possível
2. **Eliminação**: Analise quais números já estão na linha, coluna e quadrado 3x3
3. **Padrões**: Identifique padrões que limitam as possibilidades

### Usando o Sistema de Dicas
- Use as dicas quando estiver preso
- Analise por que aquele número é válido
- Aprenda os padrões para situações futuras

### Verificação de Erros
- Verifique o status regularmente
- O sistema detecta automaticamente violações das regras
- Corrija erros antes de continuar

## 🔧 TROUBLESHOOTING

### Problema: Caracteres especiais não aparecem
**Solução**: Use um terminal com suporte UTF-8 (Windows Terminal, PowerShell, Git Bash)

### Problema: Erro de compilação
**Solução**: 
```bash
# Certifique-se de usar encoding UTF-8
javac -encoding UTF-8 -d bin -sourcepath src src/br/com/dio/Main.java
```

### Problema: Jogo não inicia
**Solução**: Verifique se o Java 17+ está instalado
```bash
java -version
```

## 📚 CONCEITOS APRENDIDOS

### Programação Orientada a Objetos
- ✅ **Encapsulamento**: Classes Space, Board protegem seus dados
- ✅ **Composição**: Board contém Spaces
- ✅ **Abstração**: Interface simples para usuário
- ✅ **Polimorfismo**: Métodos overloaded

### Estruturas de Dados
- ✅ **Arrays bidimensionais**: Representação do tabuleiro
- ✅ **Listas**: Collections flexíveis
- ✅ **Sets**: Verificação de duplicatas
- ✅ **Enums**: Estados do jogo e dificuldades

### Algoritmos
- ✅ **Backtracking**: Geração de puzzles
- ✅ **Validação**: Regras do Sudoku
- ✅ **Busca**: Sistema de dicas
- ✅ **Aleatorização**: Embaralhamento de puzzles

### Interface e UX
- ✅ **Menus interativos**: Navegação intuitiva
- ✅ **Validação de entrada**: Tratamento robusto de erros
- ✅ **Feedback visual**: Indicadores de status
- ✅ **Progressão**: Estatísticas e acompanhamento
