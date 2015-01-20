package br.com.ediel.apresentacao.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 8017424701716372558L;

	@Id
	private Long id;

	private String nome;
	
	private String cpf;
	
	private String endereco;
	
	private String username;
	
	public User() {
        }
	
	public User(Long id, String nome, String username) {
	    this.id = id;
	    this.nome = nome;
	    this.username = username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nome=" + nome + "]";
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
	
	

}
