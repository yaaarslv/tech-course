import entities.Owner;
import services.Service;
import tools.CatException;
import tools.ConnectionException;
import tools.OwnerException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, OwnerException, CatException, ConnectionException, IOException {
        Service service = new Service(1430, "lab-2", "yaaarsl_v", "Password123");
        Owner owner = new Owner("Тестовый челик", new Date(2023 - 1900, 2, 29));

        long startTimeJDBC = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            service.getJdbcConnection().getOwnerJdbc().save(owner);
        }
        long durationJDBC = (System.nanoTime() - startTimeJDBC) / 1000000;
        service.getJdbcConnection().getOwnerJdbc().deleteAll();

        long startTimeHibernate = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            service.getHibernateConnection().getOwnerHibernate().save(owner);
        }
        long durationHibernate = (System.nanoTime() - startTimeHibernate) / 1000000;
        service.getHibernateConnection().getOwnerHibernate().deleteAll();

        long startTimeMyBatis = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            service.getMyBatisConnection().getOwnerMyBatis().save(owner);
        }
        long durationMyBatis = (System.nanoTime() - startTimeMyBatis) / 1000000;

        System.out.println("Время, затраченное на добавление 100 сущностей:\n" +
                "JDBC - " + durationJDBC + " мс\n" +
                "Hibernate - " + durationHibernate + " мс\n" +
                "MyBatis - " + durationMyBatis + " мс\n");

        startTimeJDBC = System.nanoTime();
        service.getJdbcConnection().getOwnerJdbc().getAll();
        durationJDBC = (System.nanoTime() - startTimeJDBC) / 1000000;

        startTimeHibernate = System.nanoTime();
        service.getHibernateConnection().getOwnerHibernate().getAll();
        durationHibernate = (System.nanoTime() - startTimeHibernate) / 1000000;

        startTimeMyBatis = System.nanoTime();
        service.getMyBatisConnection().getOwnerMyBatis().getAll();
        durationMyBatis = (System.nanoTime() - startTimeMyBatis) / 1000000;

        System.out.println("Время, затраченное на получение 100 сущностей:\n" +
                "JDBC - " + durationJDBC + " мс\n" +
                "Hibernate - " + durationHibernate + " мс\n" +
                "MyBatis - " + durationMyBatis + " мс");
    }
}
