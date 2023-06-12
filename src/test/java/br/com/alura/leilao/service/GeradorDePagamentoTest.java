package br.com.alura.leilao.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.alura.leilao.dao.PagamentoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Pagamento;
import br.com.alura.leilao.model.Usuario;


public class GeradorDePagamentoTest {

	
	GeradorDePagamento geradorPagamento;
	
	@Mock
	PagamentoDao pagamentoDaoMock;
	
	
	@Mock
	private Clock clockMock;
	
	@Captor
	private ArgumentCaptor<Pagamento> captorPagamento;
	
	@Before
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		geradorPagamento = new GeradorDePagamento(pagamentoDaoMock, clockMock);
	}
	
	@Test
	public void deveriaCriarPagamentoParaVencedorDoLeilao() {
		Leilao leilao = leilao();
		Lance lanceVencedor = leilao.getLanceVencedor();
		
		
		LocalDate data = LocalDate.of(2020, 12, 7);
		
		Instant instant = data.atStartOfDay(ZoneId.systemDefault()).toInstant();
		
		Mockito.when(clockMock.instant()).thenReturn(instant);
		Mockito.when(clockMock.getZone()).thenReturn(ZoneId.systemDefault());
				
		
		
		geradorPagamento.gerarPagamento(lanceVencedor);;
		
		
		
		Mockito.verify(pagamentoDaoMock).salvar(captorPagamento.capture());
		
		Pagamento pagamento = captorPagamento.getValue();
		
		Assert.assertEquals(LocalDate.now(clockMock).plusDays(1), pagamento.getVencimento());
		Assert.assertEquals(lanceVencedor.getValor(), pagamento.getValor());
		Assert.assertFalse(pagamento.getPago());
		Assert.assertEquals(lanceVencedor.getUsuario(), pagamento.getUsuario());
		Assert.assertEquals(leilao, pagamento.getLeilao());
		
	}
	
	
	private Leilao leilao() {

		List<Leilao> lista = new ArrayList<>();

		Leilao leilao = new Leilao("Celular", new BigDecimal("500"), new Usuario("Fulano"));

				Lance lance = new Lance(new Usuario("Ciclano"), new BigDecimal("900"));

		
		leilao.propoe(lance);
		leilao.setLanceVencedor(lance);

		return leilao;

	}

}
