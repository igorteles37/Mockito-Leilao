package br.com.alura.leilao.service;

import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

public class FinalizarLeilaoServiceTest {

	@InjectMocks
	private FinalizarLeilaoService finalizarLeilaoService;

	@Mock
	private LeilaoDao leilaoDaoMock;

	@Mock
	private EnviadorDeEmails enviadorDeEmailsMock;

	@Before
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		// this.finalizarLeilaoService = new FinalizarLeilaoService(leilaoDaoMock,
		// enviadorDeEmailsMock);
		// this.finalizarLeilaoService = new FinalizarLeilaoService();
	}

	@Test
	public void deveriaFinalizarUmLeilao() {
		List<Leilao> leiloes = leilos();
		Mockito.when(leilaoDaoMock.buscarLeiloesExpirados()).thenReturn(leiloes);
		finalizarLeilaoService.finalizarLeiloesExpirados();

		Leilao leilao = leiloes.get(0);

		Assert.assertTrue(leilao.isFechado());
		Assert.assertEquals(new BigDecimal("900"), leilao.getLanceVencedor().getValor());

		Mockito.verify(leilaoDaoMock, times(1)).salvar(leilao);

	}

	@Test
	public void deveriaEnviarEmailParaVencedorDoLeilao() {
		List<Leilao> leiloes = leilos();
		Mockito.when(leilaoDaoMock.buscarLeiloesExpirados()).thenReturn(leiloes);
		finalizarLeilaoService.finalizarLeiloesExpirados();

		Leilao leilao = leiloes.get(0);
		Lance lanceVencedor = leilao.getLanceVencedor();

		Mockito.verify(enviadorDeEmailsMock, times(1)).enviarEmailVencedorLeilao(lanceVencedor);

	}

	@Test
	public void naoDeveriaEmailParaVencedorEmCasoDeErroAoEncerrarOLeilao() {
		List<Leilao> leiloes = leilos();
		
		Mockito.when(leilaoDaoMock.buscarLeiloesExpirados()).thenReturn(leiloes);
		Mockito.when(leilaoDaoMock.salvar(Mockito.any())).thenThrow(RuntimeException.class);

		try {
			finalizarLeilaoService.finalizarLeiloesExpirados();
			Mockito.verifyNoInteractions(enviadorDeEmailsMock);
			
		} catch (Exception e) {}
		
		
		//Lance lanceVencedor = leilao.getLanceVencedor();
		
		
		//Mockito.verify(leilaoDaoMock, times(1)).salvar(leilao);
		//Mockito.verify(enviadorDeEmailsMock, times(0)).enviarEmailVencedorLeilao(lanceVencedor);
		//Mockito.verifyNoInteractions(enviadorDeEmailsMock);
		
		
	}

	private List<Leilao> leilos() {

		List<Leilao> lista = new ArrayList<>();

		Leilao leilao = new Leilao("Celular", new BigDecimal("500"), new Usuario("Fulano"));

		Lance primeiro = new Lance(new Usuario("Beltrano"), new BigDecimal("600"));

		Lance segundo = new Lance(new Usuario("Ciclano"), new BigDecimal("900"));

		leilao.propoe(primeiro);
		leilao.propoe(segundo);

		lista.add(leilao);

		return lista;
	}

}
