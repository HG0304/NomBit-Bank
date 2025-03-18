# Projeto Nombit Bank

## Objetivo do Projeto

O **Nombit Bank** é uma aplicação desenvolvida para simular operações bancárias com criptomoedas. O objetivo principal é permitir que os usuários realizem operações como depósito, saque, compra e venda de criptomoedas, além de consultar saldo e extrato. Este projeto foi criado com foco em aprendizado e demonstração de habilidades em desenvolvimento backend utilizando Java.

## Estrutura do Projeto

O projeto segue uma arquitetura organizada em camadas, com as seguintes pastas principais:

- **controller/**: Contém os controladores responsáveis por gerenciar as requisições e respostas da aplicação.
- **DAO/**: Implementa a camada de acesso a dados, incluindo a conexão com o banco de dados e operações CRUD.
- **model/**: Define as classes de modelo que representam as entidades do sistema, como Bitcoin, Ethereum e Carteira.
- **view/**: (Se aplicável) Responsável pela interface com o usuário.

## Estratégias Utilizadas

- **Organização em camadas**: A separação de responsabilidades entre controladores, modelos e acesso a dados facilita a manutenção e escalabilidade do projeto.
- **Conexão com banco de dados**: A classe [`Conexao`](src/DAO/Conexao.java) foi implementada para gerenciar a conexão com o banco de dados de forma eficiente.
- **Boas práticas de programação**: O código foi desenvolvido seguindo princípios de clareza, reutilização e modularidade.

## Desafios Superados

- **Gerenciamento de transações com criptomoedas**: Implementar a lógica de compra e venda de criptomoedas, garantindo a consistência dos dados.
- **Autenticação de usuários**: Criar um sistema seguro de login utilizando a classe [`LoginDAO`](src/DAO/LoginDAO.java).
- **Integração com banco de dados**: Configurar e gerenciar a persistência de dados de forma eficiente.

## Como Utilizar

### Pré-requisitos

- **Java JDK**: Certifique-se de ter o Java Development Kit instalado.
- **Banco de Dados**: O banco de dados já está provisionado no Azure, portanto, não é necessário configurar um banco local. Apenas atualize as credenciais de acesso na classe [`Conexao`](src/DAO/Conexao.java) para conectar-se ao banco remoto.
- **IDE**: Recomenda-se o uso do NetBeans ou outra IDE compatível com projetos Java.

### Passos para Execução

1. Clone o repositório:
   ```sh
   git clone <URL_DO_REPOSITORIO>
   ```
2. Importe o projeto na sua IDE.
3. Configure o banco de dados e execute os scripts de criação de tabelas (se aplicável).
4. Compile e execute o projeto utilizando o arquivo `build.xml` ou diretamente pela IDE.

### Funcionalidades Disponíveis

- **Login**: Autenticação de usuários.
- **Depósito e Saque**: Gerenciamento de saldo.
- **Compra e Venda de Criptomoedas**: Operações com Bitcoin e Ethereum.
- **Consulta de Saldo e Extrato**: Visualização de informações financeiras.

---

Este projeto foi desenvolvido com foco em aprendizado e demonstração de habilidades técnicas. Caso tenha dúvidas ou sugestões, sinta-se à vontade para entrar em contato.