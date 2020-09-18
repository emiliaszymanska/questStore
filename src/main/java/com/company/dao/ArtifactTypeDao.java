package com.company.dao;

import com.company.model.ArtifactType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtifactTypeDao extends TypeDao<ArtifactType> {

    public ArtifactTypeDao() {
        super("artifact_types");

        selectTypeStatement = "SELECT * FROM artifact_types WHERE id = ?";
    }

    @Override
    ArtifactType parseResultSet(ResultSet resultSet) throws SQLException {
        return ArtifactType.get(resultSet.getInt("id"));
    }
}
