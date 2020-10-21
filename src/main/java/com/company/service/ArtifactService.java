package com.company.service;

import com.company.dao.ArtifactDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Artifact;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ArtifactService {

    private ArtifactDao artifactDao;

    public ArtifactService() {
        this.artifactDao = new ArtifactDao();
    }

    public Artifact getArtifactById(int id) throws ObjectNotFoundException {
        Artifact artifact = artifactDao.getById(id);
        return artifact;
    }

    public List<Artifact> getAllArtifacts() throws ObjectNotFoundException {
        ObjectMapper mapper = new ObjectMapper();

        if (actions.length == 3) {
            int id = Integer.parseInt(actions[2]);
            return mapper.writeValueAsString(artifactService.getArtifactById(id));
        }
        return mapper.writeValueAsString(artifactService.getAllArtifacts());


        List<Artifact> artifacts = new ArrayList<>();
        artifacts = artifactDao.getAll();
        return artifacts;
    }

    public void createArtifact() {
        
    }

    public void editArtifact() {

    }

    public void deleteArtifact() {

    }
}
