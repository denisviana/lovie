# lovie app

Aplicativo para visualização de Filmes filtrados por Popularidade e pela Avaliação.

O app consome a api https://www.themoviedb.org/

Padrão de arquitetura utilizada: 
- MVP - Model, View, Presenter

o padrão MVP é um padrão já bastante utilizado no Android por promover um menor acoplamento entre entre os módulos e facilitar a realização de testes, além de tornar mais fácil a manutenção, legebilidade do cógido e permitir uma melhor implementação de novas funcionalidades.

- Gerenciador de dependências: Gradle

Bibliotecas:

- Gson 2.8.0: https://github.com/google/gson
Gson é uma biblioteca da Google utilizada para converter objetos Java em Json e vice-versa e foi utlizada no projeto por ser simples de se trabalhar e ao mesmo tempo muito eficiente.

- Retrofit2 2.2.0: http://square.github.io/retrofit/
Retrofit é uma biblioteca da Square para consumir APIs REST. Retrofit também é muito simples de implementar, trabalhadno em conjunto com diversas bibliotecas de serialização JSON, inclusive a GSON. Retrofit consegue fazer requisições GET, POST, PUT e DELETE e suporta diversos parâmetros para serem implementados em cada requisição

- Realm 3.1.1: https://realm.io/
Realm é uma biblioteca poderosa para fazer persistência no Android, e suporta outros sistemas operacionais também. Apesar de sua implementação ser parecida com de um ORM, o Realm é um banco de dados orientado a objetos e oferece mais efiência que o SQLite e outros ORMs utilizados no android, além de ser muito simples de implementar, permitindo uma maior organização no código, uma vez que com poucas linhas é possível implementar diversas operações de CRUD

- Picasso 2.5.2: http://square.github.io/picasso/
Também biblioteca da Square, o Picasso foi utlizado para fazer a manipulação das imagens que são recebidas nas chamadas da API. E também é permite uma implementação muito simples.



