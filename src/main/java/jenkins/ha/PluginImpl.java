package jenkins.ha;

import hudson.Plugin;
import jenkins.ha.redis.RedisConnections;
import jenkins.model.Jenkins;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class PluginImpl extends Plugin {


    public void start() throws Exception {
        final Jenkins jenkers = Jenkins.getInstance();
        final HaJenkinsQueue queue = new HaJenkinsQueue();
        final Field queueField = Jenkins.class.getDeclaredField("queue");
        queueField.setAccessible(true);
        ReflectionUtils.setField(queueField, jenkers, queue);
    }

    @Override
    public void postInitialize() throws Exception {
        RedisConnections.init();
//        if (config.getServeBuilds()) {
//            final ExecutorService executor = Executors.newFixedThreadPool(3);
//            executor.submit((Runnable) () -> {
//                while (true) {
//                    new DbQueueScheduler().doRun();
//                }
//            });
//            executor.submit((Runnable) () -> {
//                while (true) {
//                    new RemoteBuildStopListener.RemoteQueueCancellationListener().doRun();
//                    System.out.print("processed item");
//                }
//            });
//            executor.submit((Runnable) () -> {
//                while (true) {
//                    new Queue().subscribeToChannel("jenkins:build_cancellation", new RemoteBuildStopListener());
//                }
//
//            });
//        }
    }

    @Override
    public void stop() throws Exception {
        RedisConnections.shutDown();
    }
}
