package br.com.sistemaac_modelo;
//VALIDAÇÃO DOS MÉTODOS (AÇÕES QUE A CLASSE TERÁ)

public class Notas_e_Faltas {
    private int idNota;
    private String rgm;
    private String disciplina;
    private String semestre;
    private double nota;
    private int faltas;

    public Notas_e_Faltas() {}

    public Notas_e_Faltas(String rgm, String disciplina, String semestre, double nota, int faltas) {
        this.rgm = rgm;
        this.disciplina = disciplina;
        this.semestre = semestre;
        this.nota = nota;
        this.faltas = faltas;
    }
// USO DE GET E SETTERS (PARA CAUSAR RETORNO E VALIDAÇÃO)
    public int getIdNota() { return idNota; }
    
    public void setIdNota(int idNota) { this.idNota = idNota; }
    
    public String getRgm() { return rgm; }
    public void setRgm(String rgm) { this.rgm = rgm; }
    
    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }
    
    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }
    
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
    
    public int getFaltas() { return faltas; }
    public void setFaltas(int faltas) { this.faltas = faltas; }
}
