package br.com.ediel.apresentacao.impl;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ediel.apresentacao.model.Cliente;
import br.com.ediel.apresentacao.model.ClienteDAO;

@Named("cadastroController")
@SessionScoped
public class CadastroController implements Serializable {

	/**
	 * version 1.0
	 */
	private static final long serialVersionUID = 6395568569897342491L;
	private Cliente cliente;
	
	@Inject
	private ClienteDAO dao;
	
	public CadastroController() {
		cliente=new Cliente();
		cliente.setId(1L);
	}
	
	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void cadastrar() {
		dao.save(cliente);
		FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cliente "+ cliente.getNome() +" Cadastrado!"));
	}


	

}
