package org.example.elastic;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * ES 通用工具
 * 注意: 这个类会隐藏 IOException
 */
@Slf4j
public class EsHelper {

    private final RestHighLevelClient client;

    public EsHelper(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 获取所有索引
     */
    public Set<String> getAllIndex() {
        try {
            GetAliasesRequest request = new GetAliasesRequest();
            GetAliasesResponse response = client.indices().getAlias(request, RequestOptions.DEFAULT);
            Map<String, Set<AliasMetaData>> map = response.getAliases();
            Set<String> indices = map.keySet();
            return indices;
        } catch (IOException e) {
            log.error("", e);
            return Collections.emptySet();
        }
    }
}