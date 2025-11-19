# event sourcing tutorial

## O que é?
Event Sourcing é um padrão arquitetural onde o estado de uma aplicação é derivado de uma sequência de eventos imutáveis.
Cada mudança de estado é armazenada como um evento, e o estado atual é reconstruído a partir do replay desses eventos. 

## Conceitos demonstrados

Definição de Eventos: classes que representam mudanças significativas no domínio.

Aggregates: responsáveis por receber comandos, aplicar regras de negócio e gerar eventos.

Event Store simples: armazenamento em memória ou em arquivo, onde eventos são persistidos de forma append-only.

Replay de eventos: reconstrução do estado de uma entidade a partir da sequência de eventos registrados.

Projetores / Read Models: como projetar os eventos para modelos de leitura, se estiver implementado.

## Como rodar no IntelliJ

- clone o repositório
- sincronize as dependências
- compile e rode o projeto

## Versões

- Ponto de Partida (Base para Event Sourcing): Acesse  a branch `tutorial`

- Versão Final (Implementação Completa): Acesse a branch `main`
