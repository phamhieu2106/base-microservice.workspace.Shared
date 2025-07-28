package com.base.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkflowConfigConnectorModel {

    @JsonProperty(value = "connector.class")
    @Builder.Default
    private String connectorClass = "io.debezium.connector.postgresql.PostgresConnector";

    @JsonProperty(value = "tasks.max")
    @Builder.Default
    private String tasksMax = "1";

    @JsonProperty(value = "database.hostname")
    @Builder.Default
    private String databaseHostName = "base-postgres";

    @JsonProperty(value = "database.port")
    private String databasePort;

    @JsonProperty(value = "database.user")
    private String databaseUser;

    @JsonProperty(value = "database.password")
    private String databasePassword;

    @JsonProperty(value = "database.dbname")
    private String databaseDbName;

    @JsonProperty(value = "database.server.id")
    private String databaseServerId;

    @JsonProperty(value = "table.include.list")
    private String tableIncludeList;

    @JsonProperty(value = "plugin.name")
    @Builder.Default
    private String pluginName = "pgoutput";

    @JsonProperty(value = "value.converter")
    @Builder.Default
    private String valueConverter = "org.apache.kafka.connect.json.JsonConverter";

    @JsonProperty(value = "value.converter.schemas.enable")
    @Builder.Default
    private boolean valueConverterSchemasEnable = false;

    @JsonProperty(value = "key.converter")
    @Builder.Default
    private String keyConverter = "org.apache.kafka.connect.json.JsonConverter";

    @JsonProperty(value = "key.converter.schemas.enable")
    @Builder.Default
    private boolean keyConverterSchemasEnable = false;

    @JsonProperty(value = "snapshot.mode")
    @Builder.Default
    private String snapshotMode = "initial";

    @JsonProperty(value = "topic.prefix")
    private String topicPrefix;
}
