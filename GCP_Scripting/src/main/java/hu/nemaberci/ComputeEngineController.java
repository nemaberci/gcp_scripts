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
        // Initialize client that will be used to send requests. This client only needs
        // to be created
        // once, and can be reused for multiple requests. After completing all of your
        // requests, call
        // the `instancesClient.close()` method on the client to
        // safely clean up any remaining background resources.
        try (InstancesClient instancesClient = InstancesClient.create()) {

            // Use the `setMaxResults` parameter to limit the number of results
            // that the API returns per response page.
            AggregatedListInstancesRequest aggregatedListInstancesRequest = AggregatedListInstancesRequest
                    .newBuilder()
                    .setProject(project)
                    .setMaxResults(5)
                    .build();

            InstancesClient.AggregatedListPagedResponse response = instancesClient
                    .aggregatedList(aggregatedListInstancesRequest);

            // Despite using the `setMaxResults` parameter, you don't need to handle the
            // pagination
            // yourself. The returned `AggregatedListPager` object handles pagination
            // automatically, requesting next pages as you iterate over the results.
            for (Map.Entry<String, InstancesScopedList> zoneInstances : response.iterateAll()) {
                // Instances scoped by each zone
                String zone = zoneInstances.getKey();
                if (!zoneInstances.getValue().getInstancesList().isEmpty()) {
                    // zoneInstances.getKey() returns the fully qualified address.
                    // Hence, strip it to get the zone name only
                    System.out.printf("Instances at %s: ", zone.substring(zone.lastIndexOf('/') + 1));
                    for (Instance instance : zoneInstances.getValue().getInstancesList()) {
                        System.out.println(instance.getName());
                    }
                }
            }
            System.out.println("####### Listing all instances complete #######");
        }
    }

}
