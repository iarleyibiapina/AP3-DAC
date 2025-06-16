package DTOs;

// Uma classe para reunir e transferir dados, nesse contexto ira armazenar a logica
// para o resumo de jogos de um time.
public class ResumoTimeDTO {
    private String nome;
    private int pontuacao;
    private int vitorias;
    private int derrotas;
    private int empates;
    private int golsMarcados;
    private int golsSofridos;
    private int saldoGols;
    
    public ResumoTimeDTO(String nome) {
        this.nome = nome;
    }
    
	public ResumoTimeDTO(String nome, int pontuacao, int vitorias, int derrotas, int empates, int golsMarcados,
			int golsSofridos, int saldoGols) {
		this.nome         = nome;
		this.pontuacao    = pontuacao;
		this.vitorias     = vitorias;
		this.derrotas  	  = derrotas;
		this.empates   	  = empates;
		this.golsMarcados = golsMarcados;
		this.golsSofridos = golsSofridos;
		this.saldoGols 	  = saldoGols;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	public int getVitorias() {
		return vitorias;
	}
	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}
	public int getDerrotas() {
		return derrotas;
	}
	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}
	public int getEmpates() {
		return empates;
	}
	public void setEmpates(int empates) {
		this.empates = empates;
	}
	public int getGolsMarcados() {
		return golsMarcados;
	}
	public void setGolsMarcados(int golsMarcados) {
		this.golsMarcados = golsMarcados;
	}
	public int getGolsSofridos() {
		return golsSofridos;
	}
	public void setGolsSofridos(int golsSofridos) {
		this.golsSofridos = golsSofridos;
	}
	public int getSaldoGols() {
		return saldoGols;
	}
	public void setSaldoGols(int saldoGols) {
		this.saldoGols = saldoGols;
	}
}
