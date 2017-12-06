package br.hfs.view.exemplos;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.hfs.util.jdbc.Estrutura;
import br.hfs.util.jdbc.EstruturaDAO;

@ViewController
public class ArvoreView implements Serializable {
	private static final long serialVersionUID = 1L;

	private TreeNode selectedNode;
	private TreeNode raiz;

	private int totalLinhas;
	
	@Inject
	MessageContext messageContext;

	@PostConstruct
	public void init() {
		try {
			totalLinhas = EstruturaDAO.getInstancia().contaTotal(); 
		} catch (SQLException e) {
			messageContext.add(e.getMessage());
		}
	}
	
	public TreeNode getRaiz() {
		return raiz;
	}

	public void mostrarSelecionado() {
        if(selectedNode != null) {
        	messageContext.add(selectedNode.getData().toString());
        }
    }
	
	public void carregarArvore() {
		EstruturaDAO.getInstancia().limparListas();
		
		try {
			EstruturaDAO.getInstancia().carregarEstruturaPai();
			EstruturaDAO.getInstancia().carregarEstrutura();
		} catch (SQLException e) {
			messageContext.add(e.getMessage());
		}

		raiz = new DefaultTreeNode("Raiz", null);

		for (Estrutura item : EstruturaDAO.getInstancia()
				.getListaEstruturaPai()) {
			AddItemArvore(item, 0, null);
		}

		
	}

	private void AddItemArvore(Estrutura estrutura, int Nivel, TreeNode node1) {
		List<Estrutura> listaFilhos;
		TreeNode node2;

		if (Nivel == 0) {
			node1 = new DefaultTreeNode(estrutura.getDescricao() + " ("
					+ estrutura.getSigla() + ")", raiz);
		}

		listaFilhos = EstruturaDAO.getInstancia().itensFilhos(
				EstruturaDAO.getInstancia().getListaEstrutura(),
				estrutura.getId().getCodigoOrganizacao(),
				estrutura.getId().getCodigo());

		for (Estrutura filho : listaFilhos) {
			node2 = new DefaultTreeNode(filho.getDescricao() + " ("
					+ filho.getSigla() + ")", node1);

			AddItemArvore(filho, ++Nivel, node2);
		}
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public int getTotalLinhas() {
		return totalLinhas;
	}

	public void setTotalLinhas(int totalLinhas) {
		this.totalLinhas = totalLinhas;
	}

}
