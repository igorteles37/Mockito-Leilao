package br.com.alura.leilao.service;



import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Leilao;


@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class HelloWorldMockito {
	
	
	@InjectMocks
	LanceService lanceService;
	
	@Mock
	LeilaoDao leilaoDao;
	
	
	
	@Test
	public void  hello() {
		//LeilaoDao mock = Mockito.mock(LeilaoDao.class);
		
		//List<Leilao> todos = leilaoDao.buscarTodos();
		
		//Assert.assertTrue(todos.isEmpty());
		when(lanceService.getLeilao(Mockito.anyLong())).thenReturn(new Leilao("Meu Leilao Mockado"));
		Leilao leilaoEncontrado = lanceService.getLeilao(1L);
		Assert.assertEquals("Meu Leilao Mockado", leilaoEncontrado.getNome());
		
		
	}
	

}
