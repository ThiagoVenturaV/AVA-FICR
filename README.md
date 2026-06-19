# AVA FICR - Ambiente Virtual de Aprendizagem (API Backend)

Esta é a API Backend do **AVA (Ambiente Virtual de Aprendizagem)** desenvolvida para fins acadêmicos. O projeto foi construído utilizando **Spring Boot 3.x**, **PostgreSQL** e **Docker**, aplicando conceitos avançados de Orientação a Objetos (POO) e modelagem de banco de dados relacional.

---

## 🛠️ Tecnologias Utilizadas

* **Java 21** (JDK 21)
* **Spring Boot 3.x**
  * Spring Data JPA
  * Spring Web (REST)
  * Spring Validation
  * Spring Security Crypto (Argon2 Hashing + Bouncy Castle)
* **PostgreSQL** (Banco de dados relacional)
* **Docker & Docker Compose** (Containerização)
* **Lombok** (Produtividade/Redução de Boilerplate)

---

## 📐 Arquitetura do Projeto

O projeto segue a arquitetura em camadas padrão do Spring Boot (`Controller -> Service -> Repository -> Entity`):

* **`model`**: Contém as entidades JPA. Utiliza a estratégia de herança **`SINGLE_TABLE`** a partir da classe abstrata `Pessoa` para representar `Aluno`, `Professor` e `Secretaria` de forma coesa no banco de dados.
* **`service`**: Camada que encapsula todas as validações e regras de negócio do AVA.
* **`repository`**: Interfaces que estendem o Spring Data JPA para comunicação simplificada com o banco de dados.
* **`controller`**: Controladores REST responsáveis por expor os endpoints da API (habilitados com CORS para integração com o Front-end).
* **`dto`**: Objetos de transferência de dados para padronizar as requisições e respostas da API.
* **`exception`**: Tratamento global de erros da API.

---

## ⚙️ Regras de Negócio Implementadas

1. **População Automática de Atividades (Quadro Kanban):** 
   Ao criar uma nova tarefa (seja via Professor ou Secretaria), o sistema mapeia a disciplina vinculada e gera automaticamente registros de entrega com status `PENDENTE` para todos os alunos matriculados.
2. **Autorização Simplificada:** 
   Apenas professores responsáveis pela disciplina ou secretários podem publicar tarefas. Alunos não possuem essa permissão.
3. **Limitação de Notas:** 
   A nota dada pelo professor a uma entrega não pode ser negativa e não pode exceder o valor de pontos máximos definido para a tarefa.
4. **Cálculo Dinâmico de Média (Boletim):** 
   A média do aluno na disciplina é calculada com base na média aritmética simples das tarefas que já foram avaliadas pelo professor. Se houver tarefas com status diferente de `ENTREGUE` ou ainda não avaliadas, a média e o status da matéria ficam como `PENDENTE`.
5. **Secretaria Digital (Protocolos):** 
   Alunos podem criar chamados de secretaria (Ex: Trancamento, Prorrogação de Prazo). Apenas usuários da `Secretaria` podem analisar e marcar como `DEFERIDO` ou `INDEFERIDO`.

---

## 🚀 Como Executar o Projeto com Docker

Para facilitar a correção pelo professor ou execução em qualquer ambiente, a aplicação e o banco de dados PostgreSQL rodam de forma integrada usando Docker.

### Pré-requisitos
* Ter o **Docker** e o **Docker Compose** instalados no computador.

### Passo a Passo
1. Abra um terminal na pasta raiz do projeto.
2. Execute o comando:
   ```bash
   docker compose up --build
   ```
3. A aplicação estará ativa e escutando na porta **`8080`**. O banco PostgreSQL estará ativo na porta **`5432`**.

---

## 📖 Documentação Interativa da API (Swagger / OpenAPI)

Para visualizar todos os endpoints detalhadamente e testá-los diretamente pelo navegador, a API conta com a interface do **Swagger UI**.

* **URL de Acesso:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Com o Swagger, você pode:
* Visualizar os schemas de entrada e saída (DTOs) com suas validações.
* Executar as chamadas HTTP (POST, GET, PUT, DELETE) com dados reais de teste diretamente pelo navegador utilizando o botão **"Try it out"**.

---

## 🔒 Segurança e Hashing de Senhas (Argon2)

Para garantir a segurança das credenciais no banco de dados:
* Implementamos o algoritmo de hashing **Argon2** (Argon2id) utilizando a biblioteca `spring-security-crypto` com suporte do provider de criptografia **Bouncy Castle**.
* Todas as senhas de novos cadastros são convertidas em hash de forma segura antes de persistidas.
* **Migração Automática de Senhas:** Caso o banco já tenha sido iniciado anteriormente com senhas em texto plano (`ddl-auto=update`), a aplicação executará uma rotina automática na inicialização (`DataInitializer`) que detecta senhas legadas não criptografadas e as atualiza automaticamente para hashes Argon2, garantindo que o login funcione perfeitamente sem necessidade de limpar o banco.

---

## ⚡ Carga de Dados Inicial (Seed)

Ao iniciar a aplicação, um script de seeding (`DataInitializer`) pré-popula o banco de dados com as seguintes credenciais (todas devidamente criptografadas com Argon2):
* **Aluno:** Thiago Ventura (Email: `thiago@ficr.edu.br` / Senha original: `123456`)
* **Professores:** Wallace Felipe, Jose Gomes, Marcos Vinicius (Emails: `wallace@ficr.edu.br`, `jose@ficr.edu.br`, `marcos@ficr.edu.br` / Senhas: `123456`)
* **Secretária:** Maria Clara (Email: `secretaria@ficr.edu.br` / Senha: `123456`)
* **Disciplinas:** Desenvolvimento de Sistemas, Banco de Dados II, Programação Orientada a Objetos (com o Aluno Thiago matriculado em todas).
* **Tarefas & Notas:** Tarefas pré-cadastradas e avaliadas correspondendo aos painéis visuais do frontend.

---

## 🧪 Principais Endpoints da API

Abaixo estão os endpoints e exemplos de requisição via `CURL`.

### 1. Autenticação (Login)
* **Método:** `POST`
* **URL:** `/api/auth/login`
* **Exemplo de Body:**
```json
{
  "email": "thiago@ficr.edu.br",
  "senha": "123456"
}
```

### 2. Quadro de Atividades (Kanban do Aluno)
* **Método:** `GET`
* **URL:** `/api/entregas/aluno/{alunoId}`
* **Exemplo:** `/api/entregas/aluno/5` (Retorna a lista de tarefas do aluno Thiago com seus respectivos status: `PENDENTE`, `EM_ANDAMENTO`, `ENTREGUE`).

### 3. Submeter Resposta da Tarefa (Aluno)
* **Método:** `PUT`
* **URL:** `/api/entregas/{entregaId}/entregar`
* **Exemplo de Body:**
```json
{
  "respostaText": "https://github.com/ThiagoVenturaV/ds-crud-springboot"
}
```

### 4. Lançar Nota (Professor)
* **Método:** `PUT`
* **URL:** `/api/entregas/{entregaId}/avaliar`
* **Exemplo de Body:**
```json
{
  "nota": 9.5,
  "feedback": "Interface muito bem desenvolvida!",
  "professorId": 2
}
```

### 5. Boletim / Rendimento Escolar
* **Método:** `GET`
* **URL:** `/api/entregas/aluno/{alunoId}/disciplina/{disciplinaId}/rendimento`
* **Exemplo:** `/api/entregas/aluno/5/disciplina/1/rendimento`

### 6. Avisos da Instituição (Painel Geral)
* **Método:** `GET`
* **URL:** `/api/avisos`

### 7. Protocolos da Secretaria (Visualizar/Criar)
* **Método:** `POST`
* **URL:** `/api/protocolos`
* **Exemplo de Body:**
```json
{
  "tipo": "PRORROGACAO_PRAZO",
  "descricao": "Solicito prorrogação devido a motivos de saúde.",
  "alunoId": 5
}
```
