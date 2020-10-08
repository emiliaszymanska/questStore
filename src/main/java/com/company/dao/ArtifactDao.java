package com.company.dao;

import com.company.controller.SessionController;
import com.company.model.Artifact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDao extends Dao<Artifact> {

    private ArtifactTypeDao artifactTypeDao;

    public ArtifactDao() {
        super("artifacts");
        this.artifactTypeDao = new ArtifactTypeDao();

        selectStatement = "SELECT * FROM artifacts WHERE id = ?";
        selectAllStatement = "SELECT * FROM artifacts";
        insertStatement = "INSERT INTO artifacts (name, description, price, artifact_type_id, is_group) VALUES " +
                "(?, ?, ?, ?, ?)";
        updateStatement = "UPDATE artifacts SET name = ?, description = ?, price = ?, artifact_type_id = ?, " +
                "is_group = ? WHERE id = ?";
        deleteStatement = "DELETE FROM artifacts WHERE id = ?";
    }

    @Override
    Artifact parseResultSet(ResultSet resultSet) throws SQLException {
        return getArtifact(resultSet);
    }

    @Override
    List<Artifact> getAllObjects(ResultSet resultSet) throws SQLException {
        List<Artifact> artifacts = new ArrayList<>();

        while (resultSet.next()) {
            artifacts.add(getArtifact(resultSet));
        }
        return artifacts;
    }

    @Override
    void specifyPreparedStatement(Artifact artifact) throws SQLException {
        preparedStatement.setString(1, artifact.getName());
        preparedStatement.setString(2, artifact.getDescription());
        preparedStatement.setInt(3, artifact.getPrice());
        preparedStatement.setInt(4, artifact.getType().getId());
        preparedStatement.setBoolean(5, artifact.isGroup());
    }

    @Override
    void addIdToUpdateStatement(Artifact artifact) throws SQLException {
        preparedStatement.setInt(6, artifact.getId());
    }

    @Override
    void specifyDeleteStatement(Artifact artifact) throws SQLException {
        preparedStatement.setInt(1, artifact.getId());
    }

    private Artifact getArtifact(ResultSet resultSet) throws SQLException {
        Artifact artifact = new Artifact();
        artifact.setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setPrice(resultSet.getInt("price"))
                .setType(artifactTypeDao.getTypeById(resultSet.getInt("artifact_type_id")))
                .setGroup(resultSet.getBoolean("is_group"));

        return artifact;
    }
}
