package br.com.alura.leilao.service;



import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.dao.LanceDao;
import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.dto.NovoLanceDto;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;


@RunWith(MockitoJUnitRunner.class)
public class LanceServiceTest {
	
	

	@InjectMocks
	@Spy
	LanceService lanceService;
	
	@Mock
	LeilaoDao leilaoDaoMock;
	
	@Mock
	LanceDao lanceDaoMock;
	
	@Mock
	UsuarioDao usuarioDaoMock;
	
	
	
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
	public void testarRetornarNomeUsuarioENomeLeilao() {
		when(lanceService.getLeilao()).thenReturn(new Leilao("LEILAO MOCKADO"));
		doReturn(new Usuario("IGOR TELES MOCKADO")).when(usuarioDaoMock).buscarPorUsername(Mockito.anyString());
			
		String nomeLance = lanceService.retornarNomeUsuarioENomeLance();
		
		Assert.assertEquals("IGOR TELES MOCKADO - LEILAO MOCKADO", nomeLance);
	}
	
	@Test
	public void  testarProporLanceComSucesso() {

		when(usuarioDaoMock.buscarPorUsername(Mockito.anyString())).thenReturn(new Usuario("USUARIO MOCKADO"));
		//when(lanceService.getLeilao(Mockito.anyLong())).thenReturn(new Leilao("LEILAO MOCKADO"));
		when(leilaoDaoMock.buscarPorId(Mockito.anyLong())).thenReturn(new Leilao("Meu Leilao Mockado"));
		
		NovoLanceDto novoLanceDto = new NovoLanceDto();
		novoLanceDto.setLeilaoId(1L);
		novoLanceDto.setValor(new BigDecimal(10.00));
		
		boolean retorno = lanceService.propoeLance(novoLanceDto, "USUARIO MOCKADO");
		
		Assert.assertTrue(retorno);
		verify(lanceDaoMock, times(1)).salvar(Mockito.any(Lance.class));
	}
	
	
	@Test
	public void  testarProporLanceLeilaoComValorMaiorQueOUltimoLanceComOutroUsuario() {

		Usuario usuarioMockado1 = new Usuario("usuarioMock1");
		usuarioMockado1.setId(1L);
		when(usuarioDaoMock.buscarPorUsername("usuarioMock1")).thenReturn(usuarioMockado1);
		
		Leilao leilaoComlances = new Leilao("LEILAO MOCKADO");
		leilaoComlances.setId(1L);
	
		
		when(leilaoDaoMock.buscarPorId(Mockito.anyLong())).thenReturn(leilaoComlances);
		
		NovoLanceDto primeiroLanceDto = new NovoLanceDto();
		primeiroLanceDto.setLeilaoId(1L);
		primeiroLanceDto.setValor(new BigDecimal(101.00));
		
		boolean retorno = lanceService.propoeLance(primeiroLanceDto, "usuarioMock1");
		
		Assert.assertTrue(retorno);
		verify(lanceDaoMock, times(1)).salvar(Mockito.any(Lance.class));

		
		
		
		Usuario usuarioMockado2 = new Usuario("usuarioMock2");
		usuarioMockado1.setId(2L);
		when(usuarioDaoMock.buscarPorUsername("usuarioMock2")).thenReturn(usuarioMockado2);
		

		NovoLanceDto segundoLanceDto = new NovoLanceDto();
		segundoLanceDto.setLeilaoId(1L);
		segundoLanceDto.setValor(new BigDecimal(102.00));
		
		boolean retorno2 = lanceService.propoeLance(segundoLanceDto, "usuarioMock2");
		
		Assert.assertTrue(retorno2);
		verify(lanceDaoMock, times(2)).salvar(Mockito.any(Lance.class));
		
		
	}
	
	
	@Test
	public void  testarProporLanceLeilaoComLanceDeOutroUsuarioComValorMenorQueOLanceAnterior() {

		Usuario usuarioMockado1 = new Usuario("usuarioMock1");
		usuarioMockado1.setId(1L);
		when(usuarioDaoMock.buscarPorUsername("usuarioMock1")).thenReturn(usuarioMockado1);
		
		Leilao leilaoComlances = new Leilao("LEILAO MOCKADO");
		leilaoComlances.setId(1L);
	
		
		when(leilaoDaoMock.buscarPorId(Mockito.anyLong())).thenReturn(leilaoComlances);
		
		NovoLanceDto primeiroLanceDto = new NovoLanceDto();
		primeiroLanceDto.setLeilaoId(1L);
		primeiroLanceDto.setValor(new BigDecimal(101.00));
		
		boolean retorno = lanceService.propoeLance(primeiroLanceDto, "usuarioMock1");
		
		Assert.assertTrue(retorno);
		verify(lanceDaoMock, times(1)).salvar(Mockito.any(Lance.class));

		
		
		
		Usuario usuarioMockado2 = new Usuario("usuarioMock2");
		usuarioMockado1.setId(2L);
		when(usuarioDaoMock.buscarPorUsername("usuarioMock2")).thenReturn(usuarioMockado2);
		

		NovoLanceDto segundoLanceDto = new NovoLanceDto();
		segundoLanceDto.setLeilaoId(1L);
		segundoLanceDto.setValor(new BigDecimal(99.00));
		
		boolean retorno2 = lanceService.propoeLance(segundoLanceDto, "usuarioMock2");
		
		Assert.assertFalse(retorno2);
		verify(lanceDaoMock, times(1)).salvar(Mockito.any(Lance.class));
		
		
	}
	
	
	
	@Test
	public void  testarProporLanceLeilaoComLanceMaiorMasComOMesmoUsuario() {

		Usuario usuarioMockado1 = new Usuario("usuarioMock1");
		usuarioMockado1.setId(1L);
		when(usuarioDaoMock.buscarPorUsername("usuarioMock1")).thenReturn(usuarioMockado1);
		
		Leilao leilaoComlances = new Leilao("LEILAO MOCKADO");
		leilaoComlances.setId(1L);
	
		when(leilaoDaoMock.buscarPorId(Mockito.anyLong())).thenReturn(leilaoComlances);
		
		NovoLanceDto primeiroLanceDto = new NovoLanceDto();
		primeiroLanceDto.setLeilaoId(1L);
		primeiroLanceDto.setValor(new BigDecimal(101.00));
		
		boolean retorno = lanceService.propoeLance(primeiroLanceDto, "usuarioMock1");
		
		Assert.assertTrue(retorno);
		verify(lanceDaoMock, times(1)).salvar(Mockito.any(Lance.class));

		NovoLanceDto segundoLanceDto = new NovoLanceDto();
		segundoLanceDto.setLeilaoId(1L);
		segundoLanceDto.setValor(new BigDecimal(102.00));
		
		boolean retorno2 = lanceService.propoeLance(segundoLanceDto, "usuarioMock1");
		
		Assert.assertFalse(retorno2);
		verify(lanceDaoMock, times(1)).salvar(Mockito.any(Lance.class));
		
		
	}
	
	@Rule
	public ExpectedException expectedExeption =ExpectedException.none();
	private final String MSG_CUSTOMIZADA = "Fator da divisao nao pode ser 0"; 
	
	@Test
	public void deveLancarUmaExcecaoComMensagemCustomizada() {
			expectedExeption.expect(ArithmeticException.class);
			expectedExeption.expectMessage(MSG_CUSTOMIZADA);
			
			lanceService.realizarDivisaoLance(1000, 0);
	}
	
	@Test(expected=ArithmeticException.class)
	public void deveLancarUmaExcecao() {
			lanceService.realizarDivisaoLance(1000, 0);
	}
	
	
	
	@Test(expected=NullPointerException.class)
	public void deveLancarUmaExcecaoComMock() {
			
			when(lanceService.realizarDivisaoLance(10, 0)).thenThrow(new NullPointerException("Mensagem Mockada"));
		
			lanceService.obterValorRealLance(10, 0);
	}
	

}
