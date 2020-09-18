package com.company.dao;

import com.company.exceptions.ObjectNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class Dao<T> {

    private final String TABLE_NAME;
    protected final Connector CONNECTOR;

    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;
    protected String selectStatement, selectAllStatement, insertStatement, deleteStatement, updateStatement;

    public Dao(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
        this.CONNECTOR = new Connector();
    }

    public T getById(int id) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
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

    public List<T> getAll() throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(selectAllStatement);
            resultSet = preparedStatement.executeQuery();
            List<T> objectList = getAllObjects(resultSet);

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

            return objectList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in " + TABLE_NAME);
        }
    }

    public void insert(T t) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(insertStatement);
            specifyPreparedStatement(t);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in " + TABLE_NAME);
        }
    }

    public void update(T t) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(updateStatement);
            specifyPreparedStatement(t);
            addIdToUpdateStatement(t);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in " + TABLE_NAME);
        }
    }

    public void delete(T t) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(deleteStatement);
            specifyDeleteStatement(t);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in " + TABLE_NAME);
        }
    }

    abstract T parseResultSet(ResultSet resultSet) throws SQLException;

    abstract List<T> getAllObjects(ResultSet resultSet) throws SQLException;

    abstract void specifyPreparedStatement(T t) throws SQLException;

    abstract void addIdToUpdateStatement(T t) throws SQLException;

    abstract void specifyDeleteStatement(T t) throws SQLException;
}
