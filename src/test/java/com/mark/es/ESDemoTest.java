package com.mark.es;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

/**
 * Author: Mark
 * Date  : 2017/11/22
 */
public class ESDemoTest {


    private ElasticsearchTemplate template;

    @Before
    public void setUp() throws UnknownHostException {
        final Settings settings = Settings.builder().put("cluster.name", "demo").build();
        final PreBuiltTransportClient client = new PreBuiltTransportClient(settings, Collections.emptyList());
        client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("127.0.0.1", 9300)));
        template = new ElasticsearchTemplate(client);
    }

    @Test
    public void addData() {
        final UserDO userDO = new UserDO();
        userDO.setUid(1L);
        userDO.setFansCount(0L);
        userDO.setName("Mark");
        userDO.setPostCount(0L);
        userDO.setVerify(1);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId("1")
                .withObject(userDO).build();
        template.bulkIndex(Collections.singletonList(indexQuery));
    }


}