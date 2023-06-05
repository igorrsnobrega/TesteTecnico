# Teste Técnico - Projeto de Gerenciamento de Pedidos

## Descrição do Projeto

O projeto consiste em uma aplicação Java com Spring Boot para gerenciamento de pedidos. A aplicação permite o cadastro, consulta, atualização e exclusão de pedidos.

Este projeto utiliza as seguintes tecnologias:

- Java JDK 17
- H2 Database
- Spring Boot 3.1.0
- Docker

## Executando os testes unitários

Para executar os testes unitários, você pode rodar o seguinte comando:
- Linux: `./check_for_commit.sh`
- Windows: `./check_for_commit.bat`

Você também pode acompanhar a cobertura dos testes pelo caminho:
- `/teste/target/site/jacoco/index.html`

## Documentação da API

A documentação da API está disponível em:
- [Swagger UI - http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Pipeline de Integração Contínua

Você pode acompanhar a pipeline de Integração Contínua (CI) no menu "Actions" do GitHub ou no seguinte link:
- [GitHub Actions - https://github.com/igorrsnobrega/TesteTecnico/actions](https://github.com/igorrsnobrega/TesteTecnico/actions)

## Executando o Docker

Para executar o projeto usando o Docker, siga as etapas abaixo:

1. Certifique-se de ter o Docker instalado e em execução na sua máquina.

2. No diretório raiz do projeto, abra um terminal.

3. Execute o seguinte comando para construir a imagem Docker:

```shell
docker build -t teste_app .
```

4. Após concluir a construção da imagem, execute o seguinte comando para iniciar o contêiner Docker:

```shell
docker run -p 8080:8080 teste_app
```

Isso irá iniciar o contêiner Docker e disponibilizará a aplicação na URL: http://localhost:8080.

Certifique-se de que a porta 8080 esteja disponível para uso no seu sistema.