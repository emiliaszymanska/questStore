package com.company.service;

import com.company.dao.ArtifactDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Artifact;
import com.company.model.ArtifactType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ArtifactService {

    private ArtifactDao artifactDao;
    private ObjectMapper mapper;
    private Artifact artifact;

    public ArtifactService() {
        this.artifactDao = new ArtifactDao();
        this.mapper = new ObjectMapper();
    }

    public ArtifactService(ArtifactDao artifactDao, ObjectMapper mapper) {
        this.artifactDao = artifactDao;
        this.mapper = mapper;
    }


    public String getAll() throws ObjectNotFoundException, JsonProcessingException {
        return mapper.writeValueAsString(artifactDao.getAll());
    }

    public String getById(int id) throws ObjectNotFoundException, JsonProcessingException {
        return mapper.writeValueAsString(artifactDao.getById(id));
    }

    public String addArtifact(Map<String, String> formData) throws ObjectNotFoundException, JsonProcessingException {
        artifact = createArtifact(formData);
        artifactDao.insert(artifact);
        return mapper.writeValueAsString(artifact);
    }

    public String updateArtifact(Map<String, String> formData) throws ObjectNotFoundException, JsonProcessingException {
        artifact = artifactDao.getById(Integer.parseInt(formData.get("id")));
        getArtifactData(formData, artifact);
        artifactDao.update(artifact);
        return mapper.writeValueAsString(artifact);
    }

    public String deleteArtifact(Map<String, String> formData) throws ObjectNotFoundException, JsonProcessingException {
        artifact = artifactDao.getById(Integer.parseInt(formData.get("id")));
        artifactDao.delete(artifact);
        return mapper.writeValueAsString(artifact);
    }

    private Artifact createArtifact(Map<String, String> formData) {
        artifact = new Artifact();
        return getArtifactData(formData, artifact);
    }

    private Artifact getArtifactData(Map<String, String> formData, Artifact artifact) {
        artifact.setName(formData.get("name"))
                .setDescription(formData.get("description"))
                .setPrice(Integer.parseInt(formData.get("price")))
                .setType(ArtifactType.valueOf(formData.get("type")))
                .setGroup(Boolean.parseBoolean(formData.get("isGroup")));
        return artifact;
    }
}