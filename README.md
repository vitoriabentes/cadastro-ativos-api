# Cadastro de Ativos API

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)
![Gradle](https://img.shields.io/badge/Gradle-8.10.2-02303A.svg)
![Docker](https://img.shields.io/badge/Docker-20.10+-2496ED.svg)
![AWS](https://img.shields.io/badge/AWS-SQS-orange.svg)

Microsserviço responsável pelo gerenciamento do ciclo de vida dos ativos financeiros na plataforma de negociação. Fornece uma API RESTful com banco de dados dedicado, seguindo o padrão *Database per Service*, e se integra a outros domínios via chamadas síncronas (REST) e assíncronas (eventos).

## Escopo e Responsabilidades

Este microsserviço é o ponto central para a manutenção dos dados cadastrais dos ativos. Suas principais funções são:

- **CRUD Completo**: Oferece operações para criar, consultar, atualizar e remover ativos.
- **Sincronização por Eventos**: Publica mensagens na fila `Ativo Alterado` sempre que um ativo é criado ou atualizado. Isso notifica o domínio de precificação para iniciar ou encerrar o histórico de preços, mantendo a consistência entre os serviços.

## Arquitetura e Tecnologias

O projeto foi desenvolvido em **Java** com **Spring Boot** e segue os princípios de uma arquitetura de microsserviços.

- **Banco de Dados**: PostgreSQL, com um esquema dedicado e exclusivo para este serviço.
- **Comunicação**:
    - **Síncrona**: API RESTful para operações de cadastro.
    - **Assíncrona**: Publicação de eventos em fila SQS para desacoplamento e resiliência.
- **Infraestrutura**: Containerizado com **Docker**, inclui pipeline de integração contínua (GitHub Actions) para build e deploy.

## Contexto do Sistema

Este serviço faz parte de um sistema distribuído que simula uma plataforma de negociação de ativos, desenvolvido no contexto da disciplina **ACH2147 - Desenvolvimento de Sistemas de Informação Distribuídos** (EACH-USP). A plataforma completa gerencia a abertura e fechamento do mercado, a variação de preços em tempo real e a execução de ordens de compra e venda, garantindo a integridade das transações.