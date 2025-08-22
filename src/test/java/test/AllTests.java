package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import services.ProdutoService;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ClienteServiceTest.class,ClienteDAOTest.class,ProdutoServiceTest.class,ProdutoDAOTest.class,VendaDAOTest.class
})
public class AllTests {

}
