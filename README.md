# karolekStore
Simple library to use mysql or sqlite in java

Usage:
```java
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
        }, "localhost:3306", "karolek", "karolek", "123321");


        Queries.select()
                .table("tabela")
                .column("id")
                .column("wartosc")
                .where()
                .column("id").greaterThan(2)
                .column("id").lessOrEqualsThan(10)
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
```
