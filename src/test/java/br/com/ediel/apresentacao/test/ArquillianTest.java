package br.com.ediel.apresentacao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.ediel.apresentacao.impl.HelloWorldBean;
import br.com.ediel.apresentacao.model.User;

@RunWith(Arquillian.class)
public class ArquillianTest {

    @Deployment
    public static JavaArchive createDeployArchive() {
        return ShrinkWrap
                .create(JavaArchive.class)
                .addClasses(HelloWorldBean.class, User.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml",
                        "META-INF/persistence.xml");
    }

    @Inject
    private HelloWorldBean helloBean;

    @PersistenceContext(name = "test")
    private EntityManager em;

    @Test
    public void test_cdi() {
        assertEquals("Hello ediel!", helloBean.getHelloWorld("ediel"));
    }

    @Test
    public void test_persistence() {
        assertNotNull(em);

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        query.select(query.from(User.class));
        List<User> result = em.createQuery(query).getResultList();
        assertEquals(0, result.size());
    }

    @Test
    @UsingDataSet("datasets/xml-test.xml")
    public void test_db_unit() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> user = query.from(User.class);
        query.select(user);
        List<User> result = em.createQuery(query).getResultList();
        result.stream().forEach(item -> System.out.println(item));
        assertEquals(6, result.size());
    }

}
