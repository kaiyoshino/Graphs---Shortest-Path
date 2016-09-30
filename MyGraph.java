//Kai Yoshino
//6/1/15
//CSE373
//Assignment #5
//This class creates a representation of a graph
//Assumes that we do not have negative cost edges in the graph.

import java.util.*;

public class MyGraph implements Graph {
	// you will need some private fields to represent the graph
	// you are also likely to want some private helper methods
   
   Collection<Vertex> verts;
   Collection<Edge> edges;
   Path p;
   HashMap<Vertex, ArrayList<Edge>> map;

	/**
	 * Creates a MyGraph object with the given collection of vertices
	 * and the given collection of edges.
	 * @param v a collection of the vertices in this graph
	 * @param e a collection of the edges in this graph
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
      map = new HashMap();
      Vertex[] ve = v.toArray(new Vertex[v.size()]);
      for (int i = 0; i < ve.length; i++) {
         ArrayList<Edge> a = new ArrayList<Edge>();
         map.put(ve[i], a);
      }
      Edge[] ed = e.toArray(new Edge[e.size()]);
      for (int i = 0; i < ed.length; i++) {
         map.get(ed[i].getSource()).add(ed[i]);
         
      }
      verts = v;
      edges = e;
	}

	/** 
	 * Return the collection of vertices of this graph
	 * @return the vertices as a collection (which is anything iterable)
	 */
	public Collection<Vertex> vertices() {
      return verts;
	}

	/** 
	 * Return the collection of edges of this graph
	 * @return the edges as a collection (which is anything iterable)
	 */
	public Collection<Edge> edges() {
      return edges;
	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v.
	 *   i.e., the set of all vertices w where edges v -> w exist in the graph.
	 * Return an empty collection if there are no adjacent vertices.
	 * @param v one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException if v does not exist.
	 */
	public Collection<Vertex> adjacentVertices(Vertex v) {
      if (v.getLabel() == null) {
         throw new IllegalArgumentException();
      }
      ArrayList<Edge> a = map.get(v);
      Edge[] ed = a.toArray(new Edge[a.size()]);
      Collection<Vertex> ve = new ArrayList<Vertex>();
      for (int i = 0; i < ed.length; i++) {
         ve.add(ed[i].getDestination());
      }
      return ve;
	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
	 * Assumes that we do not have negative cost edges in the graph.
	 * @param a one vertex
	 * @param b another vertex
	 * @return cost of edge if there is a directed edge from a to b in the graph, 
	 * return -1 otherwise.
	 * @throws IllegalArgumentException if a or b do not exist.
	 */
	public int edgeCost(Vertex a, Vertex b) {
      if (a.getLabel() == null || b.getLabel() == null) {
         throw new IllegalArgumentException();
      }
      Edge[] ed = map.get(a).toArray(new Edge[map.get(a).size()]);
      for (int i = 0; i < ed.length; i++) {
         if (ed[i].getDestination() == b) {
            return ed[i].getWeight();
         }
      }
      return -1;
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path.  Assumes all edge weights are nonnegative.
	 * Uses Dijkstra's algorithm.
	 * @param a the starting vertex
	 * @param b the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *   and contains a (first) and b (last) and the cost is the cost of 
	 *   the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) {
      if (a.getLabel() == null || b.getLabel() == null) {
         throw new IllegalArgumentException();
      } else if (a.equals(b)) {
         List<Vertex> l = new ArrayList<Vertex>();
         l.add(a);
         return new Path(l, 0);
      } else {
         Vertex[] v = verts.toArray(new Vertex[verts.size()]);
         for(int i = 0; i < v.length; i++) {
            v[i].known = false;
            if (v[i].equals(a)) {
               v[i].cost = 0;
               v[i].path = null;
            } else {
               v[i].cost = Integer.MAX_VALUE;
            }
         }
         int known = 0;
         Vertex x = v[0];
         int y;
         while (known < v.length) {
            y = Integer.MAX_VALUE;
            for (int i = 0; i < v.length; i++) {
               if (v[i].cost < y && !v[i].known) {
                  x = v[i];
                  y = x.cost;
               }
            }
            x.known = true;
            known++;
            Edge[] ed = map.get(x).toArray(new Edge[map.get(x).size()]);
            for (int i = 0; i < ed.length; i++) {
               Vertex current = ed[i].getDestination();
               if(!current.known) {
                  int cost = edgeCost(x, current);
                  if(x.cost + cost < current.cost) {
                     current.cost = x.cost + cost;
                     current.path = x;
                  }
               }
            }
            
         }
      }
      Vertex pathVert = b;
      List<Vertex> l = new ArrayList<Vertex>();
      boolean exists = false;
      while(pathVert.path != null) {
         l.add(pathVert);
         pathVert = pathVert.path;
         if (pathVert.equals(a)) {
            exists = true;
         }
      }
      if (exists) {
         return new Path(l, b.cost);
      } else {
         return null;
      }
	}

}
