package br.hfs.business;

import static org.junit.Assert.*;

import java.util.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.hfs.business.PermissaoBC;
import br.hfs.domain.Permissao;

@RunWith(DemoiselleRunner.class)
public class PermissaoBCTest {

    @Inject
	private PermissaoBC permissaoBC;
	
	@Before
	public void before() {
		for (Permissao permissao : permissaoBC.findAll()) {
			permissaoBC.delete(permissao.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		Permissao permissao = new Permissao("permissao",null,Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		permissaoBC.insert(permissao);
		List<Permissao> listOfPermissao = permissaoBC.findAll();
		assertNotNull(listOfPermissao);
		assertEquals(1, listOfPermissao.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		Permissao permissao = new Permissao("permissao",null,Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		permissaoBC.insert(permissao);
		
		List<Permissao> listOfPermissao = permissaoBC.findAll();
		assertNotNull(listOfPermissao);
		assertEquals(1, listOfPermissao.size());
		
		permissaoBC.delete(permissao.getId());
		listOfPermissao = permissaoBC.findAll();
		assertEquals(0, listOfPermissao.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		Permissao permissao = new Permissao("permissao",null,Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		permissaoBC.insert(permissao);
		
		List<Permissao> listOfPermissao = permissaoBC.findAll();
		Permissao permissao2 = (Permissao)listOfPermissao.get(0);
		assertNotNull(listOfPermissao);

		// alterar para tratar uma propriedade existente na Entidade Permissao
		// permissao2.setUmaPropriedade("novo valor");
		permissaoBC.update(permissao2);
		
		listOfPermissao = permissaoBC.findAll();
		Permissao permissao3 = (Permissao)listOfPermissao.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade Permissao
		// assertEquals("novo valor", permissao3.getUmaPropriedade());
	}

}