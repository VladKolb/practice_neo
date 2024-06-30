package com.example.neo_practice.repositories;

import com.example.neo_practice.models.Object;
import com.example.neo_practice.models.Revision;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RevisionRepository extends Neo4jRepository<Revision, String> {
    @Query("MATCH (n:Object)-[r:HAVE]->(m:Revision) WHERE n.id = $objectId RETURN m")
    Set<Revision> findExtendedRevisions(String objectId);

    Revision findOneById(String revision_id);

    @Query("MATCH (n:Object)-[r:HAVE]->(m:Revision) WHERE n.id = $objectId AND m.id = $revisionId DELETE r")
    void deleteParentRelation(String objectId, String revisionId);

    @Query("MATCH (n:Revision)-[r:HAVE]->(m:Object) WHERE n.id = $revisionId DELETE r, n")
    void deleteExtendedAndRevision(String revisionId);

    @Query("MATCH (n:Object), (m:Revision) WHERE n.id = $objectId AND m.id = $revisionId CREATE (n)-[r:HAVE]->(m)")
    void createRelationship(String objectId, String revisionId);
}
