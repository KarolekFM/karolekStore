# karolekStore
Simple librabry to use mysql or sqlite in java

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
        }, "s1.speedhost.pl:3306", "sid1947_karolek", "sid1947_karolek", "PHPTy3jj");

        Queries.select()
                .table("t", "tabela")
                .column("t", "id")
                .column("t", "wartosc")
                .where()
                .column("t", "id").greaterThan(2)
                .column("t", "id").lessOrEqualsThan(10)
                .query()
                .limit(3)
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
                .order("t", "id", OrderQuery.OrderType.ASC)
                .execute(store);
```
