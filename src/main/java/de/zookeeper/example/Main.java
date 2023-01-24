package de.zookeeper.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == KeeperState.SyncConnected) {
                    System.out.println("connected to zookeeper!");
                }
            }
        });

        Thread.sleep(5000);

//        // create example
//        String rootPath = zk.create(
//                "/exroot",
//                "root".getBytes(StandardCharsets.UTF_8),
//                ZooDefs.Ids.OPEN_ACL_UNSAFE,
//                CreateMode.PERSISTENT
//        );
//        System.out.println("rootPath : " + rootPath);

        // get example
        String rootPath = "/exroot";
        byte[] result = zk.getData(rootPath, null, null);
        System.out.println("The value of " + rootPath + ": " + new String(result));

        // update example
        int latestVersion = zk.exists(rootPath, true).getVersion();
        zk.setData(rootPath, "root2".getBytes(StandardCharsets.UTF_8), latestVersion);

        result = zk.getData(rootPath, null, null);
        System.out.println("The value of " + rootPath + ": " + new String(result));
    }
}
