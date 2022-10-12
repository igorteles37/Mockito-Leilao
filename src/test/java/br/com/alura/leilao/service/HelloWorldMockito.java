package br.com.alura.leilao.service;



import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.dao.LanceDao;
import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.dto.NovoLanceDto;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;


@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class HelloWorldMockito {
	
	

	@InjectMocks
	LanceService lanceService;
	
	@Mock
	LeilaoDao leilaoDaoMock;
	
	@Mock
	LanceDao lanceDaoMock;
	
	@Mock
	UsuarioDao usuarioDaoMock;
	
	@Mock
	Leilao leilaoMock;
	
	
	@Test
	public void  hello() {
		//LeilaoDao mock = Mockito.mock(LeilaoDao.class);
		
		//List<Leilao> todos = leilaoDao.buscarTodos();
		
		//Assert.assertTrue(todos.isEmpty());
		when(leilaoDaoMock.buscarPorId(Mockito.anyLong())).thenReturn(new Leilao("Meu Leilao Mockado"));
		Leilao leilaoEncontrado = lanceService.getLeilao(1L);
		Assert.assertEquals("Meu Leilao Mockado", leilaoEncontrado.getNome());
		
		
	}
	
	@Test
	public void  testarProporLanceComSucesso() {

		when(usuarioDaoMock.buscarPorUsername(Mockito.anyString())).thenReturn(new Usuario("IGOR"));
		when(leilaoDaoMock.buscarPorId(Mockito.anyLong())).thenReturn(new Leilao("Meu Leilao Mockado"));
		when(leilaoMock.propoe(Mockito.any(Lance.class))).thenReturn(true);
		
		NovoLanceDto novoLanceDto = new NovoLanceDto();
		novoLanceDto.setLeilaoId(1L);
		novoLanceDto.setValor(new BigDecimal(10.00));
		
		boolean retorno = lanceService.propoeLance(novoLanceDto, "igor");
		
		Assert.assertTrue(retorno);
		verify(lanceDaoMock, times(1)).salvar(Mockito.any(Lance.class));
			
		
	}
	
	
	

}
