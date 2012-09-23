package io.searchbox.core;


import fr.tlrx.elasticsearch.test.annotations.ElasticsearchIndex;
import fr.tlrx.elasticsearch.test.annotations.ElasticsearchNode;
import fr.tlrx.elasticsearch.test.support.junit.runners.ElasticsearchRunner;
import io.searchbox.Parameters;
import io.searchbox.client.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static junit.framework.Assert.*;

/**
 * @author Dogukan Sonmez
 */

@RunWith(ElasticsearchRunner.class)
@ElasticsearchNode
public class UpdateIntegrationTest extends AbstractIntegrationTest {

    @Test
    @ElasticsearchIndex(indexName = "twitter")
    public void updateWithValidParameters() {
        String script = "{\n" +
                "    \"script\" : \"ctx._source.tags += tag\",\n" +
                "    \"params\" : {\n" +
                "        \"tag\" : \"blue\"\n" +
                "    }\n" +
                "}";
        try {

            Index index = new Index.Builder("{\"user\":\"kimchy\", \"tags\":\"That is test\"}").index("twitter").type("tweet").id("1").build();
            index.addParameter(Parameters.REFRESH, true);
            client.execute(index);

            SearchResult result = client.execute(new Update.Builder(script).index("twitter").type("tweet").id("1").build());
            assertNotNull(result);
            assertTrue(result.isSucceeded());

            SearchResult getResult = client.execute(new Get.Builder("1").index("twitter").type("tweet").build());
            assertEquals("That is testblue", ((Map) getResult.getValue("_source")).get("tags"));

        } catch (Exception e) {
            fail("Failed during the update with valid parameters. Exception:" + e.getMessage());
        }
    }
}
