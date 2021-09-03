# Movies List's
Uma Rest API com autenticação(JWT) para criar listas de filmes.

O cliente pode criar uma conta e, após se autenticar, poderá criar listas para adicionar filmes.

Filmes são fornecidos pela [The Movie Database(TMDB)](https://www.themoviedb.org/)

## Stack
Tecnologias utilizadas para o desenvolvimento do projeto

- Java 11
- Spring boot 2.5.4
- MySQL 5.7
- Liquibase 4.4.3

## Start App
Após se cadastrar no site da TMDB e gerar seu token(JWT) para autenticação na API, será necessário atualizar a variável de ambiente no arquivo `application.properties` com o token gerado pelo site.
``` properties
app.api.token=${TOKEN}
```

Veja mais em: [TMDB API](https://developers.themoviedb.org/3/getting-started/introduction)

As credenciais de conexão do banco também precisam ser informadas no arquivo de configuração: `application.properties`
``` properties
# Host e nome da tabela, no caso "movies
spring.datasource.url=jdbc:mysql://localhost:3306/movies 
spring.datasource.username=${USERNAME_DB}
spring.datasource.password=${PASSWORD_DB}
```

> É necesário ter o MySQL 5.7 instalado(ou através do docker) com um banco chamado "movies", caso não seja alterado no arquivo de configuração

#### Init
Inicializando o projeto e utilizando o Maven wrapper através do terminal:
``` bash
./mvnw clean install
```

ou no Windows

``` cmd
mvnw.cmd clean install
```

#### Migration
Durante a primeira execução do projeto as tabelas serão criadas automaticamente pelo Liquibase.
> Desde que a conexão seja informada, conforme mostrando anteriormente

