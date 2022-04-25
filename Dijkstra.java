import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Dijkstra {
//Slow version and my implementation is not the best one I could make
    public static void main(String[] args) {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        Vertex c = new Vertex();
        Vertex d = new Vertex();
        Vertex e = new Vertex();
        Vertex z = new Vertex();
        a.setName("a");
        b.setName("b");
        c.setName("c");
        d.setName("d");
        e.setName("e");
        z.setName("z");
        ArrayList<Vertex> allVertices = new ArrayList<>();
        allVertices.add(a);
        allVertices.add(b);
        allVertices.add(c);
        allVertices.add(d);
        allVertices.add(e);
        allVertices.add(z);
        Edge ab = new Edge(a, b, 4);
        Edge ac = new Edge(a, c, 2);
        Edge bd = new Edge(b, d, 5);
        Edge ce = new Edge(c, e, 10);
        Edge cd = new Edge(c, d, 8);
        Edge cb = new Edge(c, b, 1);
        Edge dz = new Edge(d, z, 6);
        Edge ez = new Edge(e, z, 5);
        Edge ed = new Edge(e, d, 2);
        a.addE(ab);
        a.addE(ac);
        b.addE(ab);
        b.addE(bd);
        b.addE(cb);
        c.addE(ac);
        c.addE(cb);
        c.addE(cd);
        c.addE(ce);
        d.addE(bd);
        d.addE(cd);
        d.addE(ed);
        d.addE(dz);
        e.addE(ce);
        e.addE(ed);
        e.addE(ez);
        z.addE(dz);
        z.addE(ez);

        Vertex s;

        s = a;

        HashMap<String,Edge> adj = new HashMap<>();
        for (Vertex v : allVertices) {
            for (Edge edge : v.getEdges()) {
                adj.put(edge.getFirstVertex().getName()+edge.getSecondVertex().getName(), edge);
                adj.put(edge.getSecondVertex().getName()+edge.getFirstVertex().getName(), edge);
            }
        }
        ArrayList<Vertex> q = new ArrayList<>();
        HashMap<String, Integer> dist = new HashMap<>();
        HashMap<String, Vertex> prev = new HashMap<>();
        for (Vertex v : allVertices) {
            dist.put(v.getName(), Integer.MAX_VALUE);
            prev.put(v.getName(), null);
            q.add(v);
        }
        dist.put(s.getName(), 0);

        while (!q.isEmpty()) {
            int minDistU = Integer.MAX_VALUE;
            String minDistName = "";

            for (Vertex vertex : q) {
                if (dist.get(vertex.getName()) <= minDistU) {
                    minDistName = vertex.getName();
                    minDistU = dist.get(vertex.getName());
                }
            }

            Vertex u = null;

            for (Vertex vertex : q) {
                if (vertex.getName().equals(minDistName)) {
                    u = vertex;
                }
            }

            q.remove(u);

            if (u != null){
                for (Vertex v : u.getNeighbors()) {
                    if (q.contains(v)) {
                        int alt = dist.get(u.getName()) + adj.get(u.getName() + v.getName()).getAmount();
                        if (alt < dist.get(v.getName())) {
                            dist.put(v.getName(), alt);
                            prev.put(v.getName(), u);
                        }
                    }
                }
            }
        }

        System.out.println(dist);
        System.out.println(prev);

    }
}
class Vertex {
    ArrayList<Edge> edges = new ArrayList<>();
    String name;
    public void addE(Edge e) {
        edges.add(e);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Vertex[] getNeighbors() {
        Vertex[] vertices = new Vertex[this.getEdges().size()];
        int i = 0;
        for (Edge e : this.getEdges()) {
            if (e.getFirstVertex() == this) {
                vertices[i] = e.getSecondVertex();
            } else if (e.getSecondVertex() == this) {
                vertices[i] = e.getFirstVertex();
            }
        ++i;
        }
        return vertices;
    }

    @Override
    public String toString() {
        return name;
    }
}
class Edge {
    Vertex[] vertices = new Vertex[2];
    int amount;

    Edge(Vertex a, Vertex b, int amount) {
        vertices[0] = a;
        vertices[1] = b;
        this.amount = amount;
    }

    public Vertex getFirstVertex() {
        return vertices[0];
    }

    public Vertex getSecondVertex() {
        return vertices[1];
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Edge{" + "vertices=" + Arrays.toString(vertices) + ", amount=" + amount + '}';
    }
}
