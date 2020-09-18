package com.company.dao;

import com.company.exceptions.ObjectNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class TypeDao<T> {

    private final String TABLE_NAME;
    protected final Connector CONNECTOR;

    protected String selectTypeStatement;

    public TypeDao(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
        this.CONNECTOR = new Connector();
    }

    public T getTypeById(int id) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            PreparedStatement preparedStatement = CONNECTOR.connection.prepareStatement(selectTypeStatement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            T object = parseResultSet(resultSet);

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

            return object;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in " + TABLE_NAME);
        }
    }

    abstract T parseResultSet(ResultSet resultSet) throws SQLException;
}
