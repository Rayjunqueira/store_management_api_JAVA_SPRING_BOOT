# API de gerenciamento de loja em Java Spring Boot + Postgresql e Docker.

# Sobre o Projeto

API desenvolvida em Java/Spring boot com cadastro de categorias, produtos, categorias de clientes, categorias de produtos, pedidos e transações.

# Features
 <p>◼ Segurança de criptografia de senhas e dados sensíveis utilizando BCrypt Encoder. </p>
 ◼ Segurança de login de usuário utilizando JWT e Spring Security (Json Web Token). </p>
 ◼ Mapeamento de objetos de DTO e Model através da biblioteca mapstruct. </p>
 ◼ Busca de dados em cachê utilizando redis. </p>
 ◼ Armazenamento em banco de dados postgres utilizando a biblioteca do Postgres. </p>
 ◼ Migrations de tabelas e colunas ao banco de dados através da biblioteca flyway. </p>
 ◼ Uso de containers docker pela pasta dockerfile. </p>
 ◼ Cálculo automático de estoque de produtos por quantidade em pedido. </p>
 ◼ CRUD completo das principais rotas da API. </p>
 ◼ Projeto todo desenvolvido em arquitetura MVC. </p>

# Rodando a aplicação na sua máquina

1 - Clone o repositório

    git clone git@github.com:Rayjunqueira/store_management_api_JAVA_SPRING_BOOT.git
    
2 - Acesse a pasta clonada
    cd nome-do-repositorio

3 - Execute o comando de construção e instalação (Maven):
    mvn clean install
    
4 - Execute o aplicativo Spring Boot:
    mvn spring-boot:run

# Configurarando Banco de Dados Postgres
