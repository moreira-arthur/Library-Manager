# Relatório do Projeto

## Identificação do Grupo

- **Nome do Aluno 1:** Vinicius Paiva
- **Número USP do Aluno 1:** 13783530
- **Nome do Aluno 2:** Vitor Zago 
- **Número USP do Aluno 2:** 14783595
- **Nome do Aluno 3:** Arthur Moreira Correa
- **Número USP do Aluno 3:** 13749952

## Requisitos

Os requisitos são fornecidos na atribuição, mas incluímos novos requisitos conforme necessário para nossa implementação específica.

### Funcionalidades do Sistema:

1. **Gerenciamento de Livros:** Adicionar, editar, excluir e buscar livros por título, autor, ISBN ou categoria.
2. **Gerenciamento de Usuários:** Adicionar, editar, excluir e buscar usuários por nome, sobrenome ou cpf.
3. **Gerenciamento de Empréstimos:** Registrar empréstimos de livros para usuários, registrar devoluções de livros e gerenciar multas por atrasos.
4. **Design da Interface Gráfica (GUI):** Design de uma interface gráfica usando Swing que permita aos bibliotecários interagir com o sistema e realizar as tarefas necessárias. A GUI deve ser intuitiva, fácil de navegar e visualmente atraente.
5. **Design Orientado a Objetos:** Implementação do sistema utilizando princípios de programação orientada a objetos. Criação de classes para livros, usuários, empréstimos e outras entidades relevantes. Utilização de herança e polimorfismo para criar um design de sistema flexível e extensível.
6. **Entrada/Saída de Dados:** Implementação de E/S de arquivos para salvar e carregar dados de livros, usuários e empréstimos a partir de arquivos. Garantir a integridade e consistência dos dados durante as operações de leitura/escrita.
7. **Estruturas de Controle e Estruturas de Dados:** Implementação da lógica do sistema usando estruturas de controle (loops, condicionais) e estruturas de dados apropriadas (arrays, listas, mapas) para gerenciar o inventário de livros, registros de usuários e transações de empréstimos.
8. **Tratamento de Exceções:** Implementação de tratamento de exceções para capturar e lidar com possíveis erros ou entradas inválidas dos usuários durante a operação do sistema.
9. **Autenticação de Usuários:** Implementação de um sistema de autenticação simples para restringir o acesso às funcionalidades do sistema com base em funções de usuário (bibliotecário, administrador).
10. **Documentação:** Inclusão de comentários e JavaDocs no código.

## Descrição do Projeto

Nosso projeto implementa as funcionalidades descritas nos requisitos de forma integrada, utilizando uma abordagem orientada a objetos. A seguir, descrevemos a implementação de cada funcionalidade:

### Gerenciamento de Livros

- Criamos uma classe `Livro` com atributos como título, autor, ISBN e categoria.
- Implementamos métodos para adicionar, editar, excluir e buscar livros no sistema.

### Gerenciamento de Usuários

- Criamos uma classe `Usuario` com atributos como nome e informação de contato.
- Implementamos métodos para adicionar, editar, excluir e buscar usuários.

### Gerenciamento de Empréstimos

- Criamos uma classe `Emprestimo` para gerenciar os empréstimos de livros.
- Implementamos métodos para registrar empréstimos, registrar devoluções e gerenciar multas.

### Design da Interface Gráfica

- Utilizamos Swing para desenvolver uma GUI intuitiva e fácil de usar.
- A interface permite aos bibliotecários realizar todas as operações de gerenciamento de livros, usuários e empréstimos.

### Design Orientado a Objetos

- Utilizamos herança e polimorfismo para criar um design flexível.
- As classes principais incluem `Livro`, `Usuario` e `Emprestimo`.

### Entrada/Saída de Dados

- Implementamos E/S de arquivos para salvar e carregar dados.

### Estruturas de Controle e Estruturas de Dados

- Implementamos loops e condicionais para a lógica do sistema.

### Tratamento de Exceções

- Implementamos blocos `try-catch` para capturar e lidar com exceções.
- Garantimos que o sistema lida adequadamente com entradas inválidas e erros inesperados.

### Autenticação de Usuários

- Implementamos um sistema de login simples.
- Restringimos o acesso às funcionalidades baseando-nos no papel do usuário (bibliotecário ou administrador).

### Documentação

- Incluímos comentários no código para facilitar a compreensão.
- Utilizamos JavaDocs para documentar as principais classes e métodos.

## Comentários Sobre o Código

- O código está estruturado de maneira modular para facilitar a manutenção e expansão futura.
- Utilizamos padrões de design quando apropriado para melhorar a clareza e a eficiência do código.

## Plano de Testes

### Descrição dos Testes

- Realizamos testes unitários para cada classe principal (`Livro`, `Usuario`, `Emprestimo`).
- Testamos a integração das diferentes partes do sistema.
- Utilizamos a ferramenta JUnit para automatizar os testes.

### Resultados dos Testes

- Todos os testes unitários passaram sem erros.

## Procedimentos de Construção

### Passo-a-Passo para Executar o Código

1. **Instalar Dependências:** Instale o Java JDK.
2. **Baixar o Projeto:** Clone o repositório do projeto a partir do [link do repositório].
3. **Construir o Projeto:** Navegue até o diretório do projeto e execute `./gradlew build`.
4. **Configurar o Ambiente:** Certifique-se de que o Swing está configurado corretamente no seu ambiente.
5. **Executar o Projeto:** Execute o comando `./gradlew run` para iniciar a aplicação.

## Problemas

- Encontramos dificuldades na integração da GUI com a lógica de negócios, mas resolvemos utilizando padrões de design apropriados.
- A implementação da E/S de arquivos apresentou desafios de sincronização, superados com técnicas de controle de concorrência.

## Comentários

- A experiência de desenvolver este projeto foi enriquecedora, permitindo-nos aplicar e expandir nossos conhecimentos em programação orientada a objetos e desenvolvimento de interfaces gráficas.
- Acreditamos que a solução implementada atende bem aos requisitos e oferece uma base sólida para futuras expansões.
