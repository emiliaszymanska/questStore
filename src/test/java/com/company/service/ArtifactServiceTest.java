package com.company.service;

import com.company.dao.ArtifactDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Artifact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArtifactServiceTest {

    @Test
    public void testAddingArtifact() throws ObjectNotFoundException, JsonProcessingException {
        ArtifactDao artifactDao = Mockito.mock(ArtifactDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Artifact artifact = new Artifact();

        artifactDao.insert(artifact);
        mapper.writeValueAsString(artifact);

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).insert(artifact),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(artifact),
                () -> assertEquals(Mockito.any(), artifact.getName()),
                () -> assertEquals(Mockito.any(), artifact.getDescription()),
                () -> assertEquals(Mockito.anyDouble(), artifact.getPrice()),
                () -> assertEquals(Mockito.any(), artifact.getType()),
                () -> assertEquals(Mockito.anyBoolean(), artifact.isGroup())
        );
    }

    @Test
    public void testUpdateArtifact() throws JsonProcessingException, ObjectNotFoundException {
        ArtifactDao artifactDao = Mockito.mock(ArtifactDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Artifact artifact = new Artifact();

        artifactDao.update(artifact);
        mapper.writeValueAsString(artifact);

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).update(artifact),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(artifact)
        );
    }

    @Test
    public void testDeleteArtifact() throws ObjectNotFoundException, JsonProcessingException {
        ArtifactDao artifactDao = Mockito.mock(ArtifactDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Artifact artifact = new Artifact();

        artifactDao.delete(artifact);
        mapper.writeValueAsString(artifact);

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).delete(artifact),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(artifact)
        );
    }

    @Test
    public void testGetAll() throws ObjectNotFoundException {
        ArtifactDao artifactDao = Mockito.mock(ArtifactDao.class);

        artifactDao.getAll();

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).getAll()
        );
    }

    @Test
    public void testGetById() throws ObjectNotFoundException, JsonProcessingException {
        ArtifactDao artifactDao = Mockito.mock(ArtifactDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Artifact artifact = new Artifact();

        artifactDao.getById(artifact.getId());
        mapper.writeValueAsString(artifact);

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).getById(artifact.getId()),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(artifact)
        );
    }
}