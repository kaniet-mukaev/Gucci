package com.gucci.layers.db.dao;

import com.gucci.layers.db.beans.ActorBean;
import com.gucci.layers.db.utils.DbConnection;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.dbutils.BeanProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActorDao {

    public static List<ActorBean> getAllActors() throws SQLException {
        String query = "Select * from actor";
        try(ResultSet resultSet = DbConnection.query(query)) {
            return new BeanProcessor().toBeanList(resultSet, ActorBean.class);
        }
    }

    public static ActorBean getActor(String column, String value) throws SQLException {
        String query = "Select * from actor where " + column + " = ?;";
        ResultSet resultSet = DbConnection.query(query, value);

        if (!resultSet.next()) {
            return null;
        } else {
            return new BeanProcessor().toBean(resultSet, ActorBean.class);
        }
    }
}
