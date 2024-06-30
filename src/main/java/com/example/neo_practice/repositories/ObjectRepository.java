package com.example.neo_practice.repositories;

import ch.qos.logback.core.net.ObjectWriter;
import com.example.neo_practice.models.Object;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ObjectRepository extends Neo4jRepository<Object, String> {
    @Query("MATCH (n:Revision)-[r:HAVE]->(m:Object) WHERE n.id = $revisionId RETURN m")
    Set<Object> findExtendedObjects(String revisionId);

    @Query("MATCH (n:Object) where n.isRoot = true RETURN n")
    Object findRoot();

    Object findOneById(String objectId);

    @Query("MATCH (n:Revision)-[r:HAVE]->(m:Object) WHERE m.id = $objectId DELETE r, m")
    void deleteObjectAndRelation(String objectId);

    @Query("MATCH (n:Revision)-[r:HAVE]->(m:Object) WHERE m.id = $objectId DELETE r")
    void deleteParentRelation(String objectId);

    @Query("MATCH (n:Object), (m:Revision) WHERE n.id = $objectId AND m.id = $revisionId CREATE (m)-[r:HAVE]->(n)")
    void createRelationship(String objectId, String revisionId);
}
