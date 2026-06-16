package com.ava.backend.config;

import com.ava.backend.model.*;
import com.ava.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private EntregaTarefaRepository entregaTarefaRepository;

    @Autowired
    private AvisoRepository avisoRepository;

    @Autowired
    private ProtocoloRepository protocoloRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only run if database is empty to prevent duplicates
        if (pessoaRepository.count() > 0) {
            return;
        }

        System.out.println("====== INICIANDO POPULAÇÃO DE DADOS INICIAIS (SEEDING) ======");

        // 1. Criar Secretária
        Secretaria sec = new Secretaria();
        sec.setNome("Maria Clara");
        sec.setEmail("secretaria@ficr.edu.br");
        sec.setCpf("111.111.111-11");
        sec.setSenha("123456");
        sec = secretariaRepository.save(sec);

        // 2. Criar Professores
        Professor profWallace = new Professor();
        profWallace.setNome("Wallace Felipe");
        profWallace.setEmail("wallace@ficr.edu.br");
        profWallace.setCpf("222.222.222-22");
        profWallace.setSenha("123456");
        profWallace = professorRepository.save(profWallace);

        Professor profJose = new Professor();
        profJose.setNome("Jose Gomes");
        profJose.setEmail("jose@ficr.edu.br");
        profJose.setCpf("333.333.333-33");
        profJose.setSenha("123456");
        profJose = professorRepository.save(profJose);

        Professor profMarcos = new Professor();
        profMarcos.setNome("Marcos Vinicius");
        profMarcos.setEmail("marcos@ficr.edu.br");
        profMarcos.setCpf("444.444.444-44");
        profMarcos.setSenha("123456");
        profMarcos = professorRepository.save(profMarcos);

        // 3. Criar Aluno (Thiago Ventura)
        Aluno alunoThiago = new Aluno();
        alunoThiago.setNome("Thiago Ventura");
        alunoThiago.setEmail("thiago@ficr.edu.br");
        alunoThiago.setCpf("555.555.555-55");
        alunoThiago.setSenha("123456");
        alunoThiago = alunoRepository.save(alunoThiago);

        // 4. Criar Disciplinas
        Disciplina ds = new Disciplina();
        ds.setNome("Desenvolvimento de Sistemas");
        ds.setProfessor(profWallace);
        ds.getAlunos().add(alunoThiago);
        ds = disciplinaRepository.save(ds);

        Disciplina bd = new Disciplina();
        bd.setNome("Banco de Dados II");
        bd.setProfessor(profJose);
        bd.getAlunos().add(alunoThiago);
        bd = disciplinaRepository.save(bd);

        Disciplina poo = new Disciplina();
        poo.setNome("Programação Orientada a Objetos");
        poo.setProfessor(profMarcos);
        poo.getAlunos().add(alunoThiago);
        poo = disciplinaRepository.save(poo);

        // Atualizar lista de disciplinas do aluno Thiago
        alunoThiago.getDisciplinas().add(ds);
        alunoThiago.getDisciplinas().add(bd);
        alunoThiago.getDisciplinas().add(poo);
        alunoThiago = alunoRepository.save(alunoThiago);

        // 5. Criar Tarefas
        // 5.1 Desenvolvimento de Sistemas (DS)
        Tarefa dsT1 = new Tarefa();
        dsT1.setTitulo("CRUD em Spring Boot");
        dsT1.setDescricao("Implementação de endpoints REST com persistência de dados.");
        dsT1.setDataEntrega(LocalDateTime.now().plusDays(2));
        dsT1.setPontosMaximos(10.0);
        dsT1.setDisciplina(ds);
        dsT1.setCriadoPor(profWallace);
        dsT1 = tarefaRepository.save(dsT1);

        Tarefa dsT2 = new Tarefa();
        dsT2.setTitulo("Front-end Kanban React");
        dsT2.setDescricao("Criação da interface dinâmica utilizando Tailwind e Context API.");
        dsT2.setDataEntrega(LocalDateTime.now().plusDays(1));
        dsT2.setPontosMaximos(15.0); // Conforme o mockup dsT2 é 15pts
        dsT2.setDisciplina(ds);
        dsT2.setCriadoPor(profWallace);
        dsT2 = tarefaRepository.save(dsT2);

        // 5.2 Banco de Dados II (BD)
        Tarefa bdT1 = new Tarefa();
        bdT1.setTitulo("Atividade 1: CRUD em Spring Boot");
        bdT1.setDescricao("Conectar Spring ao Postgres e validar chaves primárias.");
        bdT1.setDataEntrega(LocalDateTime.now().plusDays(5));
        bdT1.setPontosMaximos(10.0);
        bdT1.setDisciplina(bd);
        bdT1.setCriadoPor(profJose);
        bdT1 = tarefaRepository.save(bdT1);

        Tarefa bdT2 = new Tarefa();
        bdT2.setTitulo("Mapeamento ORM JPA");
        bdT2.setDescricao("Definição de entidades e relacionamentos entre tabelas.");
        bdT2.setDataEntrega(LocalDateTime.now().plusDays(5));
        bdT2.setPontosMaximos(5.0); // Conforme mockup bdT2 é 5pts
        bdT2.setDisciplina(bd);
        bdT2.setCriadoPor(profJose);
        bdT2 = tarefaRepository.save(bdT2);

        // 5.3 Programação Orientada a Objetos (POO)
        Tarefa pooT1 = new Tarefa();
        pooT1.setTitulo("Atividade 1: CRUD em Spring Boot");
        pooT1.setDescricao("Polimorfismo e encapsulamento em controllers.");
        pooT1.setDataEntrega(LocalDateTime.now().plusDays(6));
        pooT1.setPontosMaximos(10.0);
        pooT1.setDisciplina(poo);
        pooT1.setCriadoPor(profMarcos);
        pooT1 = tarefaRepository.save(pooT1);

        Tarefa pooT2 = new Tarefa();
        pooT2.setTitulo("Atividade 3: Front-end Kanban React");
        pooT2.setDescricao("Design de classes para gerenciar entregas.");
        pooT2.setDataEntrega(LocalDateTime.now().plusDays(7));
        pooT2.setPontosMaximos(10.0);
        pooT2.setDisciplina(poo);
        pooT2.setCriadoPor(profMarcos);
        pooT2 = tarefaRepository.save(pooT2);

        // 6. Criar Entregas (com notas e status específicos para bater com os mockups!)
        // DS: N1 = 8.5 (T1), N2 = 9.5 (T2)
        EntregaTarefa dsE1 = new EntregaTarefa();
        dsE1.setTarefa(dsT1);
        dsE1.setAluno(alunoThiago);
        dsE1.setStatus(StatusTarefa.ENTREGUE);
        dsE1.setRespostaText("https://github.com/ThiagoVenturaV/ds-crud-springboot");
        dsE1.setDataEntrega(LocalDateTime.now().minusDays(2));
        dsE1.setNota(8.5);
        dsE1.setFeedback("Excelente código estruturado, boas práticas JPA.");
        entregaTarefaRepository.save(dsE1);

        EntregaTarefa dsE2 = new EntregaTarefa();
        dsE2.setTarefa(dsT2);
        dsE2.setAluno(alunoThiago);
        dsE2.setStatus(StatusTarefa.ENTREGUE);
        dsE2.setRespostaText("https://github.com/ThiagoVenturaV/ds-kanban-react");
        dsE2.setDataEntrega(LocalDateTime.now().minusDays(1));
        dsE2.setNota(9.5);
        dsE2.setFeedback("Interface moderna e responsiva. Parabéns!");
        entregaTarefaRepository.save(dsE2);

        // BD: N1 = 9.0 (T1), T2 = PENDENTE (N2 Aguardando / Média Pendente)
        EntregaTarefa bdE1 = new EntregaTarefa();
        bdE1.setTarefa(bdT1);
        bdE1.setAluno(alunoThiago);
        bdE1.setStatus(StatusTarefa.ENTREGUE);
        bdE1.setRespostaText("Implementação do repositório utilizando JpaRepository.");
        bdE1.setDataEntrega(LocalDateTime.now().minusDays(3));
        bdE1.setNota(9.0);
        bdE1.setFeedback("Consultas JPQL corretas.");
        entregaTarefaRepository.save(bdE1);

        EntregaTarefa bdE2 = new EntregaTarefa();
        bdE2.setTarefa(bdT2);
        bdE2.setAluno(alunoThiago);
        bdE2.setStatus(StatusTarefa.PENDENTE); // Ainda pendente no Kanban
        entregaTarefaRepository.save(bdE2);

        // POO: N1 = 10.0 (T1), N2 = 10.0 (T2)
        EntregaTarefa pooE1 = new EntregaTarefa();
        pooE1.setTarefa(pooT1);
        pooE1.setAluno(alunoThiago);
        pooE1.setStatus(StatusTarefa.ENTREGUE);
        pooE1.setRespostaText("Uso de classes abstratas e interfaces corretamente aplicados.");
        pooE1.setDataEntrega(LocalDateTime.now().minusDays(4));
        pooE1.setNota(10.0);
        pooE1.setFeedback("Perfeito!");
        entregaTarefaRepository.save(pooE1);

        EntregaTarefa pooE2 = new EntregaTarefa();
        pooE2.setTarefa(pooT2);
        pooE2.setAluno(alunoThiago);
        pooE2.setStatus(StatusTarefa.ENTREGUE);
        pooE2.setRespostaText("Diagrama UML de classes criado.");
        pooE2.setDataEntrega(LocalDateTime.now().minusDays(3));
        pooE2.setNota(10.0);
        pooE2.setFeedback("Perfeito e no prazo!");
        entregaTarefaRepository.save(pooE2);

        // 7. Criar Avisos da Instituição (para bater com o Painel Geral)
        Aviso aviso1 = new Aviso();
        aviso1.setTitulo("Manutenção programada no portal");
        aviso1.setConteudo("Informamos que o sistema passará por atualização preventiva no dia 20/06, das 02h às 06h. O acesso poderá ficar instável.");
        aviso1.setDataPublicacao(LocalDateTime.now().minusHours(2));
        aviso1.setCriadoPor(sec);
        avisoRepository.save(aviso1);

        Aviso aviso2 = new Aviso();
        aviso2.setTitulo("Início do prazo de matrículas");
        aviso2.setConteudo("As renovações de matrícula para o próximo semestre letivo estarão disponíveis no painel financeiro a partir de amanhã.");
        aviso2.setDataPublicacao(LocalDateTime.now().minusDays(1));
        aviso2.setCriadoPor(sec);
        avisoRepository.save(aviso2);

        // 8. Criar Protocolos (Secretaria Digital)
        Protocolo prot1 = new Protocolo();
        prot1.setTipo(TipoProtocolo.PRORROGACAO_PRAZO);
        prot1.setDescricao("Prorrogação de Prazo - POO. Solicitação devido a problema de saúde.");
        prot1.setStatus(StatusProtocolo.EM_ANALISE);
        prot1.setAluno(alunoThiago);
        protocoloRepository.save(prot1);

        Protocolo prot2 = new Protocolo();
        prot2.setTipo(TipoProtocolo.TRANCAMENTO_DISCIPLINA);
        prot2.setDescricao("Trancamento de Disciplina por motivos de choque de horário.");
        prot2.setStatus(StatusProtocolo.DEFERIDO);
        prot2.setAluno(alunoThiago);
        prot2.setAnalisadoPor(sec);
        protocoloRepository.save(prot2);

        System.out.println("====== POPULAÇÃO DE DADOS INICIAIS FINALIZADA COM SUCESSO ======");
    }
}
