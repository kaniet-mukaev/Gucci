package com.gucci.layers.db.dao;

import com.gucci.layers.db.dto.FilmAverageDto;
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
public class FilmAverageDao {

    public static List<FilmAverageDto> getMaxAverageLengthOfFilm() throws SQLException {
        String query = """
                Select c.name, AVG(f.length) as average_length, MAX(f.length) as max_length
                from category c
                join film_category fc on c.category_id = fc.category_id
                join film f on fc.film_id = f.film_id
                group by c.name
                """;
        try(ResultSet resultSet = DbConnection.query(query)) {
            return new BeanProcessor().toBeanList(resultSet, FilmAverageDto.class);
        }
    }
}
