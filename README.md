# Banco BIC: Banco do Instituto de Computação.

**Repositório para o trabalho final da disciplina Programação Orientada a Objetos**

# O trabalho

O trabalho final tinha a proposta desenvolver uma aplicação orientada a objetos em Java. A Aplicação escolhida pelo grupo foi realizar um banco digital, dentro dos limites digitais do banco, sem poder uma verificação concreta de depósitos ou usuários com identificação inválidas.

# Versão do Java 

Utilizamos a [JDK 18](https://jdk.java.net/) para a realização desse trabalho. Deve ser utilizada essa versão.

# Como instalar e executar o BIC
```
git clone https://github.com/Asunnya/bic-poo
cd exec/jar
java -jar bic-poo.jar
```
## Possíveis erros na execução do .jar
Caso você tenha problema ao rodar o .jar, listamos alguns erros conhecidos
```
Error: LinkageError occurred while loading main class Main
        java.lang.UnsupportedClassVersionError: Main has been compiled by a more recent version of the Java Runtime (class file version 62.0), this version of the Java Runtime only recognizes class file versions up to 61.0
```
Caso você tenha o problema assima, utilize essas versões para rodar o -jar.

```
openjdk 18 2022-03-22
OpenJDK Runtime Environment (build 18+36-2087)
OpenJDK 64-Bit Server VM (build 18+36-2087, mixed mode, sharing)
```

# Funcionalidades do BIC para o Cliente

1. Tipos de Clientes diferentes, sendo, cliente pessoa e cliente empresa. 
2. Tipos de Contas diferentes dependendo da renda informada pelo cliente
3. Tipos de Cartões com limites diferentes, dependendo com da renda informada pelo cliente
4. Cada tipo de conta tem seu rendimento, tanto no saldo, tanto no dinheiro guardado, aumentando dependendo do tipo de conta.
5. A possibilidade de criação de outros cartões virtuais, atrelados ao um mesmo limite e uma mesma fatura. 
6. Agendamento de suas transações para um dia específico e pagamento por debito automático para não esquecer de pagar sua fatura e ter juros!
7. Caso for uma empresa, se mais de um gerente deseja acessar nosso banco, você pode simplesmente adicionar a identificação do usuário e o mesmo utiliza a senha da empresa.
8. Com a conta no bic, você rende seu saldo e caso tenha dinheiro guardado, rende muito mais!

# Recomendação de uso pelo código-fonte
Recomendamos a utilização da [IDE InteliJJ](https://www.jetbrains.com/idea/) para o projeto. Apenas porque foi essa utilizada pelos autores do projeto.

# O Projeto: Explicação focada para desenvolvedores.

1. Utilizamos regex em algumas verificações, mas não utilizamos nenhuma API para verificar realmente se aquele cpf é válido, lembrando, é uma aplicação focada no entendimento de orientação a objetos em java.
2. Utilizamos threads que atualizam o banco todos os dias, para assim, realizar os agendamentos de transações, os debitos automáticos de todas as contas.
3. Em relação a transferir e depositar, não usamos tantos sistemas para impedir de burlar diversas transferência, há um limite no depósitos que estipulamos para o usuário não depositar mais do que um valor estipulado com base na renda, enquanto o banco estiver aberto, isto é, o programa ativo localmente, ele não poderá burlar. Porém, abrindo a aplicação esse valor é atualizado e o mesmo pode continuar com seus depósitos.
4. A verificação de data consiste no horário e data local do computador que está rodando o programa. Caso haja a troca da data, a thread atualiza.
5. Nessa disciplina, é opcional o uso de Java Swing e seu aprendizado é por fora da matéria, optamos utilizar o terminal pela falta de tempo para o aprendizado do Swing.
6. Há claro, verificações caso o usuário coloque um valor inválido. São verificações simples com regex e tentando generalizar o que deve ser correto. Por favor, siga os formatos corretos de entrada que não terá problemas.
7. Nessa disciplina, não há ligação entre Java e banco de dados, por isso, utilizamos arquivos.
8. Estamos cientes que existem coisas que no mundo real seria um absurdo em um banco, por exemplo, só receber o valor depositado, porém não temos como verificar se houve realmente um dinheiro, isso é apenas um trabalho de uma disciplina, foque em como resolvemos um banco digital com suas limitações
9. Estamos também cientes de que, no mundo real, a fatura aumenta com a utilização do cartão, porém, não temos como também verificar se foi utilizado algum cartão porque não é esse o objetivo da aplicação, por isso, existe a opção de aumentar fatura, novamente, foque em como resolvemos um banco digital com suas limitações.

# Diagrama do projeto.
##### Transação: Gerencia as transações, sendo todas as transferências, pagamentos um tipo de transação. Boleto é uma transação, focada em gerar um boleto e pagar o mesmo, quando alguém paga um boleto gerado por outra pessoa, essa pessoa recebe o valor de quem pagou. Cada transação é única. Cada boleto, também é único
![Banco BIC-Transacao drawio](https://user-images.githubusercontent.com/56206429/178110439-84df9bb5-6240-4c54-a7ba-749777c5cfe1.svg)
##### Utils Bank: Gerencia as utilidades do Banco, há classes para gerenciamento de arquivos, gerenciamento do banco e geração aleatória. Toda a unicidade que existe entre o cliente e suas transações, são geradas pela geração aleatória.
![Banco BIC-UtilsBank drawio](https://user-images.githubusercontent.com/56206429/178110440-15312ab2-4ea1-41be-9bc6-69258b636423.svg)
#### Agência: A agência contém apenas uma instância, que é a própria agência, nela contém gerenciamento generícos para todos os usuários. Como renderConta()
![Banco BIC-Agencia drawio](https://user-images.githubusercontent.com/56206429/178110441-942e7aae-7493-412d-91f7-6fde81d13106.svg)
#### Conta: A conta em si, dita gerenciamentos que serão feitos para qualquer tipo de cliente que contém uma conta, seja qual for o tipo dela. A classe contém as funcioalidades e os atributos, por exemplo, o saldo, as chaves pix do cliente e a possibilidade de modificação
![Banco BIC-Conta drawio](https://user-images.githubusercontent.com/56206429/178110442-24cf94bf-5e67-4dd9-9140-91478d604666.svg)
##### Interfaces: A classe interface contém todos os gerenciamentos que irão ocorrer entre o usuário e a sua conta. As verificações são feitas pelo VerificarEntrada que são chamados pelo menu do usuário, cada funcionalidade tem seu menu e sua maneira de se comunicar com o usuário, criando uma classe que contém os dados para serem depois efetivamente utilizado pela conta.
![Banco BIC-Interfaces drawio](https://user-images.githubusercontent.com/56206429/178110445-e0136022-517c-4233-9cca-151762732761.svg)

