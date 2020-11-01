package com.company.service;

import com.company.dao.ArtifactDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Artifact;
import com.company.model.ArtifactType;
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

        Map<String, String> map = new HashMap<>();
        map.put("price", "1000");
        map.put("type", "RELATED_TO_STUDENTS");
        map.put("isGroup", "false");

        ArtifactService artifactService = new ArtifactService(artifactDao, mapper);
        artifactService.addArtifact(map);

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).insert(Mockito.any()),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(Mockito.any())
        );
    }

    @Test
    public void testUpdateArtifact() throws JsonProcessingException, ObjectNotFoundException {
        ArtifactDao artifactDao = Mockito.mock(ArtifactDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Map<String, String> map = new HashMap<>();
        map.put("id", "15");
        map.put("name", "test");
        map.put("description", "test");
        map.put("price", "1000");
        map.put("type", "RELATED_TO_STUDENTS");
        map.put("isGroup", "false");

        Artifact artifact = new Artifact();
        Mockito.when(artifactDao.getById(15)).thenReturn(artifact);
        Artifact expected = new Artifact(0, "test", "test", 1000, ArtifactType.RELATED_TO_STUDENTS, false);


        ArtifactService artifactService = new ArtifactService(artifactDao, mapper);
        artifactService.updateArtifact(map);

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).update(artifact),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(artifact),
                () -> assertEquals(expected, artifact)
        );
    }

    @Test
    public void testDeleteArtifact() throws ObjectNotFoundException, JsonProcessingException {
        ArtifactDao artifactDao = Mockito.mock(ArtifactDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Map<String, String> map = new HashMap<>();
        map.put("id", "15");

        ArtifactService artifactService = new ArtifactService(artifactDao, mapper);
        artifactService.deleteArtifact(map);

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).delete(Mockito.any()),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(Mockito.any())
        );
    }

    @Test
    public void testGetAll() throws ObjectNotFoundException, JsonProcessingException {
        ArtifactDao artifactDao = Mockito.mock(ArtifactDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        ArtifactService artifactService = new ArtifactService(artifactDao, mapper);
        artifactService.getAll();

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).getAll()
        );
    }

    @Test
    public void testGetById() throws ObjectNotFoundException, JsonProcessingException {
        ArtifactDao artifactDao = Mockito.mock(ArtifactDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        ArtifactService artifactService = new ArtifactService(artifactDao, mapper);
        artifactService.getById(Mockito.anyInt());

        assertAll(
                () -> Mockito.verify(artifactDao, Mockito.times(1)).getById(Mockito.anyInt())
        );
    }
}