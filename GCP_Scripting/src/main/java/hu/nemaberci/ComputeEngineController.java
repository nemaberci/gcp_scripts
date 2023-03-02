package hu.nemaberci;

import java.io.IOException;
import java.util.Map;

import com.google.cloud.compute.v1.AggregatedListInstancesRequest;
import com.google.cloud.compute.v1.Instance;
import com.google.cloud.compute.v1.InstancesClient;
import com.google.cloud.compute.v1.InstancesScopedList;

public class ComputeEngineController {

    // List all instances in the specified project ID.
    public static void listAllInstances(String project) throws IOException {
        
        try (InstancesClient instancesClient = InstancesClient.create()) {

            AggregatedListInstancesRequest aggregatedListInstancesRequest = AggregatedListInstancesRequest
                    .newBuilder()
                    .setProject(project)
                    .setMaxResults(5)
                    .build();

            InstancesClient.AggregatedListPagedResponse response = instancesClient
                    .aggregatedList(aggregatedListInstancesRequest);

            for (Map.Entry<String, InstancesScopedList> zoneInstances : response.iterateAll()) {
                String zone = zoneInstances.getKey();
                if (!zoneInstances.getValue().getInstancesList().isEmpty()) {
                    System.out.printf("Instances at %s: ", zone.substring(zone.lastIndexOf('/') + 1));
                    for (Instance instance : zoneInstances.getValue().getInstancesList()) {
                        System.out.println(instance.getName());
                    }
                }
            }
        
            System.out.println("####### Listing all instances complete #######");
        
        }
    }

    public static void createInstance(String project, String zone, String instanceName) {
        
        // todo

    }

    public static void deleteInstance(String project, String zone, String instanceName) {

        // todo

    }

}
