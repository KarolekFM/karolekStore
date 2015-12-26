package net.karolek.store;

import net.karolek.store.common.QueryCallback;
import net.karolek.store.common.TaskProvider;
import net.karolek.store.queries.interfaces.OrderQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Test {

    public static void main(String[] args) {
        Store store = Store.createMysql(new TaskProvider() {

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            @Override
            public Logger getLogger() {
                return Logger.getLogger("Store");
            }

            @Override
            public void runTask(Runnable runnable) {
                executorService.execute(runnable);
            }

            @Override
            public void runTaskTimer(final Runnable runnable, long period) {
                new Timer().scheduleAtFixedRate(new TimerTask() {

                    @Override
                    public void run() {
                        runnable.run();
                    }
                }, period, period);
            }
        }, "s1.speedhost.pl:3306", "sid1947_karolek", "sid1947_karolek", "PHPTy3jj");

        store.setDebug(true);

        Queries.select()
                .table("tabela")
                .column("id")
                .column("wartosc")
                .where()
                .column("id").greaterThan(2)
                .query()
                .limit(5)
                .offset(2)
                .callback(new QueryCallback() {
                    @Override
                    public void done(ResultSet resultSet) throws SQLException {
                        while (resultSet.next()) {
                            System.out.println(resultSet.getInt("id") + " -> " + resultSet.getString("wartosc"));
                        }
                    }

                    @Override
                    public void error(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .order("id", OrderQuery.OrderType.ASC)
                .execute(store);

        Queries.insert()
                .table("tabela")
                .add("wartosc", "czesc!")
                .execute(store);

        Queries.update()
                .table("tabela")
                .set("wartosc", "update!")
                .where()
                .column("id").equals(4)
                .query()
                .execute(store);

        Queries.delete()
                .table("tabela")
                .where()
                .column("wartosc").like("update!")
                .query()
                .execute(store);

        Queries.customQuery().query("SELECT * FROM tabela").callback(new QueryCallback() {
            @Override
            public void done(ResultSet resultSet) throws SQLException {
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + " -> " + resultSet.getString("wartosc"));
                }
            }

            @Override
            public void error(Throwable throwable) {
                throwable.printStackTrace();
            }
        }).execute(store);


    }

}
