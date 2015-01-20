package br.com.ediel.apresentacao.test;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;

import br.com.ediel.apresentacao.impl.CadastroController;
import br.com.ediel.apresentacao.impl.LoginController;
import br.com.ediel.apresentacao.model.Cliente;
import br.com.ediel.apresentacao.model.ClienteDAO;
import br.com.ediel.apresentacao.model.ClienteDAOJPA;
import br.com.ediel.apresentacao.model.Credentials;

@RunWith(Arquillian.class)
public class CadastroTeste {

	private static final Logger logger = Logger.getLogger("CadastroTeste");

	private static final String WEBAPP_SRC = "src/main/webapp";

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "exemplo.war")
				.addClasses(Credentials.class, LoginController.class,
						Cliente.class, CadastroController.class,
						ClienteDAO.class, ClienteDAOJPA.class)
				.merge(ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class).importDirectory(WEBAPP_SRC)
						.as(GenericArchive.class), "/",
						Filters.include(".*\\.xhtml$"))

				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml")
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml");
	}

	// Injeta o Web Driver
	@Drone
	// private WebDriver browser;
	private FirefoxDriver browser;

	// Injeta a URL da aplicação
	@ArquillianResource
	private URL deploymentUrl;

	@FindBy(id = "form:cpf")
	private WebElement cpf;

	// Injeta o elemento que referencia o inputtext userName
	@FindBy(id = "form:userName")
	private WebElement userName;

	@FindBy(id = "form:password")
	private WebElement password;

	@FindBy(id = "form:nome")
	private WebElement nome;

	@FindBy(id = "form:endereco")
	private WebElement endereco;

	@FindBy(id = "form:cadastrarButton")
	private WebElement cadastroButton;

	// Injeta o primeiro elemento que possui a tag "li"
	@FindBy(tagName = "li")
	private WebElement facesMessage;

	/**
	     * 
	     */
	@Test
	public void cadastroCliente() {

		final String nomeCliente = "Fabio Russo";
		final String asset = "Cliente " + nomeCliente + " Cadastrado!";

		// Abre a página de teste
		browser.get(deploymentUrl.toExternalForm() + "cadastro.jsf");

		logger.info("Title" + browser.getTitle());
		logger.info("Page Source" + browser.getPageSource());
		logger.info("Window Handle" + browser.getWindowHandle());

		cpf.sendKeys("29729296839");
		nome.sendKeys(nomeCliente);
		userName.sendKeys("frusso");
		password.sendKeys("fodase");

		// Sincroniza a requisição da página
		guardHttp(cadastroButton).click();

		assertEquals(asset, facesMessage.getText().trim());
	}

	@Test
	public void test() {
	}

}
