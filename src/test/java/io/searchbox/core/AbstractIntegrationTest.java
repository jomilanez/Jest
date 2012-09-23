package io.searchbox.core;

import io.searchbox.client.http.JestHttpClient;
import io.searchbox.configuration.SpringClientTestConfiguration;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Dogukan Sonmez
 */

public class AbstractIntegrationTest {

    AnnotationConfigApplicationContext context;

    protected JestHttpClient client;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(SpringClientTestConfiguration.class);
        client = context.getBean(JestHttpClient.class);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

}
