package com.chatbox.dao;

import com.chatbox.mapper.RowMapper;
import com.chatbox.util.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class AbstractDao<T> {
    private final DataSource dataSource;

    @Autowired
    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<T> query(String procName, RowMapper<T> rowMapper, Object... params) {
        log.info("Begin query with procName = {} and params = {}", procName, params);
        List<T> list = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            callableStatement = connection.prepareCall(procName);
            setParameters(callableStatement, params);
            int outParamIndex = params.length + 1;
            callableStatement.registerOutParameter(outParamIndex, Types.REF_CURSOR);

            callableStatement.execute();
            resultSet = callableStatement.getObject(outParamIndex, ResultSet.class);
            while (resultSet.next()) {
                list.add(rowMapper.mapRow(resultSet));
            }
            log.info("End query with count = {}", list.size());
            return list;
        } catch (Exception e) {
            log.info("Exception in query");
            log.error("Exception in query", e);
            return list;
        }
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(callableStatement != null) {
                    callableStatement.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            }catch(SQLException e) {
                log.info("SQLException in query");
                log.error("SQLException in query", e);
            }
        }
    }

    public List<String> query(String procName, Object... params) {
        List<String> list = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            callableStatement = connection.prepareCall(procName);
            setParameters(callableStatement, params);
            int outParamIndex = params.length + 1;
            callableStatement.registerOutParameter(outParamIndex, Types.REF_CURSOR);

            callableStatement.execute();
            resultSet = callableStatement.getObject(outParamIndex, ResultSet.class);
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (Exception e) {
            return list;
        }
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(callableStatement != null) {
                    callableStatement.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int count(String procName, Object... params) {
        log.info("Begin count with procName = {} and params = {}", procName, params);
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            int count = 0;
            connection = dataSource.getConnection();
            callableStatement = connection.prepareCall(procName);
            setParameters(callableStatement, params);
            int outParamIndex = params.length + 1;
            callableStatement.registerOutParameter(outParamIndex, Types.REF_CURSOR);

            callableStatement.execute();
            resultSet = callableStatement.getObject(outParamIndex, ResultSet.class);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            log.info("End count with count = {}", count);
            return count;
        } catch (Exception e) {
            log.info("Exception in count");
            log.error("Exception in count", e);
            return 0;
        }
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(callableStatement != null) {
                    callableStatement.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            }catch(SQLException e) {
                log.info("SQLException in count");
                log.error("SQLException in count", e);
            }
        }
    }

    public String update(String procName, Object... params) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            callableStatement = connection.prepareCall(procName);
            setParameters(callableStatement, params);

            callableStatement.execute();
            int updateCount = callableStatement.executeUpdate();
            if(updateCount > 0) {
                return APIResponse.SUCCESS;
            }
            return APIResponse.ERROR;
        } catch (Exception e) {
            return APIResponse.ERROR;
        }
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(callableStatement != null) {
                    callableStatement.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private void setParameters(CallableStatement callableStatement, Object[] params) {
        try {
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                if(param instanceof Long)
                    callableStatement.setLong(i+1, (Long) param);
                if(param instanceof Integer)
                    callableStatement.setInt(i+1, (Integer) param);
                else if(param instanceof String)
                    callableStatement.setString(i+1, (String) param);
                else if(param instanceof Timestamp)
                    callableStatement.setTimestamp(i+1, (Timestamp) param);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
