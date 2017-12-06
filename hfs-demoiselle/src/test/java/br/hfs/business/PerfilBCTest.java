package br.hfs.business;

import static org.junit.Assert.*;

import java.util.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.hfs.business.PerfilBC;
import br.hfs.domain.Perfil;

@RunWith(DemoiselleRunner.class)
public class PerfilBCTest {

    @Inject
	private PerfilBC perfilBC;
	
	@Before
	public void before() {
		for (Perfil perfil : perfilBC.findAll()) {
			perfilBC.delete(perfil.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		Perfil perfil = new Perfil("perfil",null,null,Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		perfilBC.insert(perfil);
		List<Perfil> listOfPerfil = perfilBC.findAll();
		assertNotNull(listOfPerfil);
		assertEquals(1, listOfPerfil.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		Perfil perfil = new Perfil("perfil",null,null,Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		perfilBC.insert(perfil);
		
		List<Perfil> listOfPerfil = perfilBC.findAll();
		assertNotNull(listOfPerfil);
		assertEquals(1, listOfPerfil.size());
		
		perfilBC.delete(perfil.getId());
		listOfPerfil = perfilBC.findAll();
		assertEquals(0, listOfPerfil.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		Perfil perfil = new Perfil("perfil",null,null,Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		perfilBC.insert(perfil);
		
		List<Perfil> listOfPerfil = perfilBC.findAll();
		Perfil perfil2 = (Perfil)listOfPerfil.get(0);
		assertNotNull(listOfPerfil);

		// alterar para tratar uma propriedade existente na Entidade Perfil
		// perfil2.setUmaPropriedade("novo valor");
		perfilBC.update(perfil2);
		
		listOfPerfil = perfilBC.findAll();
		Perfil perfil3 = (Perfil)listOfPerfil.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade Perfil
		// assertEquals("novo valor", perfil3.getUmaPropriedade());
	}

}