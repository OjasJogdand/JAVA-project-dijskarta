package project2.java;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputFilename = "cities_dataset.txt";
        String outputFilename = "output_distances.txt";
        Map<String, Integer> cityIndex = new HashMap<>();
        ArrayList<ArrayList<ArrayList<Integer>>> adjList = readCitiesDataset(inputFilename, cityIndex);
        if (adjList == null) {
            System.out.println("Failed to read dataset.");
            return;
        }

        String sourceCity = "Pune";
        int V = cityIndex.size();
        int S = cityIndex.get(sourceCity);

        Solution obj = new Solution();
        int[] res = obj.dijkstra(V, adjList, S);

        
        writeDistancesToFile(outputFilename, res, cityIndex, sourceCity);
        System.out.println("Output written to " + outputFilename);
    }

    
    static ArrayList<ArrayList<ArrayList<Integer>>> readCitiesDataset(String filename, Map<String, Integer> cityIndex) {
        ArrayList<ArrayList<ArrayList<Integer>>> adjList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int index = 0;

            br.readLine(); 

            while ((line = br.readLine()) != null) {
                
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String origin = parts[0];
                String destination = parts[1];
                int distance = Integer.parseInt(parts[2]);

                if (!cityIndex.containsKey(origin)) {
                    cityIndex.put(origin, index++);
                    adjList.add(new ArrayList<>());
                }

                if (!cityIndex.containsKey(destination)) {
                    cityIndex.put(destination, index++);
                    adjList.add(new ArrayList<>());
                }

                int originIndex = cityIndex.get(origin);
                int destIndex = cityIndex.get(destination);

                
                adjList.get(originIndex).add(new ArrayList<>(Arrays.asList(destIndex, distance)));
                adjList.get(destIndex).add(new ArrayList<>(Arrays.asList(originIndex, distance)));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return adjList;
    }

    
    static void writeDistancesToFile(String filename, int[] distances, Map<String, Integer> cityIndex, String sourceCity) {
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Shortest distances from " + sourceCity + ":\n");
            for (Map.Entry<String, Integer> entry : cityIndex.entrySet()) {
                String city = entry.getKey();
                int index = entry.getValue();
                int distance = distances[index];
                String distanceStr = (distance == (int) 1e9) ? "Infinity" : String.valueOf(distance);
                writer.write("To " + city + ": " + distanceStr + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Pair {
    int node;
    int distance;
    public Pair(int distance, int node) {
        this.node = node;
        this.distance = distance;
    }
}


class Solution {
    static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S) {
        PriorityQueue<Pair> pq = new PriorityQueue<>((x, y) -> x.distance - y.distance);
        int[] dist = new int[V];
        Arrays.fill(dist, (int) 1e9);
        dist[S] = 0;
        pq.add(new Pair(0, S));

        while (!pq.isEmpty()) {
            int dis = pq.peek().distance;
            int node = pq.peek().node;
            pq.remove();

            for (int i = 0; i < adj.get(node).size(); i++) {
                int adjNode = adj.get(node).get(i).get(0);
                int edgeWeight = adj.get(node).get(i).get(1);

                if (dis + edgeWeight < dist[adjNode]) {
                    dist[adjNode] = dis + edgeWeight;
                    pq.add(new Pair(dist[adjNode], adjNode));
                }
            }
        }
        return dist;
    }
}
