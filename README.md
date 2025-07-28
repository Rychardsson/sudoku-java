# 🎮 Sudoku Java - DIO

Um jogo de Sudoku completo desenvolvido em Java para consolidar conhecimentos em **Programação Orientada a Objetos**, manipulação de estruturas de dados e interface de terminal.

## 🎯 Características

- **Interface interativa no terminal** com cores e emojis
- **Gerador automático de puzzles** com 4 níveis de dificuldade
- **Sistema de validação** que verifica as regras do Sudoku em tempo real
- **Sistema de dicas** para ajudar quando necessário
- **Estatísticas do jogo** com progresso detalhado
- **Interface amigável** com menus coloridos e feedback visual

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Terminal com suporte a cores ANSI (Windows Terminal, PowerShell, Git Bash, etc.)

### Compilação e Execução

```bash
# Navegar para o diretório do projeto
cd sudoku-java

# Compilar o projeto
javac -d bin -sourcepath src src/br/com/dio/Main.java

# Executar o jogo
java -cp bin br.com.dio.Main
```

## 🎲 Como Jogar

### 1. **Iniciando um Novo Jogo**

- Escolha a opção "1" no menu principal
- Selecione o nível de dificuldade:
  - **Fácil**: 45 pistas (ideal para iniciantes)
  - **Médio**: 35 pistas (desafio moderado)
  - **Difícil**: 25 pistas (para jogadores experientes)
  - **Expert**: 17 pistas (máximo desafio)

### 2. **Inserindo Números**

- Use a opção "2" para colocar números
- Informe coluna (0-8), linha (0-8) e o número (1-9)
- O sistema valida automaticamente se o movimento é válido

### 3. **Sistema de Coordenadas**

```
   0 1 2   3 4 5   6 7 8
0  . . . | . . . | . . .
1  . . . | . . . | . . .
2  . . . | . . . | . . .
   ------+-------+------
3  . . . | . . . | . . .
4  . . . | . . . | . . .
5  . . . | . . . | . . .
   ------+-------+------
6  . . . | . . . | . . .
7  . . . | . . . | . . .
8  . . . | . . . | . . .
```

### 4. **Funcionalidades Disponíveis**

- **Visualizar jogo**: Mostra o tabuleiro atual com destaque para números fixos
- **Verificar status**: Informa se há erros e o progresso atual
- **Pedir dica**: Sugere um número válido para uma posição específica
- **Remover número**: Remove números que você inseriu (não funciona com números fixos)
- **Limpar jogo**: Remove todos os números inseridos, mantendo apenas os fixos
- **Estatísticas**: Mostra progresso detalhado e informações do jogo

## 🧩 Regras do Sudoku

O Sudoku é um quebra-cabeça lógico onde você deve preencher uma grade 9×9 com números de 1 a 9, seguindo estas regras:

1. **Cada linha** deve conter todos os números de 1 a 9 (sem repetição)
2. **Cada coluna** deve conter todos os números de 1 a 9 (sem repetição)
3. **Cada quadrado 3×3** deve conter todos os números de 1 a 9 (sem repetição)

## 🛠️ Arquitetura do Projeto

### Estrutura de Pacotes

```
src/
└── br/com/dio/
    ├── Main.java                 # Classe principal com interface do usuário
    ├── model/
    │   ├── Board.java           # Lógica do tabuleiro e validações
    │   ├── Space.java           # Representa cada célula do tabuleiro
    │   └── GameStatusEnum.java  # Estados do jogo
    └── util/
        ├── BoardTemplate.java   # Template visual do tabuleiro
        └── SudokuGenerator.java # Gerador de puzzles
```

### Conceitos de POO Aplicados

#### 1. **Encapsulamento**

- Atributos privados nas classes `Board`, `Space`
- Métodos públicos para acesso controlado aos dados
- Validações internas para manter integridade dos dados

#### 2. **Abstração**

- Interface simples para o usuário através da classe `Main`
- Complexidade do algoritmo de geração oculta na classe `SudokuGenerator`
- Métodos específicos para cada responsabilidade

#### 3. **Composição**

- `Board` contém uma lista de `Space`
- `Space` encapsula estado e comportamento de cada célula
- Relacionamento "tem-um" bem definido

#### 4. **Enumerações**

- `GameStatusEnum` para estados do jogo
- `Difficulty` para níveis de dificuldade
- Type-safety e código mais legível

## 🔧 Funcionalidades Técnicas

### Geração de Puzzles

- **Algoritmo de backtracking** para gerar soluções válidas
- **Remoção estratégica** de números baseada na dificuldade
- **Validação em tempo real** durante a geração

### Validação de Movimentos

- **Verificação de linhas, colunas e quadrados 3×3**
- **Detecção de conflitos** antes da inserção
- **Feedback imediato** sobre validade dos movimentos

### Sistema de Dicas

- **Análise do estado atual** do tabuleiro
- **Sugestão de números válidos** para posições específicas
- **Algoritmo inteligente** que considera todas as regras

### Interface do Usuário

- **Cores ANSI** para melhor experiência visual
- **Menus interativos** com navegação intuitiva
- **Feedback visual** para ações do usuário
- **Tratamento de erros** robusto

## 🎯 Objetivos de Aprendizado

Este projeto consolida os seguintes conceitos:

### Programação Orientada a Objetos

- ✅ **Classes e Objetos**: Modelagem de entidades do domínio
- ✅ **Encapsulamento**: Proteção de dados e controle de acesso
- ✅ **Composição**: Relacionamentos entre objetos
- ✅ **Abstração**: Simplificação de interfaces complexas

### Estruturas de Dados

- ✅ **Listas bidimensionais**: Representação do tabuleiro
- ✅ **Collections**: Uso de List, Set, Map
- ✅ **Algoritmos de busca**: Validação e geração de puzzles

### Manipulação de Métodos

- ✅ **Métodos estáticos e de instância**
- ✅ **Passagem de parâmetros**
- ✅ **Retorno de valores**
- ✅ **Sobrecarga de métodos**

### Interface de Terminal

- ✅ **Entrada e saída formatada**
- ✅ **Validação de entrada do usuário**
- ✅ **Cores e formatação ANSI**
- ✅ **Loops de interação**

## 🎮 Dicas para Jogar

1. **Comece com números únicos**: Procure células onde apenas um número é possível
2. **Use eliminação**: Veja quais números já estão presentes na linha, coluna e quadrado
3. **Analise padrões**: Procure por padrões que limitam as possibilidades
4. **Use o sistema de dicas**: Quando estiver preso, peça uma dica
5. **Verifique regularmente**: Use a opção de verificar status para detectar erros cedo

## 📊 Níveis de Dificuldade

| Nível       | Pistas | Descrição                                             |
| ----------- | ------ | ----------------------------------------------------- |
| **Fácil**   | 45     | Ideal para iniciantes, muitas pistas disponíveis      |
| **Médio**   | 35     | Desafio moderado, requer lógica básica                |
| **Difícil** | 25     | Para jogadores experientes, requer técnicas avançadas |
| **Expert**  | 17     | Máximo desafio, mínimo de pistas possível             |

## 🔄 Melhorias Futuras

- [ ] Salvar e carregar jogos
- [ ] Cronômetro e recordes pessoais
- [ ] Múltiplas dicas por posição
- [ ] Interface gráfica (GUI)
- [ ] Multiplayer online
- [ ] Diferentes variações do Sudoku

## 📝 Licença

Este projeto foi desenvolvido como parte do curso da DIO (Digital Innovation One) para fins educacionais.

---

**Desenvolvido com ❤️ para consolidar conhecimentos em Java e POO**
