package com.jojoreading.demo.server.test;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenAppNamespaceDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenNamespaceDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
public class ApolloTestClient {

    private static final String DEFAULT_ENV = "dev";
    private static final String DEFAULT_CLUSTER = "default";
	private static final String DEFAULT_NAMESPACE_FORMAT = "properties";
	private static final String DEFAULT_COMMENT = "Operation by ApolloTestClient";

    private Properties props;
    private ApolloOpenApiClient client;

    // 配置信息
    private String apolloPortalUrl;
    private String apolloAppId;
    private String apolloAppToken;
	private String apolloUserId;

    public ApolloTestClient(File dockerPropsFile) {
        createClient(dockerPropsFile);
    }

    private String loadContents(File configFile) {
		try {
            return FileUtils.readFileToString(configFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties loadProps(File configFile) {
        Properties props = new Properties();
        try {
            InputStream inputStream = new FileInputStream(configFile);
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return props;
    }

    private String readDockerConfig(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Item not in docker config, key=" + key);
        }

        return value;
    }

    private void createClient(File dockerPropsFile) {
        // 加载配置文件
        props = loadProps(dockerPropsFile);

        // 读取配置文件
        apolloPortalUrl = readDockerConfig("apollo_portal_url");
        apolloAppId = readDockerConfig("apollo_app_id");
        apolloAppToken = readDockerConfig("apollo_app_token");
        apolloUserId = readDockerConfig("apollo_user_id");

        // 创建客户端
        client = ApolloOpenApiClient.newBuilder()
                .withPortalUrl(apolloPortalUrl)
                .withToken(apolloAppToken)
                .build();
    }

    /**
     * 查询所有的命名空间
     */
    public List<OpenNamespaceDTO> listNamespace() {
        return client.getNamespaces(apolloAppId, DEFAULT_ENV, DEFAULT_CLUSTER);
    }

    /**
     * 查询命名空间是否存在
     * 注意 properties 空间不要带格式, 例如 application
     * 注意 properties 空间需要带上后缀, 例如 module-common.yml
     */
    public boolean checkNamespaceIfExists(String namespaceName) {
        return listNamespace().parallelStream().map(OpenNamespaceDTO::getNamespaceName)
                .collect(Collectors.toList()).contains(namespaceName);
    }


    @Builder
    @Getter
    private static class NamespaceDTO {
        private String name;
        private String format;
        private String namespaceName;
    }

    private NamespaceDTO readNamespaceDTO(String configFileName) {
        String fileName = FilenameUtils.getBaseName(configFileName);
        String extName = FilenameUtils.getExtension(configFileName);

        if (extName.equals(DEFAULT_NAMESPACE_FORMAT)) {
            return NamespaceDTO.builder()
                    .name(fileName)
                    .format(DEFAULT_NAMESPACE_FORMAT)
                    .namespaceName(fileName)
                    .build();
        } else {
            return NamespaceDTO.builder()
                    .name(fileName)
                    .format(extName)
                    .namespaceName(fileName + "." + extName)
                    .build();
        }
    }

    /**
     * 创建命名空间并初始化属性
     *
     * @param configFile    配置文件地址
     * @return
     */
	public String createNamespaceAndInitProps(File configFile) {
        NamespaceDTO namespaceDTO = readNamespaceDTO(configFile.getPath());

        // 判断命名空间是否已经存在
        if (!checkNamespaceIfExists(namespaceDTO.getNamespaceName())) {
            OpenAppNamespaceDTO openAppNamespaceDTO = new OpenAppNamespaceDTO();
            openAppNamespaceDTO.setAppId(apolloAppId);
            openAppNamespaceDTO.setName(namespaceDTO.getName());
            openAppNamespaceDTO.setFormat(namespaceDTO.getFormat());
            openAppNamespaceDTO.setPublic(false);
            openAppNamespaceDTO.setComment(DEFAULT_COMMENT);
            openAppNamespaceDTO.setDataChangeCreatedBy(apolloUserId);
            client.createAppNamespace(openAppNamespaceDTO);
        }

        // 读取并写入内容
        if (namespaceDTO.getFormat().equals(DEFAULT_NAMESPACE_FORMAT)) {
            // 如果是 properties 格式，则需要按条写入
            Properties properties = loadProps(configFile);

            // 优化排序方式, 让控制台的配置更具有可读性
            List<String> keyList = new ArrayList<>();
            for(Object key: properties.keySet()) {
                keyList.add((String) key);
            }
            Collections.sort(keyList);

            for (String key: keyList) {
                OpenItemDTO openItemDTO = new OpenItemDTO();
                openItemDTO.setKey(key);
                openItemDTO.setValue(properties.getProperty(key));
                openItemDTO.setDataChangeCreatedBy(apolloUserId);
                client.createOrUpdateItem(apolloAppId, DEFAULT_ENV, DEFAULT_CLUSTER,
                        namespaceDTO.getNamespaceName(), openItemDTO);
            }

        } else {
			OpenItemDTO openItemDTO = new OpenItemDTO();
            openItemDTO.setKey("content");    // 官方文档规定非 properties 格式时 key = content
            openItemDTO.setValue(loadContents(configFile));
            openItemDTO.setDataChangeCreatedBy(apolloUserId);
            client.createOrUpdateItem(apolloAppId, DEFAULT_ENV, DEFAULT_CLUSTER,
                    namespaceDTO.getNamespaceName(), openItemDTO);
        }

        NamespaceReleaseDTO releaseDTO = new NamespaceReleaseDTO();
        releaseDTO.setReleaseTitle(DEFAULT_COMMENT);
        releaseDTO.setReleasedBy(apolloUserId);
        client.publishNamespace(apolloAppId, DEFAULT_ENV, DEFAULT_CLUSTER, namespaceDTO.getNamespaceName(), releaseDTO);

        return namespaceDTO.getNamespaceName();
    }

}
