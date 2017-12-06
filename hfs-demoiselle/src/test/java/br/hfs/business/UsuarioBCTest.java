package br.hfs.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.hfs.business.UsuarioBC;
import br.hfs.domain.Usuario;

@RunWith(DemoiselleRunner.class)
public class UsuarioBCTest {

    @Inject
	private UsuarioBC usuarioBC;
	
	@Before
	public void before() {
		for (Usuario usuario : usuarioBC.findAll()) {
			usuarioBC.delete(usuario.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		Usuario usuario = new Usuario("login","senha","nome","email","cpf",null,
				Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		usuarioBC.insert(usuario);
		List<Usuario> listOfUsuario = usuarioBC.findAll();
		assertNotNull(listOfUsuario);
		assertEquals(1, listOfUsuario.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		Usuario usuario = new Usuario("login","senha","nome","email","cpf",null,
				Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		usuarioBC.insert(usuario);
		
		List<Usuario> listOfUsuario = usuarioBC.findAll();
		assertNotNull(listOfUsuario);
		assertEquals(1, listOfUsuario.size());
		
		usuarioBC.delete(usuario.getId());
		listOfUsuario = usuarioBC.findAll();
		assertEquals(0, listOfUsuario.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		Usuario usuario = new Usuario("login","senha","nome","email","cpf",null,
				Long.valueOf(1),new Date(),Long.valueOf(1),new Date());
		usuarioBC.insert(usuario);
		
		List<Usuario> listOfUsuario = usuarioBC.findAll();
		Usuario usuario2 = (Usuario)listOfUsuario.get(0);
		assertNotNull(listOfUsuario);

		// alterar para tratar uma propriedade existente na Entidade Usuario
		// usuario2.setUmaPropriedade("novo valor");
		usuarioBC.update(usuario2);
		
		listOfUsuario = usuarioBC.findAll();
		Usuario usuario3 = (Usuario)listOfUsuario.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade Usuario
		// assertEquals("novo valor", usuario3.getUmaPropriedade());
	}

}