package br.com.alura.leilao.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;

import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Usuario;

/*

@RunWith(PowerMockRunner.class)
@PrepareForTest(LanceService.class)
public class TesteMockMetodoPrivado {

	@InjectMocks
	LanceService lanceService = PowerMockito.spy(new LanceService());
	
	@Mock
	UsuarioDao usuarioDaoMock;
	
    @Test
    public void testPublicAPI() throws Exception {
    	
    	Mockito.doReturn(new Usuario("IGOR TELES MOCKADO")).when(usuarioDaoMock).buscarPorUsername(Mockito.anyString());
        PowerMockito.when(lanceService, "getNomeLeilao", Mockito.anyInt()).thenReturn("LEILAO MOCKADO");
        String retornarNomeUsuarioENomeLance = lanceService.retornarNomeUsuarioENomeLance("IGOR", 1);
        
        Assert.assertEquals("IGOR TELES MOCKADO - LEILAO MOCKADO", retornarNomeUsuarioENomeLance);
    }
    
}*/
