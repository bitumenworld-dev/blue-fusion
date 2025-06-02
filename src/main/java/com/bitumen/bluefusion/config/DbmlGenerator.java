package com.bitumen.bluefusion.config;

import jakarta.persistence.*;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DbmlGenerator {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @EventListener(ApplicationReadyEvent.class)
    public void generateDbml() {
        try {
            Metamodel metamodel = entityManagerFactory.getMetamodel();

            StringBuilder dbml = new StringBuilder();
            dbml.append("// Generated DBML from Java Entities\n\n");

            // Generate tables
            for (EntityType<?> entity : metamodel.getEntities()) {
                dbml.append(convertEntityToDbml(entity));
                dbml.append("\n");
            }

            // Generate relationships
            dbml.append(generateRelationships(metamodel));

            // Write to file
            writeToFile("database-schema.dbml", dbml.toString());
            System.out.println("DBML schema generated successfully: database-schema.dbml");
        } catch (Exception e) {
            System.err.println("Error generating DBML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String convertEntityToDbml(EntityType<?> entityType) {
        StringBuilder dbml = new StringBuilder();

        // Get table name
        String tableName = getTableName(entityType);
        dbml.append("Table ").append(tableName).append(" {\n");

        // Process attributes
        for (Attribute<?, ?> attribute : entityType.getAttributes()) {
            if (
                attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.BASIC ||
                attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.EMBEDDED
            ) {
                String columnDefinition = buildColumnDefinition(attribute, entityType);
                if (columnDefinition != null && !columnDefinition.trim().isEmpty()) {
                    dbml.append("  ").append(columnDefinition).append("\n");
                }
            }
        }

        dbml.append("}\n");
        return dbml.toString();
    }

    private String getTableName(EntityType<?> entityType) {
        Class<?> javaType = entityType.getJavaType();

        // Check for @Table annotation
        if (javaType.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = javaType.getAnnotation(Table.class);
            if (!tableAnnotation.name().isEmpty()) {
                return tableAnnotation.name();
            }
        }

        // Default to entity name in lowercase
        return entityType.getName().toLowerCase();
    }

    private String buildColumnDefinition(Attribute<?, ?> attribute, EntityType<?> entityType) {
        try {
            StringBuilder column = new StringBuilder();

            // Get column name
            String columnName = getColumnName(attribute, entityType);
            column.append(columnName);

            // Get data type
            String dataType = mapJavaTypeToDbml(attribute);
            column.append(" ").append(dataType);

            // Get constraints
            List<String> constraints = getConstraints(attribute, entityType);

            if (!constraints.isEmpty()) {
                column.append(" [").append(String.join(", ", constraints)).append("]");
            }

            return column.toString();
        } catch (Exception e) {
            System.err.println("Error processing attribute: " + attribute.getName() + " - " + e.getMessage());
            return null;
        }
    }

    private String getColumnName(Attribute<?, ?> attribute, EntityType<?> entityType) {
        try {
            // Try to get the field from the Java class
            Class<?> javaType = entityType.getJavaType();
            java.lang.reflect.Field field = javaType.getDeclaredField(attribute.getName());

            // Check for @Column annotation
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (!columnAnnotation.name().isEmpty()) {
                    return columnAnnotation.name();
                }
            }

            // Check for @JoinColumn annotation (for relationships)
            if (field.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn joinColumnAnnotation = field.getAnnotation(JoinColumn.class);
                if (!joinColumnAnnotation.name().isEmpty()) {
                    return joinColumnAnnotation.name();
                }
            }
        } catch (NoSuchFieldException e) {
            // Field not found, use attribute name
        }

        // Default to attribute name
        return attribute.getName();
    }

    private String mapJavaTypeToDbml(Attribute<?, ?> attribute) {
        Class<?> javaType = attribute.getJavaType();

        if (javaType == String.class) {
            // Try to get length from @Column annotation
            int length = getStringLength(attribute);
            return "varchar(" + length + ")";
        } else if (javaType == Long.class || javaType == long.class) {
            return "bigint";
        } else if (javaType == Integer.class || javaType == int.class) {
            return "int";
        } else if (javaType == BigDecimal.class) {
            return "decimal(10,2)";
        } else if (javaType == Boolean.class || javaType == boolean.class) {
            return "boolean";
        } else if (javaType == LocalDateTime.class || javaType == java.sql.Timestamp.class) {
            return "timestamp";
        } else if (javaType == LocalDate.class || javaType == java.sql.Date.class) {
            return "date";
        } else if (javaType == Double.class || javaType == double.class) {
            return "double";
        } else if (javaType == Float.class || javaType == float.class) {
            return "float";
        }

        // Default fallback
        return "varchar(255)";
    }

    private int getStringLength(Attribute<?, ?> attribute) {
        try {
            // This is a bit tricky with JPA metamodel, so we'll use reflection
            EntityType<?> entityType = (EntityType<?>) attribute.getDeclaringType();
            Class<?> javaType = entityType.getJavaType();
            java.lang.reflect.Field field = javaType.getDeclaredField(attribute.getName());

            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                return columnAnnotation.length();
            }
        } catch (Exception e) {
            // Ignore and use default
        }

        return 255; // Default length
    }

    private List<String> getConstraints(Attribute<?, ?> attribute, EntityType<?> entityType) {
        List<String> constraints = new ArrayList<>();

        try {
            Class<?> javaType = entityType.getJavaType();
            java.lang.reflect.Field field = javaType.getDeclaredField(attribute.getName());

            // Check for @Id annotation
            if (field.isAnnotationPresent(Id.class)) {
                constraints.add("primary key");
            }

            // Check for @GeneratedValue annotation
            if (field.isAnnotationPresent(GeneratedValue.class)) {
                GeneratedValue genValue = field.getAnnotation(GeneratedValue.class);
                if (genValue.strategy() == GenerationType.IDENTITY) {
                    constraints.add("increment");
                }
            }

            // Check for @Column constraints
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);

                if (!columnAnnotation.nullable()) {
                    constraints.add("not null");
                }

                if (columnAnnotation.unique()) {
                    constraints.add("unique");
                }
            }
        } catch (NoSuchFieldException e) {
            // Field not found, skip constraints
        }

        return constraints;
    }

    private String generateRelationships(Metamodel metamodel) {
        StringBuilder relationships = new StringBuilder();
        relationships.append("// Relationships\n");

        for (EntityType<?> entityType : metamodel.getEntities()) {
            for (Attribute<?, ?> attribute : entityType.getAttributes()) {
                if (
                    attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.MANY_TO_ONE ||
                    attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.ONE_TO_ONE
                ) {
                    String relationship = buildRelationship(attribute, entityType);
                    if (relationship != null) {
                        relationships.append(relationship).append("\n");
                    }
                }
            }
        }

        return relationships.toString();
    }

    private String buildRelationship(Attribute<?, ?> attribute, EntityType<?> entityType) {
        try {
            Class<?> javaType = entityType.getJavaType();
            java.lang.reflect.Field field = javaType.getDeclaredField(attribute.getName());

            String sourceTable = getTableName(entityType);
            String targetTable = getTargetTableName(attribute);
            String foreignKeyColumn = getForeignKeyColumn(field);

            return String.format("Ref: %s.%s > %s.id", sourceTable, foreignKeyColumn, targetTable);
        } catch (Exception e) {
            System.err.println("Error building relationship for: " + attribute.getName() + " - " + e.getMessage());
            return null;
        }
    }

    private String getTargetTableName(Attribute<?, ?> attribute) {
        Class<?> targetType = attribute.getJavaType();

        // Check for @Table annotation on target entity
        if (targetType.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = targetType.getAnnotation(Table.class);
            if (!tableAnnotation.name().isEmpty()) {
                return tableAnnotation.name();
            }
        }

        // Default to class name in lowercase
        return targetType.getSimpleName().toLowerCase();
    }

    private String getForeignKeyColumn(java.lang.reflect.Field field) {
        // Check for @JoinColumn annotation
        if (field.isAnnotationPresent(JoinColumn.class)) {
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            if (!joinColumn.name().isEmpty()) {
                return joinColumn.name();
            }
        }

        // Default naming convention: fieldName + "_id"
        return field.getName() + "_id";
    }

    private void writeToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
