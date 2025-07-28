package com.base.handler;

import com.base.BaseObjectLoggAble;
import com.base.common.annotation.BaseWorkflowConnector;
import com.base.connector.DebeziumConnector;
import com.base.exception.ServiceException;
import com.base.model.WorkflowConfigConnectorModel;
import com.base.model.WorkflowConnectorModel;
import com.base.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class BaseWorkflowRegisterJDBCHandler extends BaseObjectLoggAble implements CommandLineRunner {

    private final ApplicationContext applicationContext;
    private final DebeziumConnector debeziumConnector;

    @Value("${henry.connector.db-port:}")
    private String DATABASE_PORT;
    @Value("${spring.datasource.username:}")
    private String DATABASE_USER;
    @Value("${spring.datasource.password:}")
    private String DATABASE_PASSWORD;
    @Value("${henry.connector.db-name:}")
    private String DATABASE_DBNAME;
    @Value("${henry.connector.server-id:}")
    private String DATABASE_SERVER_ID;
    @Value("${henry.connector.db-event-table:event_entity}")
    private String TABLE;
    @Value("${henry.connector.prefix-message:workflow}")
    private String TOPIC_PREFIX;

    @Override
    public void run(String... args) {
        Map<String, Object> workflowConnectorMap = applicationContext.getBeansWithAnnotation(BaseWorkflowConnector.class);

        for (Object config : workflowConnectorMap.values()) {
            BaseWorkflowConnector annotation = config.getClass().getAnnotation(BaseWorkflowConnector.class);
            if (annotation == null) {
                throw new ServiceException("No BaseWorkflowConnector annotation found");
            }

            WorkflowConnectorModel configConnectorModel = new WorkflowConnectorModel();
            configConnectorModel.setName(annotation.connectorName());
            configConnectorModel.setConfig(WorkflowConfigConnectorModel.builder()
//                    .databaseHostName()
                    .databasePort(DATABASE_PORT)
                    .databaseUser(DATABASE_USER)
                    .databasePassword(DATABASE_PASSWORD)
                    .databaseDbName(DATABASE_DBNAME)
                    .databaseServerId(DATABASE_SERVER_ID)
                    .tableIncludeList(annotation.schema() + "." + TABLE)
                    .topicPrefix(TOPIC_PREFIX)
                    .build());

            if (StringUtils.isNotBlank(annotation.connectorName())) {
                try {
                    debeziumConnector.sendConnector(annotation.connectorName(), ObjectMapperUtils.mapObjectToString(configConnectorModel));
                } catch (JsonProcessingException e) {
                    throw new ServiceException(e.getMessage(), e);
                }
            }
        }
    }
}
