package br.com.ediel.apresentacao.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClienteDAOJPA implements ClienteDAO {

	@PersistenceContext
	EntityManager em;
	
	public ClienteDAOJPA() {
		
	}

	@Override
	public void save(Cliente cliente) {
		em.persist(cliente);
	}

}
