# Teste Técnico - Java Developer

Este é um teste técnico para a vaga de Java Developer. O objetivo do teste é avaliar suas habilidades em desenvolvimento Java utilizando as tecnologias mencionadas abaixo.

## Tecnologias Utilizadas

- Java JDK 17
- Spring Boot 3.1.0
- MySQL
- H2 Database
- Docker

## Descrição do Projeto

O projeto consiste em uma aplicação Java com Spring Boot para gerenciamento de pedidos. A aplicação permite o cadastro, consulta, atualização e exclusão de pedidos.

A estrutura básica do projeto inclui os seguintes componentes:

- `Pedido` (classe de domínio): representa um pedido com suas propriedades, como número de controle, data de cadastro, descrição, valor, quantidade, etc.
- `PedidoDTO` (classe de transferência de dados): representa os dados de um pedido enviados/recebidos pela API.
- `PedidoRepository` (repositório): realiza a persistência e recuperação de pedidos no banco de dados.
- `PedidoService` (serviço): contém a lógica de negócio para manipulação de pedidos, como validações e cálculo de desconto.
- Controladores (API REST): expõem endpoints para realizar operações CRUD de pedidos.

## Executando o Projeto

Certifique-se de ter as seguintes dependências instaladas em sua máquina:

- Java JDK 17
- Docker

Siga as etapas abaixo para executar o projeto:

1. Clone este repositório em sua máquina local.
2. Navegue até o diretório raiz do projeto.
3. Execute o seguinte comando para criar a imagem Docker do banco de dados MySQL:

   ```
   docker-compose up -d
   ```

4. Aguarde até que o banco de dados esteja em execução.
5. Execute o seguinte comando para iniciar a aplicação:

   ```
   ./mvnw spring-boot:run
   ```

6. A aplicação estará disponível na URL: `http://localhost:8080`.

## Endpoints da API

A API oferece os seguintes endpoints para manipulação de pedidos:

- `GET /pedidos`: recupera todos os pedidos cadastrados.
- `GET /pedidos/{id}`: recupera um pedido específico pelo seu ID.
- `POST /pedidos`: cria um novo pedido com base nos dados fornecidos.
- `PUT /pedidos/{id}`: atualiza um pedido existente pelo seu ID.
- `DELETE /pedidos/{id}`: exclui um pedido existente pelo seu ID.

Certifique-se de utilizar uma ferramenta como o Postman ou cURL para fazer as requisições à API.