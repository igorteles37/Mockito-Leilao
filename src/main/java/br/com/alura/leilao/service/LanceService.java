package br.com.alura.leilao.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.leilao.dao.LanceDao;
import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.dto.NovoLanceDto;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

@Service
public class LanceService {

	@Autowired
	private LanceDao lances;

	@Autowired
	private UsuarioDao usuarios;

	@Autowired
	private LeilaoDao leiloes;

	public boolean propoeLance(NovoLanceDto lanceDto, String nomeUsuario) {
		
		Usuario usuario = usuarios.buscarPorUsername(nomeUsuario);
		
		Lance lance = lanceDto.toLance(usuario);

		Leilao leilao = this.getLeilao(lanceDto.getLeilaoId());

		if (leilao.propoe(lance)) {
			lances.salvar(lance);
			return true;
		}

		return false;
	}

	public String retornarNomeUsuarioENomeLance() {
		Usuario usuario = usuarios.buscarPorUsername("Igor");
		return usuario.getNome() + " - " + getLeilao().getNome();
	}
	
	
	public String retornarNomeUsuarioENomeLance(String nomeUsuario, Integer codigoLeilao) {
		Usuario usuario = usuarios.buscarPorUsername(nomeUsuario);
		//return usuario.getNome() + " - " + getNomeLeilao() ;
		return usuario.getNome() + " - " + getNomeLeilao(codigoLeilao) ;
	}
	
	
	
	private String getNomeLeilao(Integer codigoLeilao) {
		return "LEILAO REAL";
	}

	
	public Leilao getLeilao() {
		return new Leilao("LEILAO REAL");
	}

	public Leilao getLeilao(Long leilaoId) {
		return leiloes.buscarPorId(leilaoId);
	}
	
	
	public int obterValorRealLance(int valorLance, int fatorDivisao) {
		return realizarDivisaoLance(valorLance, fatorDivisao);
	}
	
	public int realizarDivisaoLance(int valorLance, int fatorDivisao) {
		
		if (fatorDivisao == 0)
			throw new ArithmeticException("Fator da divisao nao pode ser 0");
		
		return valorLance / fatorDivisao;  
	}

}