package br.hfs.business;

import static org.junit.Assert.*;

import java.util.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.hfs.business.EntidadeBC;
import br.hfs.domain.Entidade;

@RunWith(DemoiselleRunner.class)
public class EntidadeBCTest {

    @Inject
	private EntidadeBC entidadeBC;
	
	@Before
	public void before() {
		for (Entidade entidade : entidadeBC.findAll()) {
			entidadeBC.delete(entidade.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		Entidade entidade = new Entidade("nome",null,null,Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		entidadeBC.insert(entidade);
		List<Entidade> listOfEntidade = entidadeBC.findAll();
		assertNotNull(listOfEntidade);
		assertEquals(1, listOfEntidade.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		Entidade entidade = new Entidade("nome",null,null,Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		entidadeBC.insert(entidade);
		
		List<Entidade> listOfEntidade = entidadeBC.findAll();
		assertNotNull(listOfEntidade);
		assertEquals(1, listOfEntidade.size());
		
		entidadeBC.delete(entidade.getId());
		listOfEntidade = entidadeBC.findAll();
		assertEquals(0, listOfEntidade.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		Entidade entidade = new Entidade("nome",null,null,Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		entidadeBC.insert(entidade);
		
		List<Entidade> listOfEntidade = entidadeBC.findAll();
		Entidade entidade2 = (Entidade)listOfEntidade.get(0);
		assertNotNull(listOfEntidade);

		// alterar para tratar uma propriedade existente na Entidade Entidade
		// entidade2.setUmaPropriedade("novo valor");
		entidadeBC.update(entidade2);
		
		listOfEntidade = entidadeBC.findAll();
		Entidade entidade3 = (Entidade)listOfEntidade.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade Entidade
		// assertEquals("novo valor", entidade3.getUmaPropriedade());
	}

}