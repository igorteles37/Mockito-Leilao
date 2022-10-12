package br.com.alura.leilao.service;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;


@RunWith(MockitoJUnitRunner.class)
public class LanceServiceTest {
	
	
	@InjectMocks
	@Spy
	LanceService lanceService;
	
	@Mock
	UsuarioDao usuarioDaoMock;
	
	@Test
	public void testarRetornarNomeUsuarioENomeLeilao() {
		when(lanceService.getLeilao()).thenReturn(new Leilao("LEILAO MOCKADO"));
		Mockito.doReturn(new Usuario("IGOR TELES MOCKADO")).when(usuarioDaoMock).buscarPorUsername(Mockito.anyString());
			
		String nomeLance = lanceService.retornarNomeUsuarioENomeLance();
		
		Assert.assertEquals("IGOR TELES MOCKADO - LEILAO MOCKADO", nomeLance);
	}

}
