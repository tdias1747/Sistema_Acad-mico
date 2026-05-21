package br.com.sistemaac_modelo;

public class Curso {
    private String rgm;
    private String curso;
    private String campus;
    private String periodo;

    public Curso() {}

    public Curso(String rgm, String curso, String campus, String periodo) {
        this.rgm = rgm;
        this.curso = curso;
        this.campus = campus;
        this.periodo = periodo;
    }
    public String getRgm() { return rgm; }
    public void setRgm(String rgm) { this.rgm = rgm; }
    
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    
    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }
    
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
}
