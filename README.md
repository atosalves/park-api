-----

# Park-API

Bem-vindo ao **Park-API**, uma API robusta e segura para gerenciamento de estacionamentos. Desenvolvida com Spring Boot, esta aplicação oferece funcionalidades para usuários (clientes e administradores), vagas de estacionamento e operações de check-in/check-out.

-----

## Funcionalidades

A Park-API oferece as seguintes funcionalidades principais:

### Gerenciamento de Usuários (`UserController`)

  * **Criação de Usuários**: Permite o registro de novos usuários.
  * **Busca de Usuário por ID**: Recupera detalhes de um usuário específico.
  * **Atualização de Senha**: Permite que os usuários alterem suas senhas.
  * **Listagem de Todos os Usuários**: Retorna uma lista de todos os usuários (apenas para administradores).

### Gerenciamento de Vagas de Estacionamento (`ParkingSpotController`)

  * **Criação de Vagas**: Adiciona novas vagas de estacionamento ao sistema (apenas para administradores).
  * **Busca de Vaga por Código**: Recupera detalhes de uma vaga de estacionamento específica usando seu código (apenas para administradores).

### Gerenciamento de Estacionamento (`ParkingController`)

  * **Check-in de Veículos**: Registra a entrada de um veículo em uma vaga de estacionamento (apenas para administradores).
  * **Check-out de Veículos**: Registra a saída de um veículo, calculando a duração do estacionamento e o valor (apenas para administradores).
  * **Busca de Estacionamento por Recibo**: Permite consultar os detalhes de um estacionamento usando o número do recibo.
  * **Listagem de Estacionamentos por CPF**: Retorna o histórico de estacionamentos de um cliente específico (apenas para administradores).
  * **Listagem de Estacionamentos por Usuário**: Permite que o próprio cliente visualize seu histórico de estacionamentos.

### Gerenciamento de Clientes (`CustomerController`)

  * **Criação de Clientes**: Associa um usuário a um perfil de cliente.
  * **Busca de Cliente por ID**: Recupera detalhes de um cliente específico (apenas para administradores).
  * **Listagem de Todos os Clientes**: Retorna uma lista paginada de todos os clientes (apenas para administradores).
  * **Detalhes do Cliente Logado**: Permite que um cliente visualize seus próprios detalhes.

### Autenticação (`AuthController`)

  * **Autenticação de Usuário**: Permite que os usuários façam login e obtenham um token JWT para acessar a API.

-----

## Tecnologias e Ferramentas

Este projeto utiliza um conjunto robusto de tecnologias e ferramentas para garantir segurança, desempenho e manutenibilidade:

  * **Spring Boot (v3.5.3)**: Framework principal para o desenvolvimento da aplicação Java.
  * **Spring Security**: Para autenticação e autorização, incluindo controle de acesso baseado em roles (`ADMIN`, `CUSTOMER`) e JWT.
  * **Spring Data JPA**: Para persistência de dados e interação com o banco de dados.
  * **Maven**: Ferramenta de automação de build e gerenciamento de dependências.
  * **MySQL**: Banco de dados relacional para armazenamento dos dados da aplicação.
  * **Docker Compose**: Para orquestração e gerenciamento fácil do ambiente de banco de dados.
  * **JWT (JSON Web Tokens)**: Para autenticação segura e sem estado.
  * **Springdoc OpenAPI UI**: Para geração automática de documentação da API (Swagger UI).
  * **Lombok**: Reduz a verbosidade do código Java.
  * **Hibernate Validator (Jakarta Validation)**: Para validação de dados em DTOs.
  * **H2 Database**: Banco de dados em memória, útil para testes e desenvolvimento.
  * **Spring Boot DevTools**: Para agilizar o desenvolvimento com reinícios rápidos e hot-reloading.

-----

## Como Rodar o Projeto

Para configurar e executar o projeto localmente, siga os passos abaixo:

### Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

  * **Java Development Kit (JDK) 21 ou superior**
  * **Maven 3.x**
  * **Docker e Docker Compose**

### Configuração do Banco de Dados com Docker Compose

O arquivo `docker-compose.yml` já está configurado para levantar um contêiner MySQL:

```yaml
compose:
  services:
    mysql:
      image: "mysql:latest"
      environment:
        - "MYSQL_DATABASE=park_api"
        - "MYSQL_PASSWORD=password"
        - "MYSQL_ROOT_PASSWORD=password"
        - "MYSQL_USER=user"
      ports:
        - "3307:3306"
```

Para iniciar o contêiner do MySQL, execute o seguinte comando no diretório raiz do projeto (onde está o `pom.xml`):

```bash
docker-compose up -d
```

Este comando irá baixar a imagem do MySQL e iniciar o serviço na porta `3307` do seu host. Os dados do banco de dados `park_api` serão configurados automaticamente com as credenciais fornecidas.

### Configuração da Aplicação Spring Boot

1.  **Clone o Repositório** (se ainda não o fez):

    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd park-api
    ```

2.  **Configurações do Banco de Dados**:
    Certifique-se de que o arquivo `application.properties` (ou `application.yml`) em `src/main/resources` esteja configurado para conectar ao MySQL:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3307/park_api
    spring.datasource.username=user
    spring.datasource.password=password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    ```

3.  **Compilar e Executar**:
    No diretório raiz do projeto, execute o comando Maven para compilar e iniciar a aplicação:

    ```bash
    mvn spring-boot:run
    ```

    A aplicação estará disponível em `http://localhost:8080`.

-----

## Documentação da API (Swagger UI)

Após iniciar a aplicação, você pode acessar a documentação interativa da API (Swagger UI) através do seguinte link:

  * **[http://localhost:8080/swagger-ui.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)**

Aqui você poderá explorar todos os endpoints disponíveis, seus parâmetros, modelos de requisição/resposta e testá-los diretamente no navegador.
