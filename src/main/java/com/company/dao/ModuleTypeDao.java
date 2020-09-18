package com.company.dao;

import com.company.model.ModuleType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModuleTypeDao extends TypeDao<ModuleType> {

    public ModuleTypeDao() {
        super("modules");

        selectTypeStatement = "SELECT * FROM modules WHERE id = ?";
    }

    @Override
    ModuleType parseResultSet(ResultSet resultSet) throws SQLException {
        return ModuleType.get(resultSet.getInt("id"));
    }
}
