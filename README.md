# Cadastro de Ativos API

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)
![Gradle](https://img.shields.io/badge/Gradle-8.10.2-02303A.svg)
![Docker](https://img.shields.io/badge/Docker-20.10+-2496ED.svg)
![AWS](https://img.shields.io/badge/AWS-SQS-orange.svg)

Microsserviço para **CRUD de ativos** da plataforma de negociação. API RESTful com banco **PostgreSQL** dedicado, seguindo o padrão **Database per Service** e integrando com outros microsserviços via REST e eventos.

## Contexto geral
Este projeto foi desenvolvido como trabalho da disciplina Desenvolvimento de Sistemas de Informação Distribuídos (ACH2147) na EACH-USP. O projeto tem como objetivo simular, de forma parcial, o funcionamento de uma plataforma de negociação de ativos financeiros, reproduzindo o funcionamento do mercado de ações. 

Trata-se de um sistema distribuído que permite a negociação de ativos pelos investidores por meio de operações de compra e venda. As negociações somente poderão ser realizadas durante o período em que o mercado estiver aberto, refletindo o funcionamento da Bolsa de Valores do Brasil (B3) , onde a negociação acontece em uma janela pré-determinada de abertura e fechamento do mercado. Antes de ser negociado, cada ativo deverá ser previamente cadastrado na plataforma, entretanto, durante o período de negociação, o preço unitário poderá variar dinamicamente em função da oferta e da demanda, sendo atualizado em tempo real. A plataforma será responsável por monitorar continuamente as atualizações de preços dos ativos, gerenciar as negociações em tempo real e garantir a integridade e a consistência das transações realizadas. Para suprir essas funções, o sistema possui mecanismos de validação e resiliência capazes de impedir operações inválidas, como tentativas de negociação com preços desatualizados ou quantidade insuficiente de ativos disponíveis.

## Sobre o Microsserviço
O *cadastro-ativos-api* é o microsserviço responsável pelo CRUD de ativos da plataforma. Ele fornece uma API RESTful para gerenciar o ciclo de vida completo dos ativos financeiros que serão negociados, permitindo cadastrar novos ativos, consultar ativos existentes por código, nome ou indexador, atualizar informações e remover ativos da plataforma. O microsserviço também valida regras básicas de negócio, como código no padrão B3, valores positivos e campos obrigatórios não nulos.

Sempre que um novo ativo é cadastrado ou um ativo existente é atualizado, o microsserviço publica uma mensagem na fila Ativo Alterado para que o domínio de precificação possa iniciar o primeiro registro de precificação para ativos recém-cadastrados ou encerrar a vigência da precificação atual quando o ativo deixa de estar apto para negociação. Dessa forma, um registro é inserido no banco do domínio de precificação para cada atualização ou novo cadastro de ativo, garantindo que o histórico de precificação esteja sempre sincronizado com as alterações cadastrais.