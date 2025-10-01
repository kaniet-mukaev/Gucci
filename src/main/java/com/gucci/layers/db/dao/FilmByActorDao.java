package com.gucci.layers.db.dao;

import com.gucci.layers.db.dto.Dto;
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
public class FilmByActorDao {

    public static List<Dto> getFilmsByActorId() throws SQLException {
        String query = """
                Select a.first_name, count(fa.film_id) as count
                from actor a
                join film_actor fa on a.actor_id = fa.actor_id
                group by a.first_name
                order by count desc
                limit 1
                """;
        try(ResultSet resultSet = DbConnection.query(query)) {
            return new BeanProcessor().toBeanList(resultSet, Dto.class);
        }
    }
}
