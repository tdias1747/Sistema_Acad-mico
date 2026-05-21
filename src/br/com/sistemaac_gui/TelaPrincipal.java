package br.com.sistemaac_gui;

import br.com.sistemaac_dao.AlunoDAO;
import br.com.sistemaac_dao.CursoDAO;
import br.com.sistemaac_dao.Notas_e_FaltasDAO;
import br.com.sistemaac_modelo.Aluno;
import br.com.sistemaac_modelo.Curso;
import br.com.sistemaac_modelo.Notas_e_Faltas;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

    private JTabbedPane abas;
    
    
    private JTextField txtRgm, txtNome, txtEmail, txtEndereco, txtMunicipio;
    private JFormattedTextField txtDataNasc, txtCpf, txtCelular;
    private JComboBox<String> cbUf;
    
    
    private JComboBox<String> cbCurso, cbCampus;
    private JRadioButton rbMatutino, rbVespertino, rbNoturno;
    private ButtonGroup bgPeriodo;

   
    private JTextField txtNota, txtFaltas;
    private JComboBox<String> cbDisciplina, cbSemestre;
    private JTextField txtRgmNotas, txtNomeNotasExibe, txtCursoNotasExibe;

 
    private JTextArea txtBoletim;

    
    private AlunoDAO alunoDAO = new AlunoDAO();
    private CursoDAO cursoDAO = new CursoDAO();
    private Notas_e_FaltasDAO notasDAO = new Notas_e_FaltasDAO();

    public TelaPrincipal() {
        setTitle("Sistema Acadêmico");
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        
        configurarMenuSuperior();

        
        abas = new JTabbedPane();
        abas.setBounds(10, 10, 615, 280);

        configurarDadosPessoais();
        configurarCurso();
        configurarNotasFaltas();
        configurarBoletim();

        getContentPane().add(abas);
        
        
        configurarBarraBotoes();
    }

    private void aplicarEstiloQuadrado(JComponent... campos) {
        for (JComponent campo : campos) {
            campo.setBackground(Color.WHITE);
            campo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); 
        }
    }

    private void configurarMenuSuperior() {
        JMenuBar barraMenu = new JMenuBar();
        
        JMenu menuAluno = new JMenu("Aluno");
        JMenuItem miSalvar = new JMenuItem("Salvar");
        JMenuItem miAlterar = new JMenuItem("Alterar");
        JMenuItem miExcluir = new JMenuItem("Excluir");
        JMenuItem miConsultar = new JMenuItem("Consultar");
        JMenuItem miSair = new JMenuItem("Sair");
        
        menuAluno.add(miSalvar);
        menuAluno.add(miAlterar);
        menuAluno.add(miExcluir);
        menuAluno.add(miConsultar);
        menuAluno.add(new JSeparator());
        menuAluno.add(miSair);

        JMenu menuNotas = new JMenu("Notas e Faltas");
        JMenuItem miSalvarNota = new JMenuItem("Salvar Nota");
        menuNotas.add(miSalvarNota);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem miSobre = new JMenuItem("Sobre");
        menuAjuda.add(miSobre);

        barraMenu.add(menuAluno);
        barraMenu.add(menuNotas);
        barraMenu.add(menuAjuda);
        setJMenuBar(barraMenu);

        miSair.addActionListener(e -> System.exit(0));
        miSobre.addActionListener(e -> JOptionPane.showMessageDialog(this, "SISTEMA ACADEMICO", "Sobre", JOptionPane.INFORMATION_MESSAGE));
        
        miSalvar.addActionListener(this::acaoSalvar);
        miConsultar.addActionListener(this::acaoConsultar);
        miAlterar.addActionListener(this::acaoAlterar);
        miExcluir.addActionListener(this::acaoExcluir);
        miSalvarNota.addActionListener(this::acaoSalvar);
    }

    private void configurarDadosPessoais() {
        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel lblRgm = new JLabel("RGM"); lblRgm.setBounds(15, 20, 40, 20); painel.add(lblRgm);
        txtRgm = new JTextField(); txtRgm.setBounds(60, 20, 120, 22); painel.add(txtRgm);

        txtRgm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); 
                }
            }
        });

        JLabel lblNome = new JLabel("Nome"); lblNome.setBounds(200, 20, 40, 20); painel.add(lblNome);
        txtNome = new JTextField(); txtNome.setBounds(245, 20, 340, 22); painel.add(txtNome);

        JLabel lblDataNasc = new JLabel("Data de Nascimento"); lblDataNasc.setBounds(15, 60, 130, 20); painel.add(lblDataNasc);
        try { txtDataNasc = new JFormattedTextField(new MaskFormatter("##/##/####")); } catch (Exception e) {}
        txtDataNasc.setBounds(140, 60, 90, 22); painel.add(txtDataNasc);

        JLabel lblCpf = new JLabel("CPF"); lblCpf.setBounds(250, 60, 30, 20); painel.add(lblCpf);
        try { txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##")); } catch (Exception e) {}
        txtCpf.setBounds(285, 60, 120, 22); painel.add(txtCpf);

        JLabel lblEmail = new JLabel("Email"); lblEmail.setBounds(15, 100, 40, 20); painel.add(lblEmail);
        txtEmail = new JTextField(); txtEmail.setBounds(60, 100, 525, 22); painel.add(txtEmail);

        JLabel lblEnd = new JLabel("Endereço"); lblEnd.setBounds(15, 140, 70, 20); painel.add(lblEnd);
        txtEndereco = new JTextField(); txtEndereco.setBounds(85, 140, 500, 22); painel.add(txtEndereco);

        JLabel lblMuni = new JLabel("Município"); lblMuni.setBounds(15, 180, 60, 20); painel.add(lblMuni);
        txtMunicipio = new JTextField(); txtMunicipio.setBounds(80, 180, 200, 22); painel.add(txtMunicipio);

        JLabel lblUf = new JLabel("UF"); lblUf.setBounds(295, 180, 20, 20); painel.add(lblUf);
        String[] ufs = {"SP", "RJ", "MG", "PR", "SC", "RS", "BA", "DF", "GO"};
        cbUf = new JComboBox<>(ufs); cbUf.setBounds(320, 180, 50, 22); painel.add(cbUf);

        JLabel lblCel = new JLabel("Celular"); lblCel.setBounds(385, 180, 50, 20); painel.add(lblCel);
        try { txtCelular = new JFormattedTextField(new MaskFormatter("(##)#####-####")); } catch (Exception e) {}
        txtCelular.setBounds(435, 180, 150, 22); painel.add(txtCelular);

        aplicarEstiloQuadrado(txtRgm, txtNome, txtDataNasc, txtCpf, txtEmail, txtEndereco, txtMunicipio, cbUf, txtCelular);

        abas.addTab("Dados Pessoais", painel);
    }

    private void configurarCurso() {
        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel lblCurso = new JLabel("Curso"); lblCurso.setBounds(20, 30, 50, 20); painel.add(lblCurso);
        String[] cursos = {"Análise e Desenvolvimento de Sistemas", "Ciência da Computação", "Engenharia de Software"};
        cbCurso = new JComboBox<>(cursos); cbCurso.setBounds(80, 30, 450, 22); painel.add(cbCurso);

        JLabel lblCampus = new JLabel("Campus"); lblCampus.setBounds(20, 70, 50, 20); painel.add(lblCampus);
        String[] campus = {"Tatuapé", "Carrão", "Vila Lobos"};
        cbCampus = new JComboBox<>(campus); cbCampus.setBounds(80, 70, 450, 22); painel.add(cbCampus);

        JLabel lblPeriodo = new JLabel("Período"); lblPeriodo.setBounds(20, 120, 50, 20); painel.add(lblPeriodo);
        rbMatutino = new JRadioButton("Matutino"); rbMatutino.setBounds(80, 120, 90, 20);
        rbVespertino = new JRadioButton("Vespertino"); rbVespertino.setBounds(180, 120, 100, 20);
        rbNoturno = new JRadioButton("Noturno", true); rbNoturno.setBounds(290, 120, 90, 20);
        
        bgPeriodo = new ButtonGroup();
        bgPeriodo.add(rbMatutino); bgPeriodo.add(rbVespertino); bgPeriodo.add(rbNoturno);
        painel.add(rbMatutino); painel.add(rbVespertino); painel.add(rbNoturno);

        aplicarEstiloQuadrado(cbCurso, cbCampus);

        abas.addTab("Curso", painel);
    }

    private void configurarNotasFaltas() {
        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel lblRgmN = new JLabel("RGM Aluno"); lblRgmN.setBounds(20, 15, 80, 20); painel.add(lblRgmN);
        txtRgmNotas = new JTextField(); txtRgmNotas.setBounds(100, 15, 100, 22); painel.add(txtRgmNotas);
        
        txtRgmNotas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); 
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                vinculaRgmANotas();
            }
        });
        
        txtNomeNotasExibe = new JTextField("");
        txtNomeNotasExibe.setBounds(210, 15, 370, 22);
        txtNomeNotasExibe.setEditable(false);
        txtNomeNotasExibe.setForeground(Color.BLUE);
        painel.add(txtNomeNotasExibe);

        txtCursoNotasExibe = new JTextField("");
        txtCursoNotasExibe.setBounds(20, 45, 560, 22);
        txtCursoNotasExibe.setEditable(false);
        txtCursoNotasExibe.setForeground(Color.BLUE);
        painel.add(txtCursoNotasExibe);

        JLabel lblDisc = new JLabel("Disciplina"); lblDisc.setBounds(20, 90, 70, 20); painel.add(lblDisc);
        String[] disc = {"Programação Orientada a Objetos", "Estrutura de Dados", "Bancos de Dados"};
        cbDisciplina = new JComboBox<>(disc); cbDisciplina.setBounds(100, 90, 480, 22); painel.add(cbDisciplina);

        JLabel lblSem = new JLabel("Semestre"); lblSem.setBounds(20, 130, 70, 20); painel.add(lblSem);
        String[] sem = {"2026-1", "2026-2", "2027-1", "2027-2"};
        cbSemestre = new JComboBox<>(sem); cbSemestre.setBounds(100, 130, 110, 22); painel.add(cbSemestre);

        JLabel lblNota = new JLabel("Nota"); lblNota.setBounds(240, 130, 40, 20); painel.add(lblNota);
        txtNota = new JTextField(); txtNota.setBounds(280, 130, 60, 22); painel.add(txtNota);

        JLabel lblFaltas = new JLabel("Faltas"); lblFaltas.setBounds(370, 130, 40, 20); painel.add(lblFaltas);
        txtFaltas = new JTextField(); txtFaltas.setBounds(420, 130, 60, 22); painel.add(txtFaltas);

        aplicarEstiloQuadrado(txtRgmNotas, txtNomeNotasExibe, txtCursoNotasExibe, cbDisciplina, cbSemestre, txtNota, txtFaltas);

        abas.addTab("Notas e Faltas", painel);
    }

    private void vinculaRgmANotas() {
        String rgm = txtRgmNotas.getText().trim();
        if (rgm.length() >= 4) {
            try {
                Aluno a = alunoDAO.consultar(rgm);
                if (a != null) {
                    txtNomeNotasExibe.setText(a.getNome());
                    Curso c = cursoDAO.consultar(rgm);
                    if (c != null) {
                        txtCursoNotasExibe.setText(c.getCurso() + " - " + c.getCampus());
                    } else {
                        txtCursoNotasExibe.setText("Aluno sem curso cadastrado.");
                    }
                } else {
                    txtNomeNotasExibe.setText("Aluno não encontrado.");
                    txtCursoNotasExibe.setText("");
                }
            } catch (Exception ex) {}
        } else {
            txtNomeNotasExibe.setText("");
            txtCursoNotasExibe.setText("");
        }
    }

    private void configurarBoletim() {
        JPanel painel = new JPanel();
        painel.setLayout(null);

        txtBoletim = new JTextArea();
        txtBoletim.setEditable(false);
        txtBoletim.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        aplicarEstiloQuadrado(txtBoletim);
        
        JScrollPane scroll = new JScrollPane(txtBoletim);
        scroll.setBounds(10, 10, 590, 230);
        scroll.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        painel.add(scroll);

        abas.addTab("Boletim", painel);
    }

    private void configurarBarraBotoes() {
        JButton btnSalvar = new JButton("Salvar"); btnSalvar.setBounds(40, 305, 95, 25);
        JButton btnConsultar = new JButton("Consultar RGM"); btnConsultar.setBounds(145, 305, 130, 25);
        JButton btnAlterar = new JButton("Alterar"); btnAlterar.setBounds(285, 305, 95, 25);
        JButton btnExcluir = new JButton("Excluir"); btnExcluir.setBounds(390, 305, 95, 25);
        JButton btnSair = new JButton("Sair"); btnSair.setBounds(495, 305, 95, 25);

        getContentPane().add(btnSalvar);
        getContentPane().add(btnConsultar);
        getContentPane().add(btnAlterar);
        getContentPane().add(btnExcluir);
        getContentPane().add(btnSair);

        btnSair.addActionListener(e -> System.exit(0));
        btnSalvar.addActionListener(this::acaoSalvar);
        btnConsultar.addActionListener(this::acaoConsultar);
        btnAlterar.addActionListener(this::acaoAlterar);
        btnExcluir.addActionListener(this::acaoExcluir);
    }

    private void acaoSalvar(ActionEvent e) {
        int abaAtual = abas.getSelectedIndex();
        String rgm = (abaAtual == 2) ? txtRgmNotas.getText().trim() : txtRgm.getText().trim();
        
        if (rgm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: O campo RGM é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (abaAtual == 0) {
                String cpfLimpo = txtCpf.getText().replace(".", "").replace("-", "").trim();
                if (txtNome.getText().trim().isEmpty() || cpfLimpo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar: Nome e CPF não podem ficar em branco!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                
                try {
                    String dataTexto = txtDataNasc.getText();
                    if (!dataTexto.replace("/", "").trim().isEmpty()) { 
                        String[] partes = dataTexto.split("/");
                        int dia = Integer.parseInt(partes[0].trim());
                        int mes = Integer.parseInt(partes[1].trim());
                        
                        if (dia < 1 || dia > 31 || mes < 1 || mes > 12) {
                            JOptionPane.showMessageDialog(this, "Data de Nascimento inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);
                            return; 
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Preencha a data de nascimento corretamente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Aluno aluno = new Aluno(rgm, txtNome.getText(), txtDataNasc.getText(), txtCpf.getText(), txtEmail.getText(), txtEndereco.getText(), txtMunicipio.getText(), cbUf.getSelectedItem().toString(), txtCelular.getText());
                alunoDAO.salvar(aluno);
                
                JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            } else if (abaAtual == 1) {
                String periodo = rbMatutino.isSelected() ? "Matutino" : rbVespertino.isSelected() ? "Vespertino" : "Noturno";
                Curso curso = new Curso(rgm, cbCurso.getSelectedItem().toString(), cbCampus.getSelectedItem().toString(), periodo);
                cursoDAO.salvarEAtualizar(curso);
                
                JOptionPane.showMessageDialog(this, "Dados do curso salvos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            } else if (abaAtual == 2) {
                if (txtNota.getText().trim().isEmpty() || txtFaltas.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar: Digite a Nota e as Faltas!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double notaDigitada = Double.parseDouble(txtNota.getText().replace(",", "."));
                int faltasDigitadas = Integer.parseInt(txtFaltas.getText().trim());
                
                double notaPositiva = Math.abs(notaDigitada);
                int faltasPositivas = Math.abs(faltasDigitadas);

                Notas_e_Faltas nf = new Notas_e_Faltas(rgm, cbDisciplina.getSelectedItem().toString(), cbSemestre.getSelectedItem().toString(), notaPositiva, faltasPositivas);
                notasDAO.salvar(nf);
                
                JOptionPane.showMessageDialog(this, "Notas e faltas registradas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                txtNota.setText(""); txtFaltas.setText("");
            }
        } catch (Exception ex) {
            String senderError = ex.getMessage();
            if (senderError.contains("Duplicate entry")) {
                senderError = "Este CPF ou RGM já está cadastrado no sistema!";
            }
            
            JOptionPane.showMessageDialog(this, "Não foi possível salvar!\nMotivo: " + senderError, "Erro ao Salvar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoConsultar(ActionEvent e) {
        String rgm = txtRgm.getText().trim();
        if (rgm.isEmpty()) rgm = txtRgmNotas.getText().trim();
        
        if (rgm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Insira um RGM para buscar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Aluno aluno = alunoDAO.consultar(rgm);
            if (aluno != null) {
                txtRgm.setText(aluno.getRgm());
                txtRgmNotas.setText(aluno.getRgm());
                txtNome.setText(aluno.getNome());
                txtNomeNotasExibe.setText(aluno.getNome());
                txtDataNasc.setText(aluno.getDataDeNascimento());
                txtCpf.setText(aluno.getCpf());
                txtEmail.setText(aluno.getEmail());
                txtEndereco.setText(aluno.getEndereco());
                txtMunicipio.setText(aluno.getMunicipio());
                cbUf.setSelectedItem(aluno.getUf());
                txtCelular.setText(aluno.getCelular());
            } else {
                JOptionPane.showMessageDialog(this, "Aluno não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Curso curso = cursoDAO.consultar(rgm);
            if (curso != null) {
                cbCurso.setSelectedItem(curso.getCurso());
                cbCampus.setSelectedItem(curso.getCampus());
                txtCursoNotasExibe.setText(curso.getCurso() + " - " + curso.getCampus());
                if (curso.getPeriodo().equals("Matutino")) rbMatutino.setSelected(true);
                else if (curso.getPeriodo().equals("Vespertino")) rbVespertino.setSelected(true);
                else rbNoturno.setSelected(true);
            }

            List<Notas_e_Faltas> list = notasDAO.listarpeloRgm(rgm);
            StringBuilder sb = new StringBuilder();
            sb.append("**************************************************\n");
            sb.append("               BOLETIM ACADÊMICO                  \n");
            sb.append("**************************************************\n");
            sb.append("RGM: ").append(aluno.getRgm()).append("\n");
            sb.append("Aluno: ").append(aluno.getNome()).append("\n");
            if (curso != null) {
                sb.append("Curso: ").append(curso.getCurso()).append("\n");
            }
            sb.append("===================================================\n");
            sb.append(String.format("%-25s %-10s %-6s %-6s\n", "Disciplina", "Semestre", "Nota", "Faltas"));
            sb.append("===================================================\n");
            
            for (Notas_e_Faltas n : list) {
                double notaExibida = Math.abs(n.getNota());
                int faltasExibidas = Math.abs(n.getFaltas());
                
                sb.append(String.format("%-25s %-10s %-6.2f %-6d\n", n.getDisciplina(), n.getSemestre(), notaExibida, faltasExibidas));
            }
            sb.append("==================================================\n");
            txtBoletim.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoAlterar(ActionEvent e) {
        String rgm = txtRgm.getText().trim();
        if (rgm.isEmpty()) return;
        try {
            Aluno aluno = new Aluno(rgm, txtNome.getText(), txtDataNasc.getText(), txtCpf.getText(), txtEmail.getText(), txtEndereco.getText(), txtMunicipio.getText(), cbUf.getSelectedItem().toString(), txtCelular.getText());
            alunoDAO.alterar(aluno);
            JOptionPane.showMessageDialog(this, "Cadastro alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoExcluir(ActionEvent e) {
        String rgm = txtRgm.getText().trim();
        if (rgm.isEmpty()) return;
        int conf = JOptionPane.showConfirmDialog(this, "Excluir este aluno?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            try {
                alunoDAO.excluir(rgm);
                JOptionPane.showMessageDialog(this, "Aluno removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                txtRgm.setText(""); txtNome.setText(""); txtDataNasc.setText(""); txtCpf.setText("");
                txtEmail.setText(""); txtEndereco.setText(""); txtMunicipio.setText(""); txtCelular.setText("");
                txtRgmNotas.setText(""); txtNomeNotasExibe.setText(""); txtCursoNotasExibe.setText("");
                txtBoletim.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}