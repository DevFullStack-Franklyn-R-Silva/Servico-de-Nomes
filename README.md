# Serviço de Nomes

#### Disciplina: Sistema Distribuído 
#### Professo: Tércio Morais
#### Entrega: 10 de julho de 2022 às 23:59

## Descrição da Atividade

- O objetivo deste trabalho é construir um sistemas de nomes simples para dois serviços:
    1. Validação de CPF
    2. Cálculo do índice de massa corpórea (IMC) 
- A localização do serviço deverá ser transparente para o cliente
- O cliente deverá saber apenas o endereço e porta do serviço de nomes e o nome do serviço
- A figura em anexo mostra a sequência de eventos:
    1. Ao iniciar a aplicação servidora, esta deve se registrar no serviço de nomes (binding)​
    2. A aplicação cliente deverá solicitar ao serviço de nomes um dos dois serviços implementados (lookup), informando o seu nome
    3. Com a resposta do serviço de nomes, o cliente poderá executar a chamada remota do serviço.
- Trabalho em dupla







