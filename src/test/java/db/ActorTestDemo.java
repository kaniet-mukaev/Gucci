package db;

import com.gucci.layers.db.dao.ActorDao;
import com.gucci.layers.db.dao.FilmAverageDao;
import com.gucci.layers.db.dao.FilmByActorDao;
import com.gucci.layers.db.utils.DbConnection;

import java.sql.SQLException;

public class ActorTestDemo {
    public static void main(String[] args) throws SQLException {
        DbConnection.openConnection("dvd rental");
//        ActorDao.getAllActors().forEach(System.out::println);
//
//        System.out.println(ActorDao.getActor("first_name", "Penelope"));
//
//        FilmByActorDao.getFilmsByActorId().forEach(System.out::println);

        FilmAverageDao.getMaxAverageLengthOfFilm().forEach(System.out::println);
    }
}
