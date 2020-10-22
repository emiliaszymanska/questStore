package com.company.service;

import com.company.dao.ArtifactDao;
import com.company.dao.ArtifactTypeDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Artifact;
import com.company.model.ArtifactType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArtifactServiceTest {

    private ArtifactService artifactService = new ArtifactService();
    private Artifact artifact;
    private ArtifactTypeDao artifactTypeDao = new ArtifactTypeDao();
    private ArtifactDao artifactDao = new ArtifactDao();

    @Test
    public void testIfArtifactExists() throws ObjectNotFoundException {
        artifact = new Artifact
                (1, "Doom Inscriptions", "All the students will focus on your coding problem until it's solved",
                        1000, artifactTypeDao.getTypeById(1), false);

        Artifact actual = artifactDao.getById(1);

        assertEquals(artifact.getId(), actual.getId());
        assertEquals(artifact.getName(), actual.getName());
        assertEquals(artifact.getDescription(), actual.getDescription());
        assertEquals(artifact.getPrice(), actual.getPrice());
        assertEquals(artifact.getType(), actual.getType());
        assertEquals(artifact.isGroup(), actual.isGroup());
    }
}