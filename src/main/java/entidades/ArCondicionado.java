package entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.Date;

// criar ainda as consultas personalizadas
@NamedQuery(
		name = "ArCondicionado.listarTODOS", 
		query="SELECT A FROM ArCondicionado A"
	)
@NamedQuery(
	    name = "ArCondicionado.definirTemperaturaAtual",
	    query = "UPDATE ArCondicionado a SET a.temperaturaAtual = :temperatura WHERE a.id = :id"
	)
// busca lista completa pois pode ser reutilizado em outros contextos, limitar no dao
@NamedQuery(
	    name = "ArCondicionado.buscarMaioresDataManutencao",
	    query = "SELECT a FROM ArCondicionado a ORDER BY a.dataManutencao DESC"
	)
@Entity
public class ArCondicionado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "dataManutencao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataManutencao;
    
    @Column(name = "temperaturaAtual")
    private Integer temperaturaAtual;
    
    @Column(name = "temperaturaMinima")
    private Integer temperaturaMinima;
    
    @Column(name = "temperaturaMaxima")
    private Integer temperaturaMaxima;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataManutencao() {
		return dataManutencao;
	}

	public void setDataManutencao(Date dataManutencao) {
		this.dataManutencao = dataManutencao;
	}

	public Integer getTemperaturaAtual() {
		return temperaturaAtual;
	}

	public void setTemperaturaAtual(Integer temperaturaAtual) {
		this.temperaturaAtual = temperaturaAtual;
	}

	public Integer getTemperaturaMinima() {
		return temperaturaMinima;
	}

	public void setTemperaturaMinima(Integer temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}

	public Integer getTemperaturaMaxima() {
		return temperaturaMaxima;
	}

	public void setTemperaturaMaxima(Integer temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}

	@Override
	public String toString() {
	    return "id=" + id +
	           ", descricao='" + descricao + '\'' +
	           ", dataManutencao=" + dataManutencao +
	           ", temperaturaAtual=" + temperaturaAtual +
	           ", temperaturaMinima=" + temperaturaMinima +
	           ", temperaturaMaxima=" + temperaturaMaxima;
	}
}
